package com.brugnot.security.core.user.impl.abs;

import com.brugnot.security.rest.commons.user.User;

/**
 * Abstract user Implementation
 * Created by Antonin on 03/12/2016.
 */
public class AbstractUser implements User{

    /***
     * user Name
     */
    private String userName;

    /**
     * Constructor (super)
     * @param userName
     */
    public AbstractUser(String userName) {
        this.userName = userName;
    }

    /**
     * Get the user name
     * @return user name
     */
    public String getUserName() {
        return this.userName;
    }

    @Override
    public String toString() {
        return "{userName='" + userName + '}';
    }
}
