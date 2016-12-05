package com.brugnot.security.rest.commons.user;

import java.security.PrivateKey;

/**
 * Created by Antonin on 03/12/2016.
 */
public interface SigningUser extends User{

    /**
     * Signing user Private Key
     * @return privateKey
     */
    PrivateKey getPrivateKey();
}
