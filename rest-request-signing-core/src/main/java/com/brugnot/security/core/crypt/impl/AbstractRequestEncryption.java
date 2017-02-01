package com.brugnot.security.core.crypt.impl;

import com.brugnot.security.core.crypt.HashedRestCanonicalRequestDecryptor;
import com.brugnot.security.core.crypt.HashedRestCanonicalRequestEncryptor;
import com.brugnot.security.core.crypt.wrapper.DecryptionWrapper;
import com.brugnot.security.core.crypt.wrapper.EncryptionWrapper;
import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestDecryptingException;
import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestEncryptingException;
import com.brugnot.security.core.exception.crypt.RequestEncryptionException;
import com.brugnot.security.rest.commons.user.CandidateUser;
import com.brugnot.security.rest.commons.user.SigningUser;
import org.apache.commons.codec.binary.Base64;
import org.perf4j.aop.Profiled;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Antonin on 27/11/2016.
 */
public class AbstractRequestEncryption implements HashedRestCanonicalRequestEncryptor, HashedRestCanonicalRequestDecryptor{


    private Cipher encryptKeyCipher;

    private Cipher requestCipher;

    public AbstractRequestEncryption(String encryptKeyCipherAlgorithm, String requestCipherAlgorithm) throws RequestEncryptionException {
        try {
            this.encryptKeyCipher = Cipher.getInstance(encryptKeyCipherAlgorithm);
            this.requestCipher = Cipher.getInstance(requestCipherAlgorithm);
        }catch(NoSuchAlgorithmException e){
            throw new RequestEncryptionException("Error while instantiating the request encryption components",e);
        }catch(NoSuchPaddingException e){
            throw new RequestEncryptionException("Error while instantiating the request encryption components",e);
        }

    }

    @Profiled
    public EncryptionWrapper encryptHashedRestCanonicalRequest(SigningUser user, String hashedRestCanonicalRequest) throws HashedRestCanonicalRequestEncryptingException {


        long startTime = System.currentTimeMillis();

        try {
            encryptKeyCipher.init(Cipher.ENCRYPT_MODE, user.getPrivateKey());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        SecretKey encryptKey = keyGen.generateKey();

        try {
            requestCipher.init(Cipher.ENCRYPT_MODE, encryptKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] encryptedHashedRequest;
        try {
            encryptedHashedRequest = requestCipher.doFinal(hashedRestCanonicalRequest.getBytes());
        } catch (IllegalBlockSizeException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while encrypting the hashedRestCanonicalRequest",e);
        } catch (BadPaddingException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while encrypting the hashedRestCanonicalRequest",e);
        }

        byte[] encryptedKey;
        try {
            encryptedKey = encryptKeyCipher.doFinal(encryptKey.getEncoded());
        } catch (IllegalBlockSizeException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while encrypting the key",e);
        } catch (BadPaddingException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while encrypting the key",e);
        }


        return new EncryptionWrapper(Base64.encodeBase64String(encryptedHashedRequest),
                System.currentTimeMillis()-startTime,
                Base64.encodeBase64String(encryptedKey));
    }

    @Profiled
    public DecryptionWrapper decryptHashedRestCanonicalRequest(CandidateUser user, String cryptedHashedRestCanonicalRequest) throws HashedRestCanonicalRequestDecryptingException {

        long startTime = System.currentTimeMillis();

        try {
            encryptKeyCipher.init(Cipher.DECRYPT_MODE, user.getPublicKey());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] decryptedKey;
        try {
            decryptedKey = encryptKeyCipher.doFinal(Base64.decodeBase64(user.getEncryptedRequestKey()));
        } catch (IllegalBlockSizeException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while decrypting the Key",e);
        } catch (BadPaddingException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while decrypting the Key",e);
        }

        SecretKey originalKey = new SecretKeySpec(decryptedKey, 0, decryptedKey.length, "AES");

        try {
            requestCipher.init(Cipher.DECRYPT_MODE, originalKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] decrypted;
        try {
            decrypted = requestCipher.doFinal(Base64.decodeBase64(cryptedHashedRestCanonicalRequest));
        } catch (IllegalBlockSizeException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while decrypting the crypted hashedRestCanonicalRequest",e);
        } catch (BadPaddingException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while decrypting the crypted hashedRestCanonicalRequest",e);
        }

        return new DecryptionWrapper(new String(decrypted),System.currentTimeMillis()-startTime);
    }
}
