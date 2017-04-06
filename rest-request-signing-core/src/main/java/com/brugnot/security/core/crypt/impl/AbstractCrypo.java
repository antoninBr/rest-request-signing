package com.brugnot.security.core.crypt.impl;

import com.brugnot.security.core.exception.crypt.CryptoComponentInstantiationException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Antonin on 05/04/2017.
 */
public abstract class AbstractCrypo {

    /**
     * Create a Cipher with the provided Algorithm
     * @param cipherAlgorithm
     * @return instantiated Cipher
     * @throws CryptoComponentInstantiationException
     */
    protected Cipher createCipher(String cipherAlgorithm) throws CryptoComponentInstantiationException {
        try {
            return  Cipher.getInstance(cipherAlgorithm);
        }catch(NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new CryptoComponentInstantiationException("Error while instantiating the request encryption components",e);
        }

    }

    /**
     * Generate a One Time Usage Symmetric Encryption Key
     * @param keyAlgorithm
     * @return symmetrical key
     * @throws CryptoComponentInstantiationException
     */
    protected SecretKey generateOneTimeUsageSymmetricEncryptionKey(String keyAlgorithm) throws CryptoComponentInstantiationException {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(keyAlgorithm);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoComponentInstantiationException("Error while generating a one time usage symmetric encryption key",e);
        }

    }
}
