package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.exception.user.SigningUserCreationException;
import com.brugnot.security.core.user.SigningUserCreator;
import com.brugnot.security.core.user.impl.abs.AbstractKeystoreUserOperation;
import com.brugnot.security.rest.commons.user.KeystoreUser;
import com.brugnot.security.rest.commons.user.SigningUser;
import org.perf4j.aop.Profiled;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;

/**
 * Created by Antonin on 03/12/2016.
 */
public class KeystoreSigningUserCreatorImpl extends AbstractKeystoreUserOperation implements SigningUserCreator<KeystoreUser> {

    @Profiled
    public SigningUser createSigningUser(KeystoreUser user) throws SigningUserCreationException {

        PrivateKey privateKey;
        try {
            privateKey = (PrivateKey) keystoreLoader.getKeyStore().getKey(user.getKeyAlias(),user.getKeyPassword().toCharArray());
        } catch (KeyStoreException e) {
            throw new SigningUserCreationException("Error while consulting the user Keystore",e);
        } catch (NoSuchAlgorithmException e) {
            throw new SigningUserCreationException("Error while consulting the user Keystore",e);
        } catch (UnrecoverableKeyException e) {
            throw new SigningUserCreationException("Error while consulting the user Keystore",e);
        }

        return new SigningUserImpl(user.getUserName(),privateKey);

    }


}
