package com.brugnot.security.rest.commons.logging;

/**
 * Created by Antonin on 24/01/2017.
 */
public enum DebugLogType {

    INPUT_ARGUMENT("input-argument"),
    OUTPUT("output"),
    PROCESSING("processing");

    private String loggingPrefix;

    DebugLogType(String loggingPrefix) {
        this.loggingPrefix = loggingPrefix;
    }

    public String getLoggingPrefix() {
        return loggingPrefix;
    }
}
