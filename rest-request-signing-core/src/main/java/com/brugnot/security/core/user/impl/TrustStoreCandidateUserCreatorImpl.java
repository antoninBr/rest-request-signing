package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.exception.user.UserAuthenticationException;
import com.brugnot.security.core.user.CandidateUserCreator;
import com.brugnot.security.core.user.impl.abs.AbstractKeystoreUserOperation;
import com.brugnot.security.rest.commons.user.CandidateUser;
import com.brugnot.security.rest.commons.user.KeystoreUser;

import javax.inject.Inject;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Antonin on 03/12/2016.
 */
public class TrustStoreCandidateUserCreatorImpl extends AbstractKeystoreUserOperation implements CandidateUserCreator {

    private ConcurrentMap<String,KeystoreUser> users;

    public CandidateUser createCandidateUser(String candidateName, String encryptedRequestKey) throws UserAuthenticationException {

        KeystoreUser user = getUserFromCandidateName(candidateName);

        PublicKey publicKey;

        try {
            Certificate certificate = keystoreLoader.getKeyStore().getCertificate(user.getKeyAlias());
            publicKey = certificate.getPublicKey();
        } catch (KeyStoreException e) {
            throw new UserAuthenticationException("Error while getting the user Public Key from the TrustStore",e);
        }

        return new CandidateUserImpl(user.getUserName(),publicKey,encryptedRequestKey);
    }

    private KeystoreUser getUserFromCandidateName(String candidateName) throws UserAuthenticationException {

        if(!users.containsKey(candidateName)){
            throw new UserAuthenticationException("Unknown User Name : '"+candidateName+"'");
        }

        return users.get(candidateName);

    }

    @Inject
    public void setUsers(ConcurrentMap<String, KeystoreUser> users) {
        this.users = users;
    }
}
