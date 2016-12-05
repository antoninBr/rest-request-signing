package com.brugnot.security.core.com.brugnot.security.core.builder.versionning;

/**
 * Created by Antonin on 27/11/2016.
 */
public interface BuilderVersion {

    /**
     * Get the Version as a String
     * @return version
     */
    String getVersion();

    /**
     * Get the Hash
     * @return hash
     */
    String getVersionHash();
}
