package com.brugnot.security.core;

/**
 * Created by a.brugnot on 27/03/2017.
 */
public enum TestConstant {

    VALID_USERNAME("anto"),
    VALID_KEYSTORE_PASSWORD("pwd"),
    VALID_USER_KEY_PASSWORD("pwd"),
    VALID_USER_KEY_ALIAS("rrs4j_sample"),
    KEYSTORE_P12_FILE("keystore.p12"),
    TRUSTSTORE_JKS_FILE("truststore.jks"),
    VALID_TRUSTSTORE_PASSWORD("pwd");

    private String value;

    TestConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
