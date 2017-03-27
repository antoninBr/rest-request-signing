package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.TestConstant;
import com.brugnot.security.core.exception.user.UserAuthenticationException;
import com.brugnot.security.core.exception.user.code.UserAuthenticationErrCode;
import com.brugnot.security.rest.commons.user.CandidateUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Antonin on 26/03/2017.
 */
public class TrustStoreCandidateUserCreatorImplTest extends StoreTester{

    private TrustStoreCandidateUserCreatorImpl trustStoreCandidateUserCreator;

    private static final String ENCRYPTED_REQUEST_KEY = "abc";

    @Before
    public void setUp() throws Exception {
        trustStoreCandidateUserCreator = new TrustStoreCandidateUserCreatorImpl();
        trustStoreCandidateUserCreator.setKeystoreLoader(
                createKeyStoreLoader(TestConstant.TRUSTSTORE_JKS_FILE.getValue(),
                        "JKS",
                TestConstant.VALID_TRUSTSTORE_PASSWORD.getValue()));
    }

    @Test
    public void testCreateCandidateUser() throws Exception {
        trustStoreCandidateUserCreator.setUsers(Arrays.asList(new KeystoreUserImpl(TestConstant.VALID_USERNAME.getValue(),
                TestConstant.VALID_USER_KEY_ALIAS.getValue(),
                null)));
        CandidateUser candidateUser = trustStoreCandidateUserCreator.createCandidateUser(TestConstant.VALID_USERNAME.getValue(),ENCRYPTED_REQUEST_KEY);
        Assert.assertNotNull(candidateUser);
        Assert.assertNotNull(TestConstant.VALID_USERNAME.getValue(),candidateUser.getUserName());
        Assert.assertNotNull(candidateUser.getPublicKey());
        Assert.assertEquals(ENCRYPTED_REQUEST_KEY,candidateUser.getEncryptedRequestKey());
    }

    @Test
    public void testUnknownUser() throws Exception {
        trustStoreCandidateUserCreator.setUsers(Arrays.asList(new KeystoreUserImpl(TestConstant.VALID_USERNAME.getValue(),
                TestConstant.VALID_USER_KEY_ALIAS.getValue(),
                null)));
        try{
            trustStoreCandidateUserCreator.createCandidateUser("unknown",ENCRYPTED_REQUEST_KEY);
        }catch (UserAuthenticationException e){
            if(!UserAuthenticationErrCode.UNKONWN_USER.equals(e.getErrCode())){
                Assert.fail();
            }

        }
    }

}