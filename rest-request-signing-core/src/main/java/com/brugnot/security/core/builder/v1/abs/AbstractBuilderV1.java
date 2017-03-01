package com.brugnot.security.core.builder.v1.abs;


import com.brugnot.security.core.builder.versionning.BuilderVersion;
import com.brugnot.security.core.builder.versionning.VersionedBuilder;
import com.brugnot.security.core.builder.versionning.impl.Version1;
import com.brugnot.security.core.exception.digest.HashCreationException;
import com.brugnot.security.core.tools.ContentHashProvider;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;
import com.brugnot.security.rest.commons.logging.DebugLogType;
import com.brugnot.security.rest.commons.logging.LoggedItem;
import org.perf4j.aop.Profiled;

import java.security.DigestException;

/**
 * Abstract Builder (V1)
 * Created by Antonin on 03/12/2016.
 */
public abstract class AbstractBuilderV1 implements VersionedBuilder {

    /**
     * Constants
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Get the Builder Version
     * @return version
     */
    public BuilderVersion getVersion() {
        return new Version1();
    }

    /**
     * Ge the Digest of the input data using the provided HashAlgorithm
     * @param hashAlgorithm
     * @param data
     * @return the hex String digest of the data
     * @throws DigestException
     */
    @Profiled
    protected String getHashOfData(HashAlgorithm hashAlgorithm, byte[] data) throws HashCreationException {

        ContentHashProvider contentHashProvider = new ContentHashProvider(hashAlgorithm);

        return contentHashProvider.createHexaHash(data);

    }

    /**
     * Create Canonical Entity (parameters between line separators)
     * @param parameters
     * @return canonicalEntity
     */
    protected String buildCanonicalEntity(String... parameters){

        StringBuilder canonicalEntity = new StringBuilder();

        for(String parameter : parameters){
            canonicalEntity.append(parameter);
            canonicalEntity.append(LINE_SEPARATOR);
        }

        return canonicalEntity.toString();
    }

    protected String createItemDebugLog(DebugLogType debugLogType, String itemName, LoggedItem loggedItem, Object object){

        StringBuilder debugLogBuilder = new StringBuilder();

        debugLogBuilder.append(debugLogType.getLoggingPrefix()+"=");

        debugLogBuilder.append(itemName);

        if(loggedItem.isLogSize()){
            debugLogBuilder.append(",size="+loggedItem.getObjectSize(object));

        }

        if(loggedItem.isLogValue()){

            debugLogBuilder.append(",value="+object);

        }

        return debugLogBuilder.toString();


    }
}
