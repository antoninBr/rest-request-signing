package com.brugnot.security.cxf.interceptor;

import com.brugnot.security.core.crypt.HashedRestCanonicalRequestDecryptor;
import com.brugnot.security.core.crypt.wrapper.DecryptionWrapper;
import com.brugnot.security.core.exception.builder.RestBuilderException;
import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestDecryptingException;
import com.brugnot.security.core.exception.user.UserAuthenticationException;
import com.brugnot.security.core.exception.user.code.UserAuthenticationErrCode;
import com.brugnot.security.core.user.AuthenticatedUserCreator;
import com.brugnot.security.core.user.AuthenticatedUserHolder;
import com.brugnot.security.core.user.CandidateUserCreator;
import com.brugnot.security.cxf.commons.CXFFaultProvider;
import com.brugnot.security.cxf.interceptor.abs.AbstractCxfRestInOperation;
import com.brugnot.security.rest.commons.exception.RequestComponentExtractionException;
import com.brugnot.security.cxf.interceptor.exception.RequestPayloadExtractionException;
import com.brugnot.security.rest.commons.exception.SecurityHeadersExtractionException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;
import com.brugnot.security.rest.commons.hash.NormalizedHashAlgorithm;
import com.brugnot.security.rest.commons.header.RestSecurityHeaders;
import com.brugnot.security.rest.commons.header.RestSecurityHeadersEnum;
import com.brugnot.security.rest.commons.user.CandidateUser;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by Antonin on 17/01/2017.
 */
public final class RestSigningInInterceptor extends AbstractCxfRestInOperation{

    /**
     * Candidate User Creator
     */
    private CandidateUserCreator candidateUserCreator;

    /**
     * Hashed Rest Canonical Request Decryptor
     */
    private HashedRestCanonicalRequestDecryptor hashedRestCanonicalRequestDecryptor;

    /**
     *Authenticated User Creator
     */
    private AuthenticatedUserCreator authenticatedUserCreator;

