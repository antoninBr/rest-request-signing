package com.brugnot.security.core.exception.user;

/**
 * Created by Antonin on 28/11/2016.
 */
public class UserException extends Exception{

    /**
     *
     * @param message
     */
    public UserException(String message) {
        super(message);
    }

    /**
     * 
     * @param message
     * @param cause
     */
    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

}
