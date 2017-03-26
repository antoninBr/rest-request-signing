package com.brugnot.security.core.exception.user;



/**
 * Created by Antonin on 03/12/2016.
 */
public class SigningUserCreationException extends UserException {

    /**
     *
     * @param message
     * @param cause
     */
    public SigningUserCreationException(String message, Throwable cause) {
        super(message, cause);
    }

}
