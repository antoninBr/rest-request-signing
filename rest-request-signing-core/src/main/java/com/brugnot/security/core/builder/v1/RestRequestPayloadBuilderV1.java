package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestRequestPayloadBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestRequestPayloadBuildingException;
import com.brugnot.security.core.exception.digest.HashCreationException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;


/**
 * Created by Antonin on 12/12/2016.
 */
public class RestRequestPayloadBuilderV1 extends AbstractBuilderV1 implements RestRequestPayloadBuilder {

    public String buildRestRequestPayload(HashAlgorithm hashAlgorithm, byte[] restRequestPayload) throws RestRequestPayloadBuildingException {

        byte[] restRequestPayloadToProcess;

        if(restRequestPayload==null){
            restRequestPayloadToProcess = "".getBytes();
        }else{
            restRequestPayloadToProcess = restRequestPayload.clone();
        }

        String hashedRestRequestPayload;
        try {
            hashedRestRequestPayload = getHashOfData(hashAlgorithm,restRequestPayloadToProcess);
        } catch (HashCreationException e) {
            throw new RestRequestPayloadBuildingException("Error while building the hashed rest request payload",e);
        }

        return hashedRestRequestPayload;
    }
}
