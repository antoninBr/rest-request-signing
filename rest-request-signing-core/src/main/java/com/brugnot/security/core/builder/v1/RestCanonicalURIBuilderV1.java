package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestCanonicalURIBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestCanonicalURIBuildingException;
import com.brugnot.security.rest.commons.logging.DebugLogType;
import com.brugnot.security.rest.commons.logging.LoggedItem;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Rest Canonical URI Builder (V1)
 * Created by Antonin on 12/12/2016.
 */
public class RestCanonicalURIBuilderV1 extends AbstractBuilderV1 implements RestCanonicalURIBuilder{

    /**
     * Logger for this builder
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestCanonicalURIBuilderV1.class);

    @Profiled
    public String buildRestCanonicalURI(String URIAbsolutePath) throws RestCanonicalURIBuildingException {

        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"URIAbsolutePath", LoggedItem.STRING,URIAbsolutePath));

        try {
            URI uri = new URI(URIAbsolutePath);
            uri = uri.normalize();
            String canonicalURI = uri.getPath();
            LOGGER.debug(createItemDebugLog(DebugLogType.OUTPUT,"canonicalURI", LoggedItem.STRING,canonicalURI));
            return canonicalURI;
        } catch (URISyntaxException e) {
            throw new RestCanonicalURIBuildingException("Error while Building the rest canonical URI",e);
        }

    }
}
