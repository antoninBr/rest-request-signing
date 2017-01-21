package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestCanonicalHeadersBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestCanonicalHeadersBuildingException;

import java.util.List;
import java.util.Map;

/**
 * Created by Antonin on 03/12/2016.
 */
public class RestCanonicalHeadersBuilderV1 extends AbstractBuilderV1 implements RestCanonicalHeadersBuilder {

    public String buildRestCanonicalHeaders(Map<String, List<String>> headers) throws RestCanonicalHeadersBuildingException {
        return "";
    }
}
