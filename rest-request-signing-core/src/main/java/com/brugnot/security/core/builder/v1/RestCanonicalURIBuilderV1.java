package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestCanonicalURIBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestCanonicalURIBuildingException;
import org.perf4j.aop.Profiled;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Rest Canonical URI Builder (V1)
 * Created by Antonin on 12/12/2016.
 */
public class RestCanonicalURIBuilderV1 extends AbstractBuilderV1 implements RestCanonicalURIBuilder{

    @Profiled
    public URI buildRestCanonicalURI(String URIAbsolutePath) throws RestCanonicalURIBuildingException {
        try {
            return new URI(URIAbsolutePath);
        } catch (URISyntaxException e) {
            throw new RestCanonicalURIBuildingException("",e);
        }
    }
}
