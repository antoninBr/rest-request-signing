package com.brugnot.security.cxf.interceptor;

import com.brugnot.security.core.crypt.HashedRestCanonicalRequestEncryptor;
import com.brugnot.security.core.crypt.wrapper.EncryptionWrapper;
import com.brugnot.security.core.exception.builder.RestBuilderException;
import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestEncryptingException;
import com.brugnot.security.core.exception.user.SigningUserCreationException;
import com.brugnot.security.core.user.SigningUserCreator;
import com.brugnot.security.cxf.commons.CXFFaultProvider;
import com.brugnot.security.cxf.interceptor.abs.AbstractCxfRestOutOperation;
import com.brugnot.security.rest.commons.exception.RequestComponentExtractionException;
import com.brugnot.security.cxf.interceptor.exception.RequestPayloadExtractionException;
import com.brugnot.security.rest.commons.header.RestSecurityHeadersEnum;
import com.brugnot.security.rest.commons.user.SigningUser;
import com.brugnot.security.rest.commons.user.User;
import org.apache.cxf.annotations.Provider;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Antonin on 04/12/2016.
 */
@Provider(Provider.Type.OutInterceptor)
public final class RestSigningOutInterceptor extends AbstractCxfRestOutOperation {

    /**
     * The User that signs its request
     */
    private User user;

    /**
     * Hashed Rest Canonical Request Encryptor
     */
    private HashedRestCanonicalRequestEncryptor hashedRestCanonicalRequestEncryptor;

    /**
     * Signing User Creator
     */
    private SigningUserCreator signingUserCreator;

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestSigningOutInterceptor.class);


    public void handleMessage(Message message) throws Fault {

        LOGGER.info("Signing the Rest Outgoing Request");

        EncryptionWrapper encryptionWrapper;
        try {
            SigningUser signingUser = signingUserCreator.createSigningUser(user);
            String hashRestCanonicalRequest = buildRestRequestFromMessage(message);
            encryptionWrapper = hashedRestCanonicalRequestEncryptor.encryptHashedRestCanonicalRequest(signingUser,hashRestCanonicalRequest);

            Map<String, List<String>> headersToUpdate = (Map<String, List<String>>) message
                    .get(Message.PROTOCOL_HEADERS);

            headersToUpdate.put(RestSecurityHeadersEnum.REST_CANONICAL_REQUEST.getNormalizedName(), Arrays.asList(encryptionWrapper.getProcessedRequest()));
            headersToUpdate.put(RestSecurityHeadersEnum.REST_CANONICAL_REQUEST_KEY.getNormalizedName(), Arrays.asList(encryptionWrapper.getEncryptKey()));
            headersToUpdate.put(RestSecurityHeadersEnum.REST_SIGNATURE_USER.getNormalizedName(),Arrays.asList(user.getUserName()));
            headersToUpdate.put(RestSecurityHeadersEnum.REST_CANONICAL_REQUEST_BUILDER_VERSION.getNormalizedName(),Arrays.asList("v1"));
            headersToUpdate.put(RestSecurityHeadersEnum.REST_PAYLOAD_HASH_ALGORITHM.getNormalizedName(), Arrays.asList(payloadHashAlgorithm.getHashAlgorithm()));
            headersToUpdate.put(RestSecurityHeadersEnum.REST_CANONICAL_REQUEST_HASH_ALGORITHM.getNormalizedName(), Arrays.asList(requestHashAlgorithm.getHashAlgorithm()));

        } catch (SigningUserCreationException e) {
            LOGGER.error("Error while creating a signing user using the provided user and signing user creator",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.CLIENT, e);
        } catch (RestBuilderException e) {
            LOGGER.error("Error building one of the rest canonical request artifacts",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.CLIENT, e);
        } catch (HashedRestCanonicalRequestEncryptingException e) {
            LOGGER.error("Error while encrypting the rest canonical request",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.CLIENT, e);
        } catch (RequestPayloadExtractionException e) {
            LOGGER.error("Error while extracting the outgoing request payload for the content hashing",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.CLIENT, e);
        } catch (RequestComponentExtractionException e) {
            LOGGER.error("Error while extracting one of the outgoing request components (query params, headers, etc...)",e);
            throw new CXFFaultProvider().createFault(CXFFaultProvider.FaultSide.CLIENT, e);
        }

        LOGGER.info("Rest outgoing request signed");

    }

    @Inject
    public void setUser(User user) {
        this.user = user;
    }

    @Inject
    public void setHashedRestCanonicalRequestEncryptor(HashedRestCanonicalRequestEncryptor hashedRestCanonicalRequestEncryptor) {
        this.hashedRestCanonicalRequestEncryptor = hashedRestCanonicalRequestEncryptor;
    }

    @Inject
    public void setSigningUserCreator(SigningUserCreator signingUserCreator) {
        this.signingUserCreator = signingUserCreator;
    }
}
