package com.brugnot.security.core.builder.v1;

import com.brugnot.security.rest.commons.hash.HashAlgorithm;
import com.brugnot.security.rest.commons.hash.NormalizedHashAlgorithm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Antonin on 12/12/2016.
 */
public class RestRequestPayloadBuilderV1Test {

    private RestRequestPayloadBuilderV1 restRequestPayloadBuilderV1;

    private HashAlgorithm hashAlgorithmToUseForThisTest = NormalizedHashAlgorithm.SHA_256;

    @Before
    public void setUp() throws Exception {

        restRequestPayloadBuilderV1= new RestRequestPayloadBuilderV1();

    }

    @Test
    public void buildRestRequestPayload() throws Exception {

        String payload = "this is a payload";

        String hashedPayload = restRequestPayloadBuilderV1.buildRestRequestPayload(hashAlgorithmToUseForThisTest,payload.getBytes());
        Assert.assertNotNull(hashedPayload);

        Assert.assertEquals(hashedPayload,restRequestPayloadBuilderV1.buildRestRequestPayload(hashAlgorithmToUseForThisTest,payload.getBytes()));

    }

    @Test
    public void buildRestRequestForEmptyPayload() throws Exception {

        String hashedPayload = restRequestPayloadBuilderV1.buildRestRequestPayload(hashAlgorithmToUseForThisTest,null);
        Assert.assertNotNull(hashedPayload);

        Assert.assertEquals(hashedPayload,restRequestPayloadBuilderV1.buildRestRequestPayload(hashAlgorithmToUseForThisTest, null));

    }

}