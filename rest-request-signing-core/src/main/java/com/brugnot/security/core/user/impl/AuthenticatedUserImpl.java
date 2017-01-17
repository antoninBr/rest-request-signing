package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.user.impl.abs.AbstractUser;
import com.brugnot.security.rest.commons.user.AuthenticatedUser;

import java.util.Date;

/**
 * Created by Antonin on 17/01/2017.
 */
public class AuthenticatedUserImpl extends AbstractUser implements AuthenticatedUser {

    private Date userAuthenticationDate;

    public AuthenticatedUserImpl(String userName, Date userAuthenticationDate) {
        super(userName);
        this.userAuthenticationDate = userAuthenticationDate;
    }

    public Date getUserAuthenticationDate() {
        return userAuthenticationDate;
    }
}
