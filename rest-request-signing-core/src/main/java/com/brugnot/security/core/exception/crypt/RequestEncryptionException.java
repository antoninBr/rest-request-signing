package com.brugnot.security.core.exception.crypt;

/**
 * Created by Antonin on 27/11/2016.
 */
public class RequestEncryptionException extends Exception {

    public RequestEncryptionException(String message) {
        super(message);
    }

    public RequestEncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
