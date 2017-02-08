package com.brugnot.security.core.crypt.impl;

import com.brugnot.security.core.crypt.wrapper.DecryptionWrapper;
import com.brugnot.security.core.crypt.wrapper.EncryptionWrapper;
import com.brugnot.security.core.user.impl.CandidateUserImpl;
import com.brugnot.security.core.user.impl.GeneratedSigningUser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Created by Antonin on 27/11/2016.
 */
public class RSARequestEncryptionTest {


    private static final String TEST_USER_NAME = "testUser";

    @Test
    public void testEncryption() throws Exception {

        String generatedHasedRequest = getRandomHexString(30);

        GeneratedSigningUser signingUser = new GeneratedSigningUser(TEST_USER_NAME);

        RSARequestEncryption rsaRequestEncryption = new RSARequestEncryption();

        EncryptionWrapper encryptionWrapper = rsaRequestEncryption.encryptHashedRestCanonicalRequest(signingUser, generatedHasedRequest);

        CandidateUserImpl authenticatedUser = new CandidateUserImpl(TEST_USER_NAME,signingUser.getPublicKey(),encryptionWrapper.getEncryptKey());

        DecryptionWrapper result = rsaRequestEncryption.decryptHashedRestCanonicalRequest(authenticatedUser,encryptionWrapper.getProcessedRequest());

        Assert.assertEquals(generatedHasedRequest,result.getProcessedRequest());

    }

    private String getRandomHexString(int numberOfChars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numberOfChars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numberOfChars);
    }

}
