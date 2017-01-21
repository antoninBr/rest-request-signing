package com.brugnot.security.cxf.interceptor;

import com.brugnot.security.core.crypt.HashedRestCanonicalRequestDecryptor;
import com.brugnot.security.core.crypt.wrapper.DecryptionWrapper;
import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestDecryptingException;
import com.brugnot.security.core.exception.user.UserAuthenticationException;
import com.brugnot.security.core.user.AuthenticatedUserCreator;
import com.brugnot.security.core.user.AuthenticatedUserHolder;
import com.brugnot.security.core.user.CandidateUserCreator;
import com.brugnot.security.cxf.interceptor.abs.AbstractCxfRestInOperation;
import com.brugnot.security.cxf.interceptor.exception.SecurityHeadersExtractionException;
import com.brugnot.security.rest.commons.hash.NormalizedHashAlgorithm;
import com.brugnot.security.rest.commons.header.RestSecurityHeaders;
import com.brugnot.security.rest.commons.header.RestSecurityHeadersEnum;
import com.brugnot.security.rest.commons.user.CandidateUser;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by Antonin on 17/01/2017.
 */
public final class RestSigningInInterceptor extends AbstractCxfRestInOperation{

    private CandidateUserCreator candidateUserCreator;

    private HashedRestCanonicalRequestDecryptor hashedRestCanonicalRequestDecryptor;

    private AuthenticatedUserCreator authenticatedUserCreator;

    private AuthenticatedUserHolder holder;

    public void handleMessage(Message message) throws Fault {

        Map<String, List<String>> headers = (Map<String, List<String>>) message
                .get(Message.PROTOCOL_HEADERS);

        try {

            String candidateName = extractRestSecurityHeader(headers, RestSecurityHeadersEnum.REST_SIGNATURE_USER);
            String canonicalRequestKey = extractRestSecurityHeader(headers, RestSecurityHeadersEnum.REST_CANONICAL_REQUEST_KEY);
            CandidateUser candidateUser = candidateUserCreator.createCandidateUser(candidateName,canonicalRequestKey);

            payloadHashAlgorithm = NormalizedHashAlgorithm.createNormalizedHashAlgorithm(extractRestSecurityHeader(headers, RestSecurityHeadersEnum.REST_PAYLOAD_HASH_ALGORITHM));
            requestHashAlgorithm = NormalizedHashAlgorithm.createNormalizedHashAlgorithm(extractRestSecurityHeader(headers, RestSecurityHeadersEnum.REST_CANONICAL_REQUEST_HASH_ALGORITHM));

            String hashedLocalRequest = buildRestRequestFromMessage(message);

            String cryptedHashedRequest = extractRestSecurityHeader(headers,RestSecurityHeadersEnum.REST_CANONICAL_REQUEST);

            DecryptionWrapper decryptionWrapper = hashedRestCanonicalRequestDecryptor.decryptHashedRestCanonicalRequest(candidateUser,cryptedHashedRequest);

            if(decryptionWrapper.getProcessedRequest().equals(hashedLocalRequest)){
                holder.hold(authenticatedUserCreator.createAuthenticatedUser(candidateUser));
            }else{

            }

        } catch (UserAuthenticationException e) {
            e.printStackTrace();
        } catch (SecurityHeadersExtractionException e) {
            e.printStackTrace();
        } catch (HashedRestCanonicalRequestDecryptingException e) {
            e.printStackTrace();
        }
    }

    private String extractRestSecurityHeader(Map<String, List<String>> headers, RestSecurityHeaders restSecurityHeaders) throws SecurityHeadersExtractionException {

        List<String> securityHeaderValues =  headers.get(restSecurityHeaders.getNormalizedName());

        if (securityHeaderValues == null || securityHeaderValues.isEmpty()) {
            if(restSecurityHeaders.isMandatory()){
                throw new SecurityHeadersExtractionException("Mandatory Rest Security Header with normalized name : '"+restSecurityHeaders.getNormalizedName()+"' is missing in the request headers");
            }else{
                return null;
            }
        }else{
            return securityHeaderValues.get(0);
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
