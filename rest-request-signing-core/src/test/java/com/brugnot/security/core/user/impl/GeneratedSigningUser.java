package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.user.impl.abs.AbstractUser;
import com.brugnot.security.rest.commons.user.SigningUser;

import java.security.*;

/**
 * Created by Antonin on 05/12/2016.
 */
public class GeneratedSigningUser extends AbstractUser implements SigningUser {


    private KeyPair keyPair;

    public GeneratedSigningUser(String userName) {
        super(userName);
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.initialize(512);
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public PublicKey getPublicKey(){
        return keyPair.getPublic();
    }
}
