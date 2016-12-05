package com.brugnot.security.cxf.interceptor;

import com.brugnot.security.core.crypt.EncryptionWrapper;
import com.brugnot.security.core.crypt.HashedRestCanonicalRequestEncryptor;
import com.brugnot.security.core.exception.builder.RestBuilderException;
import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestEncryptingException;
import com.brugnot.security.core.exception.user.SigningUserCreationException;
import com.brugnot.security.core.user.SigningUserCreator;
import com.brugnot.security.cxf.interceptor.abs.AbstractCxfRestOutOperation;
import com.brugnot.security.rest.commons.user.SigningUser;
import com.brugnot.security.rest.commons.user.User;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;

/**
 * Created by Antonin on 04/12/2016.
 */
public class RestOutSigningOutInterceptor extends AbstractCxfRestOutOperation {

    private User user;

    private HashedRestCanonicalRequestEncryptor hashedRestCanonicalRequestEncryptor;

    private SigningUserCreator signingUserCreator;

    public void handleMessage(Message message) throws Fault {

        EncryptionWrapper encryptionWrapper;
        try {
            SigningUser signingUser = signingUserCreator.createSigningUser(user);
            String hashRestCanonicalRequest = buildRestRequestFromMessage(message);
            encryptionWrapper = hashedRestCanonicalRequestEncryptor.encryptHashedRestCanonicalRequest(signingUser,hashRestCanonicalRequest);

        } catch (SigningUserCreationException e) {
            e.printStackTrace();
        } catch (RestBuilderException e) {
            e.printStackTrace();
        } catch (HashedRestCanonicalRequestEncryptingException e) {
            e.printStackTrace();
        }


    }
}
