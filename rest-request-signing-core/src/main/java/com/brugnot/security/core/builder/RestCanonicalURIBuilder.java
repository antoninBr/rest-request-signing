package com.brugnot.security.core.builder;

import com.brugnot.security.core.builder.versionning.VersionedBuilder;
import com.brugnot.security.core.exception.builder.RestCanonicalURIBuildingException;

import java.net.URI;

/**
 * Rest Canonical URI Builder
 *
 * Normalize URI paths according to RFC 3986
 *
 * Created by Antonin on 27/11/2016.
 */
public interface RestCanonicalURIBuilder  extends VersionedBuilder {

    /**
     * Build the Rest Canonical URI Given Provided Parameters
     * @param URIAbsolutePath
     * @return canonical URI (Normalized  according to RFC 3986)
     * @throws RestCanonicalURIBuildingException
     */
    String buildRestCanonicalURI(String URIAbsolutePath) throws RestCanonicalURIBuildingException;
}
