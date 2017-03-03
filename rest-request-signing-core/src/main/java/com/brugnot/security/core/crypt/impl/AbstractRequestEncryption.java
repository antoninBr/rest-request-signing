package com.brugnot.security.core.crypt.impl;

import com.brugnot.security.core.crypt.HashedRestCanonicalRequestDecryptor;
import com.brugnot.security.core.crypt.HashedRestCanonicalRequestEncryptor;
import com.brugnot.security.core.crypt.wrapper.DecryptionWrapper;
import com.brugnot.security.core.crypt.wrapper.EncryptionWrapper;
import com.brugnot.security.core.exception.crypt.CryptoComponentInstantiationException;
import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestDecryptingException;
import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestEncryptingException;
import com.brugnot.security.core.tools.PaddingOperation;
import com.brugnot.security.rest.commons.logging.DebugLogType;
import com.brugnot.security.rest.commons.logging.LoggedItem;
import com.brugnot.security.rest.commons.user.CandidateUser;
import com.brugnot.security.rest.commons.user.SigningUser;
import org.apache.commons.codec.binary.Base64;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Antonin on 27/11/2016.
 */
public class AbstractRequestEncryption implements HashedRestCanonicalRequestEncryptor, HashedRestCanonicalRequestDecryptor{


    /**
     * Encrypt Key Cipher Algorithm to use
     */
    private String encryptKeyCipherAlgorithm;

    /**
     * Request Cipher Algorithm to use
     */
    private  String requestCipherAlgorithm;

