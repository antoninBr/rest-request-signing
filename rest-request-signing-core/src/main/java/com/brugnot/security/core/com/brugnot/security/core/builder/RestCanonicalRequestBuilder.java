package com.brugnot.security.core.com.brugnot.security.core.builder;

import com.brugnot.security.core.HashAlgorithm;
import com.brugnot.security.core.com.brugnot.security.core.builder.versionning.VersionnedBuilder;
import com.brugnot.security.core.exception.builder.RestCanonicalRequestBuildingException;

/**
 * Rest Canonical Request Builder
 *
 * Created by Antonin on 27/11/2016.
 */
public interface RestCanonicalRequestBuilder  extends VersionnedBuilder{

    /**
     * Build the Rest Canonical Request Given Provided Parameters
     * @param httpRequestMethod
     * @param canonicalURI
     * @param canonicalQueryString
     * @param canonicalHeaders
     * @param signedHeaders
     * @param requestPayload
     * @return the Rest Canonical Request
     * @throws RestCanonicalRequestBuildingException
     */
    String buildRestCanonicalRequest(String httpRequestMethod, String canonicalURI, String canonicalQueryString, String canonicalHeaders, String signedHeaders, String requestPayload) throws RestCanonicalRequestBuildingException;

    /**
     * Build the Hashed Rest Canonical Request Given Provided Parameters
     * @param hashAlgorithm
     * @param httpRequestMethod
     * @param canonicalURI
     * @param canonicalQueryString
     * @param canonicalHeaders
     * @param signedHeaders
     * @param requestPayload
     * @return the lower case HEX Value of the hashed Rest Canonical Request
     * @throws RestCanonicalRequestBuildingException
     */
    String buildHashedRestCanonicalRequest(HashAlgorithm hashAlgorithm, String httpRequestMethod, String canonicalURI, String canonicalQueryString, String canonicalHeaders, String signedHeaders, String requestPayload) throws RestCanonicalRequestBuildingException;
}
