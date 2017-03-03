package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestRequestPayloadBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestRequestPayloadBuildingException;
import com.brugnot.security.core.exception.digest.HashCreationException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;
import com.brugnot.security.rest.commons.logging.DebugLogType;
import com.brugnot.security.rest.commons.logging.LoggedItem;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Rest Request Payload Builder (V1)
 * Created by Antonin on 12/12/2016.
 */
public class RestRequestPayloadBuilderV1 extends AbstractBuilderV1 implements RestRequestPayloadBuilder {

    /**
     * Logger for this builder
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestRequestPayloadBuilderV1.class);

    @Profiled
    public String buildRestRequestPayload(HashAlgorithm hashAlgorithm, byte[] restRequestPayload) throws RestRequestPayloadBuildingException {


        byte[] restRequestPayloadToProcess;

        if(restRequestPayload==null){
            restRequestPayloadToProcess = "".getBytes();
        }else{
            restRequestPayloadToProcess = restRequestPayload.clone();
        }

        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"restRequestPayload", LoggedItem.BYTE_ARRAY,restRequestPayloadToProcess));

        String hashedRestRequestPayload;
        try {
            hashedRestRequestPayload = getHashOfData(hashAlgorithm,restRequestPayloadToProcess);
        } catch (HashCreationException e) {
            throw new RestRequestPayloadBuildingException("Error while building the hashed rest request payload",e);
        }

        LOGGER.debug(createItemDebugLog(DebugLogType.OUTPUT,"hashedRestRequestPayload", LoggedItem.STRING,hashedRestRequestPayload));

        return hashedRestRequestPayload;
    }
}
