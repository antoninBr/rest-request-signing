package com.brugnot.security.core.user.impl.abs;

import com.brugnot.security.rest.commons.user.User;

import java.security.Principal;

/**
 * Created by Antonin on 03/12/2016.
 */
public class AbstractUser implements User{

    private String userName;

    public AbstractUser(String userNamel) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

}
