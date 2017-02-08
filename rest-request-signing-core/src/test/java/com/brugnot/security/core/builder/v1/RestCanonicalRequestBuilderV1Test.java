package com.brugnot.security.core.builder.v1;

import com.brugnot.security.rest.commons.hash.NormalizedHashAlgorithm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Antonin on 08/02/2017.
 */
public class RestCanonicalRequestBuilderV1Test {

    /**
     * The Rest Canonical Query String Builder (V1) to Test
     */
    private RestCanonicalRequestBuilderV1 restCanonicalRequestBuilderV1;

    @Before
    public void setUp() throws Exception {

        restCanonicalRequestBuilderV1 = new RestCanonicalRequestBuilderV1();

    }

    @Test
    public void createCanonicalRestRequest() throws Exception {

        String hashedRequest = restCanonicalRequestBuilderV1.buildHashedRestCanonicalRequest(NormalizedHashAlgorithm.SHA_256,
                "GET",
                "http://google.fr/test/",
                "a=3",
                "headervalue",
                "",
                "abcdefgh");
        Assert.assertNotNull(hashedRequest);

    }
}
