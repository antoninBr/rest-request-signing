package com.brugnot.security.core.user.impl;
import com.brugnot.security.core.TestConstant;
import com.brugnot.security.core.exception.loader.KeystoreLoaderException;
import com.brugnot.security.core.exception.user.SigningUserCreationException;
import com.brugnot.security.core.exception.user.code.SigningUserCreationErrCode;
import com.brugnot.security.rest.commons.user.SigningUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by Antonin on 26/03/2017.
 */
public class KeystoreSigningUserCreatorImplTest extends StoreTester{

    KeystoreSigningUserCreatorImpl signingUserCreator;



    @Before
    public void setUp() throws Exception {
        signingUserCreator = new KeystoreSigningUserCreatorImpl();
        signingUserCreator.setKeystoreLoader(createKeyStoreLoader(TestConstant.KEYSTORE_P12_FILE.getValue(), "PKCS12", TestConstant.VALID_KEYSTORE_PASSWORD.getValue()));
    }

    @Test
    public void testCreateSigningUser() throws Exception {

        SigningUser signingUser = signingUserCreator.createSigningUser(new KeystoreUserImpl(TestConstant.VALID_USERNAME.getValue(),
                TestConstant.VALID_USER_KEY_ALIAS.getValue(),
                TestConstant.VALID_USER_KEY_PASSWORD.getValue()));
        Assert.assertNotNull(signingUser);
        Assert.assertNotNull(TestConstant.VALID_USERNAME.getValue(),signingUser.getUserName());
        Assert.assertNotNull(signingUser.getPrivateKey());

    }

    @Test
    public void testInvalidKeyAlias() throws Exception {

        try{
            signingUserCreator.createSigningUser(new KeystoreUserImpl(TestConstant.VALID_USERNAME.getValue(), "toto"
                    + "", TestConstant.VALID_USER_KEY_PASSWORD.getValue()));
        } catch (SigningUserCreationException e){
            if(!e.getErrCode().equals(SigningUserCreationErrCode.KEY_EXTRACT)){
                Assert.fail();
            }
        }
    }

    @Test
    public void testInvalidKeyPassword() throws Exception {

        try{
            signingUserCreator.createSigningUser(new KeystoreUserImpl(TestConstant.VALID_USERNAME.getValue(), TestConstant.VALID_USER_KEY_ALIAS.getValue()
                    + "", "badKeyPass"));
        } catch (SigningUserCreationException e){
            if(!SigningUserCreationErrCode.KEY_EXTRACT.equals(e.getErrCode())){
                Assert.fail();
            }
        }
    }
}