package com.brugnot.security.core.builder;

import com.brugnot.security.core.builder.versionning.VersionedBuilder;
import com.brugnot.security.core.exception.builder.RestRequestPayloadBuildingException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;

/**
 * Rest Request Payload Builder
 *
 * Created by Antonin on 27/11/2016.
 */
public interface RestRequestPayloadBuilder  extends VersionedBuilder {

    /**
     * Build the Rest Request Payload
     *
     * @param hashAlgorithm
     * @param restRequestPayload
     * @return the lower case HEX Value of the hashed Payload
     */
    String buildRestRequestPayload(HashAlgorithm hashAlgorithm, byte[] restRequestPayload) throws RestRequestPayloadBuildingException;


}
