package com.brugnot.security.core.builder;

import com.brugnot.security.core.builder.versionning.VersionedBuilder;
import com.brugnot.security.core.exception.builder.RestSignedHeadersBuildingException;

import java.util.Set;

/**
 * Rest Signed Headers Builder
 *
 * Created by Antonin on 27/11/2016.
 */
public interface RestSignedHeadersBuilder extends VersionedBuilder {

    /**
     * Build the Rest Signed Headers Given Provided Headers Names
     * @param restHeaders
     * @return rest signed headers
     * @throws RestSignedHeadersBuildingException
     */
    String buildRestSignedHeaders(Set<String> restHeaders) throws RestSignedHeadersBuildingException;
}
