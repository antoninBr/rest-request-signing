package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestCanonicalRequestBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestCanonicalRequestBuildingException;
import com.brugnot.security.core.exception.digest.HashCreationException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;

/**
 * Created by Antonin on 12/12/2016.
 */
public class RestCanonicalRequestBuilderV1 extends AbstractBuilderV1 implements RestCanonicalRequestBuilder{

    public String buildRestCanonicalRequest(String httpRequestMethod, String canonicalURI, String canonicalQueryString, String canonicalHeaders, String signedHeaders, String requestPayload) throws RestCanonicalRequestBuildingException {

        return buildCanonicalEntity(httpRequestMethod,canonicalURI,canonicalQueryString,canonicalHeaders,signedHeaders,requestPayload);
    }

    public String buildHashedRestCanonicalRequest(HashAlgorithm hashAlgorithm, String httpRequestMethod, String canonicalURI, String canonicalQueryString, String canonicalHeaders, String signedHeaders, String requestPayload) throws RestCanonicalRequestBuildingException {

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
