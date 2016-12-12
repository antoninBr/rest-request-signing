package com.brugnot.security.core.crypt.wrapper.abs;

/**
 * Created by Antonin on 09/12/2016.
 */
public abstract class AbstractCryptWrapper {

    /**
     * The processed Hashed Canonical Request
     */
    protected String processedRequest;

    /**
     * Yhe Crypto Operation Processing Time in Ms
     */
    protected long operationProcessingTimeMs;

    public AbstractCryptWrapper(String processedRequest, long operationProcessingTimeMs) {
        this.processedRequest = processedRequest;
        this.operationProcessingTimeMs = operationProcessingTimeMs;
    }

    public String getProcessedRequest() {
        return processedRequest;
    }

    public long getOperationProcessingTimeMs() {
        return operationProcessingTimeMs;
    }
}
