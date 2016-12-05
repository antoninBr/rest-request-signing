package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.user.impl.abs.AbstractUser;
import com.brugnot.security.rest.commons.user.SigningUser;

import java.security.Principal;
import java.security.PrivateKey;

/**
 * Created by Antonin on 03/12/2016.
 */
public class SigningUserImpl extends AbstractUser implements SigningUser {

    private PrivateKey privateKey;

    public SigningUserImpl(String userName, PrivateKey privateKey) {
        super(userName);
        this.privateKey = privateKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