    /**
     * Algorithm Parameters (used because we are using AES Keys)
     */
    private  IvParameterSpec ivspecToUse;

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRequestEncryption.class);

    /**
     * Constructor
     * @param encryptKeyCipherAlgorithm
     * @param requestCipherAlgorithm
     */
    public AbstractRequestEncryption(String encryptKeyCipherAlgorithm, String requestCipherAlgorithm) {
        this.encryptKeyCipherAlgorithm = encryptKeyCipherAlgorithm;
        this.requestCipherAlgorithm = requestCipherAlgorithm;
        this.ivspecToUse = new IvParameterSpec(new byte[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
    }

    @Profiled
    public EncryptionWrapper encryptHashedRestCanonicalRequest(SigningUser user, String hashedRestCanonicalRequest) throws HashedRestCanonicalRequestEncryptingException {


        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"hashed canonical request to encrypt",LoggedItem.STRING,hashedRestCanonicalRequest));
        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"signing user",LoggedItem.COMPLEX_OBJECT,user));

        long startTime = System.currentTimeMillis();

        Cipher encryptKeyCipher;
        Cipher requestCipher;
        SecretKey encryptKey;

        //Creating ciphers that we will use and symmetrical request encryption key
        try {
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"encryptKeyCipherAlgorithm",LoggedItem.STRING,encryptKeyCipherAlgorithm));
            encryptKeyCipher = createCipher(encryptKeyCipherAlgorithm);
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"requestCipherAlgorithm",LoggedItem.STRING,requestCipherAlgorithm));
            requestCipher = createCipher(requestCipherAlgorithm);
            //Create symmetrical request encryption key
            encryptKey= generateRequestEncryptionKey("AES");
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"request encrypt Key to use",LoggedItem.STRING,Arrays.toString(encryptKey.getEncoded())));
        } catch (CryptoComponentInstantiationException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while instantiating one of the crypto components",e);
        }

        //Init Cipher for request encrypt
        try {
            requestCipher.init(Cipher.ENCRYPT_MODE, encryptKey,ivspecToUse);
        } catch (InvalidKeyException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while initializing the request cipher using the generated symmetrical key",e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while initializing the request cipher using the generated algorithm parameter",e);
        }

        //Init Cipher for Request Encrypt Key
        try {
            encryptKeyCipher.init(Cipher.ENCRYPT_MODE, user.getPrivateKey());
        } catch (InvalidKeyException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while initializing the encryptKey cipher using the user private key",e);
        }

        //Encrypt the Request
        byte[] encryptedHashedRequest;
        try {
            byte[] hashedRequestAsByteArray = hashedRestCanonicalRequest.getBytes();
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"clear Hashed Request As Byte Array",LoggedItem.STRING, Arrays.toString(hashedRequestAsByteArray)));
            encryptedHashedRequest = requestCipher.doFinal(hashedRequestAsByteArray);
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"encrypted Hashed Request As Byte Array",LoggedItem.STRING, Arrays.toString(encryptedHashedRequest)));
        } catch (IllegalBlockSizeException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while encrypting the hashedRestCanonicalRequest",e);
        } catch (BadPaddingException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while encrypting the hashedRestCanonicalRequest",e);
        }

        //Encrypt the Request symmetrical Encrypt Key
        byte[] encryptedKey;
        try {
            encryptedKey = encryptKeyCipher.doFinal(encryptKey.getEncoded());
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"encrypted encrypt key As Byte Array",LoggedItem.STRING, Arrays.toString(encryptedKey)));
        } catch (IllegalBlockSizeException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while encrypting the key",e);
        } catch (BadPaddingException e) {
            throw new HashedRestCanonicalRequestEncryptingException("Error while encrypting the key",e);
        }

        //Encode as base64 the encrypted contents
        String encryptedKeyAsBase64 = Base64.encodeBase64String(encryptedKey);
        LOGGER.debug(createItemDebugLog(DebugLogType.OUTPUT,"encrypted Key As Base64",LoggedItem.STRING,encryptedKeyAsBase64));

        String encryptedHashedRequestAsBase64 = Base64.encodeBase64String(encryptedHashedRequest);
        LOGGER.debug(createItemDebugLog(DebugLogType.OUTPUT,"encrypted Hashed Request As Base64",LoggedItem.STRING,encryptedHashedRequestAsBase64));

        return new EncryptionWrapper(encryptedHashedRequestAsBase64,
                System.currentTimeMillis()-startTime,
                encryptedKeyAsBase64);
    }

    @Profiled
    public DecryptionWrapper decryptHashedRestCanonicalRequest(CandidateUser user, String cryptedHashedRestCanonicalRequest) throws HashedRestCanonicalRequestDecryptingException {

        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"hashed canonical request to decrypt",LoggedItem.STRING,cryptedHashedRestCanonicalRequest));
        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"candidate user",LoggedItem.COMPLEX_OBJECT,user));

        long startTime = System.currentTimeMillis();

        Cipher encryptKeyCipher;
        Cipher requestCipher;

        try {
            encryptKeyCipher = createCipher(encryptKeyCipherAlgorithm);
            requestCipher = createCipher(requestCipherAlgorithm);
        } catch (CryptoComponentInstantiationException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while instantiating one of the crypto components",e);
        }

        try {
            encryptKeyCipher.init(Cipher.DECRYPT_MODE, user.getPublicKey());
        } catch (InvalidKeyException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while initializing the key cipher using the user public key",e);
        }

        byte[] decryptedKey;
        try {
            decryptedKey = encryptKeyCipher.doFinal(Base64.decodeBase64(user.getEncryptedRequestKey()));
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"clear encrypt key As Byte Array",LoggedItem.STRING, Arrays.toString(decryptedKey)));
        } catch (IllegalBlockSizeException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while decrypting the Key",e);
        } catch (BadPaddingException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while decrypting the Key",e);
        }

        PaddingOperation paddingOperation = new PaddingOperation();
        decryptedKey = paddingOperation.unPadAESKeyByteArray(decryptedKey);
        LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"clear encrypt key As Byte Array with leading zero padding removed",LoggedItem.STRING, Arrays.toString(decryptedKey)));
        SecretKey originalKey = new SecretKeySpec(decryptedKey, 0, decryptedKey.length, "AES");

        try {
            requestCipher.init(Cipher.DECRYPT_MODE, originalKey,ivspecToUse);
        } catch (InvalidKeyException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while initializing the request cipher using the original decrypted key",e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while initializing the request cipher using the generated algorithm parameter",e);
        }

        byte[] decrypted;
        try {
            byte[] cryptHashedRestCanonicalRequestAsByteArray = Base64.decodeBase64(cryptedHashedRestCanonicalRequest);
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"encrypted Hashed Request As Byte Array",LoggedItem.STRING, Arrays.toString(cryptHashedRestCanonicalRequestAsByteArray)));
            decrypted = requestCipher.doFinal(cryptHashedRestCanonicalRequestAsByteArray);
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"clear Hashed Request As Byte Array",LoggedItem.STRING, Arrays.toString(decrypted)));
        } catch (IllegalBlockSizeException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while decrypting the encrypted hashedRestCanonicalRequest",e);
        } catch (BadPaddingException e) {
            throw new HashedRestCanonicalRequestDecryptingException("Error while decrypting the encrypted hashedRestCanonicalRequest",e);
        }

        return new DecryptionWrapper(new String(decrypted),System.currentTimeMillis()-startTime);
    }

    /**
     * Create a Cipher with the provided Algorithm
     * @param cipherAlgorithm
     * @return instantiated Cipher
     * @throws CryptoComponentInstantiationException
     */
    private Cipher createCipher(String cipherAlgorithm) throws CryptoComponentInstantiationException {
        try {
            return  Cipher.getInstance(cipherAlgorithm);
        }catch(NoSuchAlgorithmException e){
            throw new CryptoComponentInstantiationException("Error while instantiating the request encryption components",e);
        }catch(NoSuchPaddingException e){
            throw new CryptoComponentInstantiationException("Error while instantiating the request encryption components",e);
        }

    }

    /**
     * Generate the Request Encryption Key
     * @param keyAlgorithm
     * @return symmetrical key
     * @throws CryptoComponentInstantiationException
     */
    private SecretKey generateRequestEncryptionKey(String keyAlgorithm) throws CryptoComponentInstantiationException {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(keyAlgorithm);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoComponentInstantiationException("Error while generating the request encryption key",e);
        }

    }

    protected String createItemDebugLog(DebugLogType debugLogType, String itemName, LoggedItem loggedItem, Object object){

        StringBuilder debugLogBuilder = new StringBuilder();

        debugLogBuilder.append(debugLogType.getLoggingPrefix()+"=");

        debugLogBuilder.append(itemName);

        if(loggedItem.isLogSize()){
            debugLogBuilder.append(",size="+loggedItem.getObjectSize(object));

        }

        if(loggedItem.isLogValue()){

            debugLogBuilder.append(",value="+object);

        }

        return debugLogBuilder.toString();


    }
}
