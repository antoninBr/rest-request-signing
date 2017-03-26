package com.brugnot.security.core.exception.crypt;

/**
 * Created by Antonin on 27/11/2016.
 */
public class HashedRestCanonicalRequestDecryptingException extends CryptoComponentInstantiationException {

    /**
     *
     * @param message
     * @param cause
     */
    public HashedRestCanonicalRequestDecryptingException(String message, Throwable cause) {
        super(message, cause);
    }
}
