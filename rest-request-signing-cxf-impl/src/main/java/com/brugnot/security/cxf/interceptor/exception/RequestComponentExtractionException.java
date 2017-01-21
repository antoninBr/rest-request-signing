package com.brugnot.security.cxf.interceptor.exception;

/**
 * Request Component Extraction Exception
 * TODO : Make this exception global
 * Created by Antonin on 20/01/2017.
 */
public class RequestComponentExtractionException extends Exception {

    /**
     * Constructor
     * @param message
     */
    public RequestComponentExtractionException(String message) {
        super(message);
    }
}
