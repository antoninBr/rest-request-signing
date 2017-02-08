package com.brugnot.security.core.exception.crypt;

/**
 * Created by Antonin on 27/11/2016.
 */
public class CryptoComponentInstantiationException extends Exception {

    public CryptoComponentInstantiationException(String message) {
        super(message);
    }

    public CryptoComponentInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
