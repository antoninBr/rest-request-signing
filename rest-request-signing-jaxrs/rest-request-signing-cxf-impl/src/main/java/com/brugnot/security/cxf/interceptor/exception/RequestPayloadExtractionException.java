package com.brugnot.security.cxf.interceptor.exception;

/**
 * Created by Antonin on 15/12/2016.
 */
public class RequestPayloadExtractionException extends Exception {

    public RequestPayloadExtractionException(String message) {
        super(message);
    }

    public RequestPayloadExtractionException(String message, Throwable cause) {
        super(message, cause);
    }


}
