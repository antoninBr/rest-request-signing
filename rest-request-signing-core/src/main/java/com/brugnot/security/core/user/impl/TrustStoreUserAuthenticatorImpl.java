package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.exception.user.UserAuthenticationException;
import com.brugnot.security.core.user.UserAuthenticator;
import com.brugnot.security.core.user.impl.abs.AbstractKeystoreUserOperation;
import com.brugnot.security.rest.commons.user.AuthenticatedUser;
import com.brugnot.security.rest.commons.user.KeystoreUser;

import javax.inject.Inject;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Map;

/**
 * Created by Antonin on 03/12/2016.
 */
public class TrustStoreUserAuthenticatorImpl extends AbstractKeystoreUserOperation implements UserAuthenticator {

    private Map<String,KeystoreUser> users;

    public AuthenticatedUser authenticateUser(String userName, String encryptedRequestKey) throws UserAuthenticationException {

        KeystoreUser user = getUserFromUserName(userName);

        PublicKey publicKey;

        try {
            Certificate certificate = keystoreLoader.getKeyStore().getCertificate(user.getKeyAlias());
            publicKey = certificate.getPublicKey();
        } catch (KeyStoreException e) {
            throw new UserAuthenticationException("Error while getting the user Public Key from the TrustStore",e);
        }

        return new AuthenticatedUserImpl(user.getUserName(),publicKey,encryptedRequestKey);
    }

    private KeystoreUser getUserFromUserName(String userName) throws UserAuthenticationException {

        if(!users.containsKey(userName)){
            throw new UserAuthenticationException("Unknown User Name");
        }

        return users.get(userName);

    }

    @Inject
    public void setUsers(Map<String, KeystoreUser> users) {
        this.users = users;
    }
}
