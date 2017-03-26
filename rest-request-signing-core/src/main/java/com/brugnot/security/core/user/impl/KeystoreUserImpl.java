package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.user.impl.abs.AbstractUser;
import com.brugnot.security.rest.commons.user.KeystoreUser;

/**
 * Keystore user implementation
 * Created by Antonin on 03/12/2016.
 */
public class KeystoreUserImpl extends AbstractUser implements KeystoreUser {

    /**
     * user Key Alias within the Keystore
     */
    private String keyAlias;

    /**
     * user Key Password (if needed)
     */
    private String keyPassword;

    /**
     * Keystore user Constructor
     * @param userName
     * @param keyAlias
     * @param keyPassword
     */
    public KeystoreUserImpl(String userName, String keyAlias, String keyPassword) {
        super(userName);
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;
    }

    public String getKeyAlias() {
        return this.keyAlias;
    }

    public String getKeyPassword() {
        return this.keyPassword;
    }
}
