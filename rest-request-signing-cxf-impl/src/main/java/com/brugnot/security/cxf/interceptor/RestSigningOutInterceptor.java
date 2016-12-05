package com.brugnot.security.cxf.interceptor;

import com.brugnot.security.core.com.brugnot.security.core.builder.RestCanonicalRequestBuilder;
import com.brugnot.security.core.crypt.HashedRestCanonicalRequestEncryptor;
import com.brugnot.security.core.exception.user.SigningUserCreationException;
import com.brugnot.security.core.user.SigningUserCreator;
import com.brugnot.security.core.user.impl.abs.AbstractKeystoreUserOperation;
import com.brugnot.security.cxf.interceptor.abs.AbstractCxfRestOperation;
import com.brugnot.security.rest.commons.user.SigningUser;
import com.brugnot.security.rest.commons.user.User;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;

/**
 * Created by Antonin on 04/12/2016.
 */
public class RestSigningOutInterceptor extends AbstractCxfRestOperation {

    private HashedRestCanonicalRequestEncryptor hashedRestCanonicalRequestEncryptor;

    private User user;

    private SigningUserCreator signingUserCreator;

    public void handleMessage(Message message) throws Fault {

        SigningUser signingUser;

        try {
            signingUser = signingUserCreator.createSigningUser(user);
        } catch (SigningUserCreationException e) {
            e.printStackTrace();
        }

        String hashRestCanonicalRequest = buildRestRequestFromMessage(message);


    }
}
