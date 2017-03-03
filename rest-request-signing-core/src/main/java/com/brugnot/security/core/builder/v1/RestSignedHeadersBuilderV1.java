package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestSignedHeadersBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestSignedHeadersBuildingException;
import com.brugnot.security.rest.commons.logging.DebugLogType;
import com.brugnot.security.rest.commons.logging.LoggedItem;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Rest Signed Headers Builder V1
 * Created by Antonin on 12/12/2016.
 */
public class RestSignedHeadersBuilderV1 extends AbstractBuilderV1 implements RestSignedHeadersBuilder {

    /**
     * Logger for this builder
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestSignedHeadersBuilderV1.class);

    @Profiled
    public String buildRestSignedHeaders(Set<String> signedHeaders) throws RestSignedHeadersBuildingException {

        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"restRequestPayload", LoggedItem.SET,signedHeaders));

        SortedSet<String> sortedSignedHeaders = new TreeSet<String>(signedHeaders);

        StringBuilder canonicalSignedHeadersBuilder = new StringBuilder();

        for(String signedHeader : sortedSignedHeaders){
            canonicalSignedHeadersBuilder.append(signedHeader);
        }

        String canonicalSignedHeaders = canonicalSignedHeadersBuilder.toString();

        LOGGER.debug(createItemDebugLog(DebugLogType.OUTPUT,"canonicalSignedHeaders", LoggedItem.STRING,canonicalSignedHeaders));

        return canonicalSignedHeaders;
    }
}
