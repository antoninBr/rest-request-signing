package com.brugnot.security.core.builder;

import com.brugnot.security.core.builder.versionning.VersionedBuilder;
import com.brugnot.security.core.exception.builder.RestCanonicalQueryStringBuildingException;

/**
 * Rest Canonical Query String Builder
 *
 * Created by Antonin on 27/11/2016.
 */
public interface RestCanonicalQueryStringBuilder extends VersionedBuilder {

    /**
     * Build the Rest Canonical Query String Given Provided Query Parameters
     * @param queryParameters
     * @return rest canonical query string
     * @throws RestCanonicalQueryStringBuildingException
     */
    String buildRestCanonicalQueryString(String queryParameters) throws RestCanonicalQueryStringBuildingException;
}
