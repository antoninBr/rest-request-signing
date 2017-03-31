package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestCanonicalHeadersBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestCanonicalHeadersBuildingException;
import com.brugnot.security.rest.commons.logging.DebugLogType;
import com.brugnot.security.rest.commons.logging.LoggedItem;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Rest Canonical Headers Builder (V1)
 * Created by Antonin on 03/12/2016.
 */
public class RestCanonicalHeadersBuilderV1 extends AbstractBuilderV1 implements RestCanonicalHeadersBuilder {

    /**
     * Logger for this builder
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestCanonicalHeadersBuilderV1.class);

    @Profiled
    public String buildRestCanonicalHeaders(Map<String, List<String>> headers) throws RestCanonicalHeadersBuildingException {

        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"headers", LoggedItem.MAP,headers));

        StringBuilder canonicalHeadersBuilder = new StringBuilder();

        SortedSet<String> headerNames = new TreeSet<String>(headers.keySet());

        for (String headerName : headerNames) {

            canonicalHeadersBuilder.append(headerName.toLowerCase().trim());

            List<String> headerValues = headers.get(headerName);
            java.util.Collections.sort(headerValues);

            for(String headerValue : headerValues){
                canonicalHeadersBuilder.append(trimAll(headerValue));
            }
        }

        String canonicalHeaders = canonicalHeadersBuilder.toString();

        LOGGER.debug(createItemDebugLog(DebugLogType.OUTPUT,"canonicalHeaders", LoggedItem.STRING,canonicalHeaders));

        return canonicalHeaders;
    }

    /**
     * Remove all space before and after and convert spaces between in single spaces
     * @param headerValue
     * @return trim header value
     */
    private String trimAll(String headerValue) {
        //TODO : implement this
        return headerValue;
    }
}
