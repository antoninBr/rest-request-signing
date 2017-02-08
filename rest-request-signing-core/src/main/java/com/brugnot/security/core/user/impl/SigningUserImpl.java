package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.user.impl.abs.AbstractUser;
import com.brugnot.security.rest.commons.user.SigningUser;

import java.security.PrivateKey;

/**
 * Signing User Implementation
 * Created by Antonin on 03/12/2016.
 */
public class SigningUserImpl extends AbstractUser implements SigningUser {

    /**
     * The Signing User Private Key
     */
    private PrivateKey privateKey;

    /**
     * Signing User Constructor
     * @param userName
     * @param privateKey
     */
    protected SigningUserImpl(String userName, PrivateKey privateKey) {
        super(userName);
        this.privateKey = privateKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }


}
