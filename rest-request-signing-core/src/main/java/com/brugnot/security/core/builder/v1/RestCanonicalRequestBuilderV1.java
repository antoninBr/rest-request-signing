package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestCanonicalRequestBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestCanonicalRequestBuildingException;
import com.brugnot.security.core.exception.digest.HashCreationException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;
import com.brugnot.security.rest.commons.logging.DebugLogType;
import com.brugnot.security.rest.commons.logging.LoggedItem;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rest Canonical Request Builder (V1)
 * Created by Antonin on 12/12/2016.
 */
public class RestCanonicalRequestBuilderV1 extends AbstractBuilderV1 implements RestCanonicalRequestBuilder{

    /**
     * Logger for this builder
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestCanonicalRequestBuilderV1.class);

    @Profiled
    public String buildRestCanonicalRequest(String httpRequestMethod, String canonicalURI, String canonicalQueryString, String canonicalHeaders, String signedHeaders, String requestPayload) throws RestCanonicalRequestBuildingException {

        return buildCanonicalEntity(httpRequestMethod,canonicalURI,canonicalQueryString,canonicalHeaders,signedHeaders,requestPayload);
    }

    @Profiled
    public String buildHashedRestCanonicalRequest(HashAlgorithm hashAlgorithm, String httpRequestMethod, String canonicalURI, String canonicalQueryString, String canonicalHeaders, String signedHeaders, String requestPayload) throws RestCanonicalRequestBuildingException {

        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"hashAlgorithm", LoggedItem.STRING,hashAlgorithm.getHashAlgorithm()));
        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"httpRequestMethod", LoggedItem.STRING,httpRequestMethod));
        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"canonicalURI", LoggedItem.STRING,canonicalURI));
        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"canonicalQueryString", LoggedItem.STRING,canonicalQueryString));
        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"canonicalHeaders", LoggedItem.STRING,canonicalHeaders));
        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"signedHeaders", LoggedItem.STRING,signedHeaders));
        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"requestPayload", LoggedItem.STRING,requestPayload));

        String restCanonicalRequest = buildRestCanonicalRequest(httpRequestMethod,canonicalURI,canonicalQueryString,canonicalHeaders,signedHeaders,requestPayload);

        String hashedRestCanonicalRequest;

        try {
            hashedRestCanonicalRequest = getHashOfData(hashAlgorithm,restCanonicalRequest.getBytes());
        } catch (HashCreationException e) {
            throw new RestCanonicalRequestBuildingException("Error while building the hashed rest canonical request",e);
        }

        return hashedRestCanonicalRequest;
    }
}
