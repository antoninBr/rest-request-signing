package com.brugnot.security.core.crypt.wrapper;

import com.brugnot.security.core.crypt.wrapper.abs.AbstractCryptWrapper;

/**
 * Created by Antonin on 28/11/2016.
 */
public class EncryptionWrapper extends AbstractCryptWrapper {

    private String encryptKey;

    public EncryptionWrapper(String processedRequest, long operationProcessingTimeMs, String encryptKey) {
        super(processedRequest, operationProcessingTimeMs);
        this.encryptKey = encryptKey;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

}
