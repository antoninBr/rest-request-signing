package com.brugnot.security.core.builder.versionning.impl;

import com.brugnot.security.core.builder.versionning.BuilderVersion;

/**
 * Created by Antonin on 03/12/2016.
 */
public class Version1 implements BuilderVersion {

    private String version;

    public Version1() {
        version = "1";
    }

    public String getVersion() {
        return version;
    }

}
