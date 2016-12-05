package com.brugnot.security.rest.commons.user;

/**
 * Created by Antonin on 03/12/2016.
 */
public interface KeystoreUser extends User {

    String getKeyAlias();

    String getKeyPassword();

}
