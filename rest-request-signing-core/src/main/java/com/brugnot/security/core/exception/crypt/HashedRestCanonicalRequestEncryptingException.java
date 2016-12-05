package com.brugnot.security.core.exception.crypt;

/**
 * Created by Antonin on 27/11/2016.
 */
public class HashedRestCanonicalRequestEncryptingException extends RequestEncryptionException {

    public HashedRestCanonicalRequestEncryptingException(String message) {
        super(message);
    }

    public HashedRestCanonicalRequestEncryptingException(String message, Throwable cause) {
        super(message, cause);
    }
}
