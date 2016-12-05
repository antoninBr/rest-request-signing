package com.brugnot.security.core.crypt;

/**
 * Created by Antonin on 28/11/2016.
 */
public class EncryptionWrapper {


    private String request;

    private String encryptKey;

    public EncryptionWrapper(String request, String encryptKey) {
        this.request = request;
        this.encryptKey = encryptKey;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }
}
