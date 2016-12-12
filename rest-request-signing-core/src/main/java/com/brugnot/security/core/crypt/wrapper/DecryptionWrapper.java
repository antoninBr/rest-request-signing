package com.brugnot.security.core.crypt.wrapper;

import com.brugnot.security.core.crypt.wrapper.abs.AbstractCryptWrapper;

/**
 * Created by Antonin on 09/12/2016.
 */
public class DecryptionWrapper extends AbstractCryptWrapper {

    public DecryptionWrapper(String processedRequest, long operationProcessingTimeMs) {
        super(processedRequest, operationProcessingTimeMs);
    }
}
