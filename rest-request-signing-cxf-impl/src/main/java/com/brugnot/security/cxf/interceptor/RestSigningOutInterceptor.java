package com.brugnot.security.cxf.interceptor;

import com.brugnot.security.core.crypt.HashedRestCanonicalRequestEncryptor;
import com.brugnot.security.core.crypt.wrapper.EncryptionWrapper;
import com.brugnot.security.core.exception.builder.RestBuilderException;
import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestEncryptingException;
import com.brugnot.security.core.exception.user.SigningUserCreationException;
import com.brugnot.security.core.user.SigningUserCreator;
import com.brugnot.security.cxf.interceptor.abs.AbstractCxfRestOutOperation;
import com.brugnot.security.cxf.interceptor.exception.RequestComponentExtractionException;
import com.brugnot.security.cxf.interceptor.exception.RequestPayloadExtractionException;
import com.brugnot.security.rest.commons.header.RestSecurityHeadersEnum;
import com.brugnot.security.rest.commons.user.SigningUser;
import com.brugnot.security.rest.commons.user.User;
import org.apache.cxf.annotations.Provider;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Antonin on 04/12/2016.
 */
@Provider(Provider.Type.OutInterceptor)
public final class RestSigningOutInterceptor extends AbstractCxfRestOutOperation {

    private User user;

    private HashedRestCanonicalRequestEncryptor hashedRestCanonicalRequestEncryptor;

    private SigningUserCreator signingUserCreator;

    public void handleMessage(Message message) throws Fault {

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
            e.printStackTrace();
        } catch (RestBuilderException e) {
            e.printStackTrace();
        } catch (HashedRestCanonicalRequestEncryptingException e) {
            e.printStackTrace();
        } catch (RequestPayloadExtractionException e) {
            e.printStackTrace();
        } catch (RequestComponentExtractionException e) {
            e.printStackTrace();
        }

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
