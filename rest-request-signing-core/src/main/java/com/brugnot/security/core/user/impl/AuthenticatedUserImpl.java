package com.brugnot.security.core.user.impl;

import com.brugnot.security.rest.commons.user.AuthenticatedUser;

import java.security.PublicKey;
import java.util.Date;

/**
 * Created by Antonin on 17/01/2017.
 */
public class AuthenticatedUserImpl extends CandidateUserImpl implements AuthenticatedUser {

    private Date userAuthenticationDate;

    public AuthenticatedUserImpl(String userName, PublicKey publicKey, String encryptedRequestKey) {
        super(userName, publicKey, encryptedRequestKey);
    }

    public Date getUserAuthenticationDate() {
        return userAuthenticationDate;
    }
}
