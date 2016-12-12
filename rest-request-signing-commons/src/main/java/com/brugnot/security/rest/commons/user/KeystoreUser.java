package com.brugnot.security.rest.commons.user;

/**
 * Created by Antonin on 03/12/2016.
 */
public interface KeystoreUser extends User {

    /**
     * Keystore User Key Alias
     * @return key alias
     */
    String getKeyAlias();

    /**
     * Keystore User Key Password if exist
     * @return key password
     */
    String getKeyPassword();

}
