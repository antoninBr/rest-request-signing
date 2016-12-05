package com.brugnot.security.core.com.brugnot.security.core.builder;

import com.brugnot.security.core.com.brugnot.security.core.builder.versionning.VersionnedBuilder;
import com.brugnot.security.core.exception.builder.RestCanonicalQueryStringBuildingException;

import java.util.Map;

/**
 * Rest Canonical Query String Builder
 *
 * Created by Antonin on 27/11/2016.
 */
public interface RestCanonicalQueryStringBuilder extends VersionnedBuilder{

    /**
     * Build the Rest Canonical Query String Given Provided Query Parameters
     * @param queryParameters
     * @return rest canonical query string
     * @throws RestCanonicalQueryStringBuildingException
     */
    String buildRestCanonicalQueryString(Map<String,String> queryParameters) throws RestCanonicalQueryStringBuildingException;
}
