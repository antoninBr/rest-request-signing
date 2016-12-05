package com.brugnot.security.core.com.brugnot.security.core.builder;

import com.brugnot.security.core.com.brugnot.security.core.builder.versionning.VersionnedBuilder;
import com.brugnot.security.core.exception.builder.RestCanonicalHeadersBuildingException;

import java.util.List;
import java.util.Map;

/**
 * Rest Canonical Headers Builder
 *
 * Created by Antonin on 27/11/2016.
 */
public interface RestCanonicalHeadersBuilder extends VersionnedBuilder {

    /**
     * Build the Rest Canonical Headers Given the Provided headers
     * @param headers
     * @return rest canonical headers
     * @throws RestCanonicalHeadersBuildingException
     */
    String buildRestCanonicalHeaders(Map<String, List<String>> headers) throws RestCanonicalHeadersBuildingException;

}
