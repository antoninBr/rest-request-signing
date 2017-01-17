package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.user.impl.abs.AbstractUser;
import com.brugnot.security.rest.commons.user.CandidateUser;

import java.security.PublicKey;

/**
 * Created by Antonin on 03/12/2016.
 */
public class CandidateUserImpl extends AbstractUser implements CandidateUser {

    private PublicKey publicKey;

    private String encryptedRequestKey;

    public CandidateUserImpl(String userName, PublicKey publicKey, String encryptedRequestKey) {
        super(userName);
        this.publicKey = publicKey;
        this.encryptedRequestKey = encryptedRequestKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public String getEncryptedRequestKey() {
        return this.encryptedRequestKey;
    }
}
