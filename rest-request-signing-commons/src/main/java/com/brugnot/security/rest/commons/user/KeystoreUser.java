package com.brugnot.security.rest.commons.user;

/**
 * Created by Antonin on 03/12/2016.
 */
public interface KeystoreUser extends User {

    /**
     * Keystore user Key Alias
     * @return key alias
     */
    String getKeyAlias();

    /**
     * Keystore user Key Password if exist
     * @return key password
     */
    String getKeyPassword();

}
