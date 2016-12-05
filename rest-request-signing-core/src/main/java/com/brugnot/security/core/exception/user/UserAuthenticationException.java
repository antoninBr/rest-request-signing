package com.brugnot.security.core.exception.user;


/**
 * Created by Antonin on 03/12/2016.
 */
public class UserAuthenticationException extends UserException {

    public UserAuthenticationException(String message) {
        super(message);
    }

    public UserAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
