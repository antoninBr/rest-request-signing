package com.brugnot.security.core.exception.user;

/**
 * Created by Antonin on 28/11/2016.
 */
public class UserException extends Exception{

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
}
