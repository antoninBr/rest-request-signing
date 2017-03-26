package com.brugnot.security.core.user.impl;
import com.brugnot.security.rest.commons.user.SigningUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by Antonin on 26/03/2017.
 */
public class KeystoreSigningUserCreatorImplTest {

    KeystoreSigningUserCreatorImpl signingUserCreator;

    private KeystoreLoader createKeyStoreLoader(String keyStoreFileName, String keyStoreType, String keystorePassword){

        return new KeystoreLoader(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(keyStoreFileName),
                keyStoreType,
                keystorePassword);
    }

    @Before
    public void setUp() throws Exception {
        signingUserCreator = new KeystoreSigningUserCreatorImpl();
        signingUserCreator.setKeystoreLoader(createKeyStoreLoader("keystore.p12", "PKCS12", "pwd"));
    }

    /*@Test
    public void createSigningUser() throws Exception {

        SigningUser signingUser = signingUserCreator.createSigningUser(new KeystoreUserImpl("anto",
                "rrs4j_sample",
                "pwd"));
        Assert.assertNotNull(signingUser);

    }*/

}