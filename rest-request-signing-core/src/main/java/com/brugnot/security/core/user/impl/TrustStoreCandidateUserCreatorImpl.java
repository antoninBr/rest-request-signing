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
import java.util.List;

/**
 * Created by Antonin on 03/12/2016.
 */
public class TrustStoreCandidateUserCreatorImpl extends AbstractKeystoreUserOperation implements CandidateUserCreator {

    private List<KeystoreUser> users;

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

        for(KeystoreUser user : users){
                if(user.getUserName().equals(candidateName)){
                    return user;
                }
        }

        throw new UserAuthenticationException("Unknown User Name : '"+candidateName+"'");

    }

    @Inject
    public void setUsers(List<KeystoreUser> users) {
        this.users = users;
    }
}
