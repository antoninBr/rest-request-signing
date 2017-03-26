package com.brugnot.security.core.exception.crypt;

/**
 * Created by Antonin on 27/11/2016.
 */
public class HashedRestCanonicalRequestEncryptingException extends CryptoComponentInstantiationException {

    /**
     *
     * @param message
     * @param cause
     */
    public HashedRestCanonicalRequestEncryptingException(String message, Throwable cause) {
        super(message, cause);
    }
}
