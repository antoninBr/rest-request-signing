package com.brugnot.security.core.timestamp.v1;

import com.brugnot.security.core.crypt.impl.AbstractCrypo;
import com.brugnot.security.core.exception.crypt.CryptoComponentInstantiationException;
import com.brugnot.security.core.exception.timestamp.RestSignatureTimestampException;
import com.brugnot.security.core.timestamp.RestSignatureTimestampService;
import com.brugnot.security.core.tools.KeystoreLoader;
import com.brugnot.security.rest.commons.logging.DebugLogType;
import com.brugnot.security.rest.commons.logging.LoggedItem;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.inject.Inject;
import java.security.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Antonin on 05/04/2017.
 * TODO: depending the configuration check that the timestamp is not older than xx seconds
 * TODO : also depending on the configuration check if the timestamp has been already validated during the time interval (create a TimestampOccurrencesService with hazelcast impl (external module) and local test impl)
 * TODO : Strong Tests !!!!
 */
public class KeystoreRestSignatureTimestampService extends AbstractCrypo implements RestSignatureTimestampService {

    /**
     * DateFormat to use for timestamp
     */
    private final static SimpleDateFormat timestampDataFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KeystoreRestSignatureTimestampService.class);

    /**
     * Keystore Loader
     */
    private KeystoreLoader keystoreLoader;

    /**
     * Key Alias inside Keystore
     */
    private String keyAlias;

    /**
     * Key Password
     */
    private String keyPassword;

    /**
     * Cipher Algorithm
     */
    private String cipherAlgorithm;

    @Override
    public String createTimestamp() throws RestSignatureTimestampException {

        String currentTimestamp = getCurrentNormalizedTimestamp();

        LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"current Timestamp",LoggedItem.STRING,currentTimestamp));

        String encryptedTimestampAsBase64 = Base64.encodeBase64String(encryptTimestamp(currentTimestamp));

        LOGGER.debug(createItemDebugLog(DebugLogType.OUTPUT,"encrypted Timestamp As Base64", LoggedItem.STRING,encryptedTimestampAsBase64));

        return encryptedTimestampAsBase64;
    }

    @Override
    public boolean validateTimestamp(String encodedTimestamp) throws RestSignatureTimestampException {

        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"encoded as base64 encrypted Timestamp",LoggedItem.STRING,encodedTimestamp));

        byte[] encryptedTimestampAsByteArray = Base64.decodeBase64(encodedTimestamp);

        LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"encrypted Timestamp as Byte Array",LoggedItem.STRING,Arrays.toString(encryptedTimestampAsByteArray)));
        String receivedTimestamp = decryptTimestamp(encryptedTimestampAsByteArray);

        LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"received timestamp",LoggedItem.STRING,receivedTimestamp));

        if(validateTimestampTowardsTime(receivedTimestamp)){
            if(validateTimestampTowardsOccurrences(receivedTimestamp)){
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }

    private boolean validateTimestampTowardsOccurrences(String receivedTimestamp) {
        LOGGER.debug("Validating timestamp toward occurrences");
        return false;
    }

    private boolean validateTimestampTowardsTime(String receivedTimestamp) {
        LOGGER.debug("Validating timestamp toward time");
        return false;
    }



    /**
     * Encrypt the provided timestamp (as String)
     * @param currentTimestamp
     * @return byte array
     * @throws RestSignatureTimestampException
     */
    private byte[] encryptTimestamp(String currentTimestamp) throws RestSignatureTimestampException {
        Key timestampSymmetricKey = getTimestampSymmetricKey();
        Cipher timestampEncryptCipher;

        try {
            timestampEncryptCipher = createCipher(cipherAlgorithm);
        } catch (CryptoComponentInstantiationException e) {
            throw new RestSignatureTimestampException("Error while instantiating crypto components for timestamp encryption",e);
        }

        try {
            timestampEncryptCipher.init(Cipher.ENCRYPT_MODE, timestampSymmetricKey);
        } catch (InvalidKeyException e) {
            throw new RestSignatureTimestampException("Error while initializing cipher for timestamp encryption",e);
        }

        byte[] encryptedTimestampByteArray;

        try {
            byte[] currentTimestampAsByteArray = currentTimestamp.getBytes();
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"current Timestamp As Byte Array",LoggedItem.STRING, Arrays
                    .toString(currentTimestampAsByteArray)));
            encryptedTimestampByteArray = timestampEncryptCipher.doFinal(currentTimestampAsByteArray);
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"encrypted current Timestamp As Byte Array",LoggedItem.STRING, Arrays
                    .toString(encryptedTimestampByteArray)));
        } catch (IllegalBlockSizeException e) {
            throw new RestSignatureTimestampException("Error while crypt the timestamp",e);
        } catch (BadPaddingException e) {
            throw new RestSignatureTimestampException("Error while crypt the timestamp",e);
        }

        return encryptedTimestampByteArray;
    }



    /**
     * Decrypt the provided crypted timestamp (as byte array)
     * @param encryptedTimestampAsByteArray
     * @return decrypted timestamp
     */
    private String decryptTimestamp(byte[] encryptedTimestampAsByteArray) throws RestSignatureTimestampException {

        Key timestampSymmetricKey = getTimestampSymmetricKey();
        Cipher timestampDecryptCipher;

        try {
            timestampDecryptCipher = createCipher(cipherAlgorithm);
        } catch (CryptoComponentInstantiationException e) {
            throw new RestSignatureTimestampException("Error while instantiating crypto components for timestamp decryption",e);
        }

        try {
            timestampDecryptCipher.init(Cipher.DECRYPT_MODE, timestampSymmetricKey);
        } catch (InvalidKeyException e) {
            throw new RestSignatureTimestampException("Error while initializing cipher for timestamp decryption",e);
        }

        byte[] timestampAsByteArray;

        try {
            timestampAsByteArray = timestampDecryptCipher.doFinal(encryptedTimestampAsByteArray);
            LOGGER.debug(createItemDebugLog(DebugLogType.PROCESSING,"current Timestamp As Byte Array",LoggedItem.STRING, Arrays
                    .toString(timestampAsByteArray)));
        } catch (IllegalBlockSizeException e) {
            throw new RestSignatureTimestampException("Error while crypt the timestamp",e);
        } catch (BadPaddingException e) {
            throw new RestSignatureTimestampException("Error while crypt the timestamp",e);
        }

        return new String(timestampAsByteArray);

    }

    //TODO : Move this method to AbstractCrypto
    private Key getTimestampSymmetricKey() throws RestSignatureTimestampException {

        try {
            return keystoreLoader.getKeyStore().getKey(keyAlias,keyPassword.toCharArray());
        } catch (KeyStoreException e) {
           throw new RestSignatureTimestampException("Error while opening the configured keystore file",e);
        } catch (NoSuchAlgorithmException e) {
            throw new RestSignatureTimestampException("Wrong Algorithm on keystore key extraction",e);
        } catch (UnrecoverableKeyException e) {
            throw new RestSignatureTimestampException("Key can't be extracted from the configured keystore",e);
        }
    }

    /**
     * Get a normalizedCurrentDate
     * @return current Normalized Timestamp
     */
    private String getCurrentNormalizedTimestamp(){
        return timestampDataFormat.format(new Date());
    }

    /**
     * Get a normalizedCurrentDate
     * @return current Normalized Timestamp
     */
    private Date getDateFromNormalizedTimestamp(String normalizedTimestamp) throws RestSignatureTimestampException {
        try {
            return timestampDataFormat.parse(normalizedTimestamp);
        } catch (ParseException e) {
            throw new RestSignatureTimestampException("Could not parse the provided normalized timestamp",e);
        }
    }

    @Inject
    public void setKeystoreLoader(KeystoreLoader keystoreLoader) {
        this.keystoreLoader = keystoreLoader;
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
