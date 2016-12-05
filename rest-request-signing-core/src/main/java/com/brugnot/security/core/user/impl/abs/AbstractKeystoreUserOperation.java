package com.brugnot.security.core.user.impl.abs;

import com.brugnot.security.core.user.impl.KeystoreLoader;

import javax.inject.Inject;

/**
 * Created by Antonin on 03/12/2016.
 */
public abstract class AbstractKeystoreUserOperation {

    protected KeystoreLoader keystoreLoader;

    @Inject
    public void setKeystoreLoader(KeystoreLoader keystoreLoader) {
        this.keystoreLoader = keystoreLoader;
    }
}