    /**
     *Authenticated User Holder (request scoped)
     */
    private AuthenticatedUserHolder holder;

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestSigningInInterceptor.class);


    /**
     * Handle incoming Message and check the signing
     * @param message
     * @throws Fault
     */
    public void handleMessage(Message message) throws Fault {

        LOGGER.info("Checking the Signing of the Incoming Rest Request");

        Map<String, List<String>> headers = getHeadersOfMessage(message);

        try {

            String candidateName = extractRestSecurityHeader(headers, RestSecurityHeadersEnum.REST_SIGNATURE_USER);
            String canonicalRequestKey = extractRestSecurityHeader(headers, RestSecurityHeadersEnum.REST_CANONICAL_REQUEST_KEY);
            CandidateUser candidateUser = candidateUserCreator.createCandidateUser(candidateName,canonicalRequestKey);

            HashAlgorithm payloadHashAlgorithm = NormalizedHashAlgorithm.createNormalizedHashAlgorithm(extractRestSecurityHeader(headers, RestSecurityHeadersEnum.REST_PAYLOAD_HASH_ALGORITHM));
            HashAlgorithm requestHashAlgorithm = NormalizedHashAlgorithm.createNormalizedHashAlgorithm(extractRestSecurityHeader(headers, RestSecurityHeadersEnum.REST_CANONICAL_REQUEST_HASH_ALGORITHM));

            String cryptedHashedRequest = extractRestSecurityHeader(headers,RestSecurityHeadersEnum.REST_CANONICAL_REQUEST);

            String hashedLocalRequest = buildRestRequestFromMessage(message,requestHashAlgorithm,payloadHashAlgorithm);

            DecryptionWrapper decryptionWrapper = hashedRestCanonicalRequestDecryptor.decryptHashedRestCanonicalRequest(candidateUser,cryptedHashedRequest);

            if(decryptionWrapper.getProcessedRequest().equals(hashedLocalRequest)){
                holder.hold(authenticatedUserCreator.createAuthenticatedUser(candidateUser));
            }else{
                LOGGER.error("The hashed local canonical request and the received one are not equals (see debug log for more info)");
                LOGGER.debug("Hashed Local Request : '{}' >>> Received Hashed Request (decrypted): '{}'",hashedLocalRequest,decryptionWrapper.getProcessedRequest());
                throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.CLIENT, new UserAuthenticationException(
                        UserAuthenticationErrCode.INVALID_CANONICAL_REQUEST, "Canonical Requests do not match each other"));
            }

        } catch (UserAuthenticationException e) {
            LOGGER.error("Error while authenticating the user (check its name, extracting its public key,etc...)",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.CLIENT, e);
        } catch (SecurityHeadersExtractionException e) {
            LOGGER.error("Error while extracting one of the security headers of the incoming request",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.CLIENT, e);
        } catch (HashedRestCanonicalRequestDecryptingException e) {
            LOGGER.error("Error while decrypting the incoming request hashed canonical request",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.SERVER, e);
        } catch (RequestComponentExtractionException e) {
            LOGGER.error("Error while decrypting the incoming request hashed canonical request",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.SERVER, e);
        } catch (RestBuilderException e) {
            LOGGER.error("Error while extracting the incoming request payload for the content hashing",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.SERVER, e);
        } catch (RequestPayloadExtractionException e) {
            LOGGER.error("Error while extracting one of the incoming request components (query params, headers, etc...)",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.SERVER, e);
        }

        LOGGER.info("Rest incoming request signing checked");
    }

    private Map<String, List<String>> getHeadersOfMessage(Message message) {
        Map<String, List<String>> headers = (Map<String, List<String>>) message
                .get(Message.PROTOCOL_HEADERS);

        if(headers == null || headers.isEmpty()){
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.CLIENT, new Exception("There is no headers on the incoming request"));
        }

        LOGGER.debug("Number of headers in the request : {}",headers.size());

        return headers;
    }

    /**
     * Extract Incoming Request Rest Security Header
     * @TODO: Make this global
     * @param headers
     * @param restSecurityHeaders
     * @return extracted rest security header value
     * @throws SecurityHeadersExtractionException
     */
    private String extractRestSecurityHeader(Map<String, List<String>> headers, RestSecurityHeaders restSecurityHeaders) throws SecurityHeadersExtractionException {

        LOGGER.debug("Extracting the Security header '{}' from the request headers list",restSecurityHeaders.getNormalizedName());
        List<String> securityHeaderValues =  headers.get(restSecurityHeaders.getNormalizedName());

        if (securityHeaderValues == null || securityHeaderValues.isEmpty()) {
            if(restSecurityHeaders.isMandatory()){
                LOGGER.error("Mandatory Rest Security Header with normalized name : '{}' is missing in the request headers",restSecurityHeaders.getNormalizedName());
                throw new SecurityHeadersExtractionException("Mandatory Rest Security Header with normalized name : '"+restSecurityHeaders.getNormalizedName()+"' is missing in the request headers");
            }else{
                LOGGER.debug("There is no security header '{}' in the request headers list",restSecurityHeaders.getNormalizedName());
                return null;
            }
        }else{
            //Get the first value of the list
            String headerValue = securityHeaderValues.get(0);
            LOGGER.debug("Security header '{}' value : '{}'",restSecurityHeaders.getNormalizedName(),headerValue);
            return headerValue;
        }
    }

    @Inject
    public void setCandidateUserCreator(CandidateUserCreator candidateUserCreator) {
        this.candidateUserCreator = candidateUserCreator;
    }

    @Inject
    public void setHashedRestCanonicalRequestDecryptor(HashedRestCanonicalRequestDecryptor hashedRestCanonicalRequestDecryptor) {
        this.hashedRestCanonicalRequestDecryptor = hashedRestCanonicalRequestDecryptor;
    }

    @Inject
    public void setAuthenticatedUserCreator(AuthenticatedUserCreator authenticatedUserCreator) {
        this.authenticatedUserCreator = authenticatedUserCreator;
    }

    @Inject
    public void setHolder(AuthenticatedUserHolder holder) {
        this.holder = holder;
    }
}
