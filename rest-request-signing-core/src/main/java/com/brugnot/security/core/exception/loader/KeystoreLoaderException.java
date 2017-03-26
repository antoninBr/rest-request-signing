package com.brugnot.security.core.exception.loader;

/**
 * Keystore Loader Exception
 * Created by Antonin on 26/03/2017.
 */
public class KeystoreLoaderException extends Exception {

    /**
     * Keystore Loader Exception Constructor
     * @param message
     * @param cause
     */
    public KeystoreLoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
