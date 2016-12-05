package com.brugnot.security.core.com.brugnot.security.core.builder;

import com.brugnot.security.core.com.brugnot.security.core.builder.versionning.VersionnedBuilder;
import com.brugnot.security.core.exception.builder.RestCanonicalURIBuildingException;

import java.net.URI;

/**
 * Rest Canonical URI Builder
 *
 * Normalize URI paths according to RFC 3986
 *
 * Created by Antonin on 27/11/2016.
 */
public interface RestCanonicalURIBuilder  extends VersionnedBuilder {

    /**
     * Build the Rest Canonical URI Given Provided Parameters
     * @param URIAbsolutePath
     * @return canonical URI (Normalized  according to RFC 3986)
     * @throws RestCanonicalURIBuildingException
     */
    URI buildRestCanonicalURI(String URIAbsolutePath) throws RestCanonicalURIBuildingException;
}
