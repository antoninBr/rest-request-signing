package com.brugnot.security.core.builder.v1.abs;


import com.brugnot.security.core.builder.versionning.BuilderVersion;
import com.brugnot.security.core.builder.versionning.VersionnedBuilder;

/**
 * Created by Antonin on 03/12/2016.
 */
public abstract class AbstractBuilderV1 implements VersionnedBuilder{

    public BuilderVersion getVersion() {
        return null;
    }
}
