package com.brugnot.security.core.crypt.impl;

import com.brugnot.security.core.crypt.EncryptionWrapper;
import com.brugnot.security.core.user.impl.AuthenticatedUserImpl;
import com.brugnot.security.core.user.impl.GeneratedSigningUser;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Antonin on 27/11/2016.
 */
public class RSARequestEncryptionTest {



    @Test
    public void testEncryption() throws Exception {

        GeneratedSigningUser signingUser = new GeneratedSigningUser("test");

        RSARequestEncryption rsaRequestEncryption = new RSARequestEncryption();

        String hashedRequest = "test";

        EncryptionWrapper encryptionWrapper = rsaRequestEncryption.encryptHashedRestCanonicalRequest(signingUser,hashedRequest);

        AuthenticatedUserImpl authenticatedUser = new AuthenticatedUserImpl("test",signingUser.getPublicKey(),encryptionWrapper.getEncryptKey());

        EncryptionWrapper result = rsaRequestEncryption.decryptHashedRestCanonicalRequest(authenticatedUser,encryptionWrapper.getRequest());

        Assert.assertEquals(hashedRequest,result.getRequest());

    }
}
