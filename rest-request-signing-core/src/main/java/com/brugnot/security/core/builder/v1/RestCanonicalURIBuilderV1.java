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
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestCanonicalURIBuilderV1.class);

    @Profiled
    public URI buildRestCanonicalURI(String URIAbsolutePath) throws RestCanonicalURIBuildingException {

        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"URIAbsolutePath", LoggedItem.STRING,URIAbsolutePath));

        try {
            return new URI(URIAbsolutePath);
        } catch (URISyntaxException e) {
            throw new RestCanonicalURIBuildingException("",e);
        }
    }
}
