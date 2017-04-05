package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.exception.loader.KeystoreLoaderException;
import com.brugnot.security.core.tools.KeystoreLoader;

/**
 * Created by a.brugnot on 27/03/2017.
 */
public abstract class StoreTester {

    protected KeystoreLoader createKeyStoreLoader(String keyStoreFileName, String keyStoreType, String keystorePassword)
            throws KeystoreLoaderException {

        KeystoreLoader keystoreLoader = new KeystoreLoader(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(keyStoreFileName),
                keyStoreType,
                keystorePassword);
        keystoreLoader.load();
        return keystoreLoader;
    }
}
