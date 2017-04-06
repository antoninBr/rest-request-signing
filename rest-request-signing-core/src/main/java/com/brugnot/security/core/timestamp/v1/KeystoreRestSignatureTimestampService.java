package com.brugnot.security.core.timestamp.v1;

import com.brugnot.security.core.crypt.impl.AbstractCrypo;
import com.brugnot.security.core.exception.timestamp.RestSignatureTimestampException;
import com.brugnot.security.core.timestamp.RestSignatureTimestampService;
import com.brugnot.security.core.tools.KeystoreLoader;

import javax.inject.Inject;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Antonin on 05/04/2017.
 * TODO: depending the configuration check that the timestamp is not older than xx seconds
 * TODO : also depending on the configuration check if the timestamp has been already validated during the time interval (create a TimestampOccurrencesService with hazelcast impl (external module) and local test impl)
 * TODO : Strong Tests !!!!
 */
public class KeystoreRestSignatureTimestampService extends AbstractCrypo implements RestSignatureTimestampService {

    /**
     * DateFormat to Use for timestamp
     */
    private final static SimpleDateFormat timestampDataFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");

    private KeystoreLoader keystoreLoader;

    private String keyAlias;

    private String keyPassword;

    @Override
    public String createTimestamp() throws RestSignatureTimestampException {

        //TODO: use crypto methods to encode the created timestamp (using key in keystore)
        String currentTimestamp = getCurrentNormalizedTimestamp();
        Key timestampSymmetricKey = getTimestampSymmetricKey();

        return null;
    }

    @Override
    public boolean validateTimestamp(String encodedTimestamp) throws RestSignatureTimestampException {

        //TODO: use crypto methods to decode the created timestamp (using key in keystore)
        return false;
    }

    //TODO : Move this method to AbstractCrypto
    private Key getTimestampSymmetricKey() throws RestSignatureTimestampException {

        try {
            return keystoreLoader.getKeyStore().getKey(keyAlias,keyPassword.toCharArray());
        } catch (KeyStoreException e) {
           throw new RestSignatureTimestampException("",e);
        } catch (NoSuchAlgorithmException e) {
            throw new RestSignatureTimestampException("",e);
        } catch (UnrecoverableKeyException e) {
            throw new RestSignatureTimestampException("",e);
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

}
