package com.brugnot.security.core.builder.v1;

import com.brugnot.security.rest.commons.header.RestSecurityHeadersEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Antonin on 04/03/2017.
 */
public class RestSignedHeadersBuilderV1Test {

    private RestSignedHeadersBuilderV1 restSignedHeadersBuilderV1;

    @Before
    public void setUp() throws Exception {
        restSignedHeadersBuilderV1 = new RestSignedHeadersBuilderV1();
    }

    @Test
    public void buildRestSignedHeadersTest() throws Exception {

        Set<String> signedHeadersSet = new HashSet<>();
        signedHeadersSet.add(RestSecurityHeadersEnum.REST_CANONICAL_REQUEST.getNormalizedName());
        signedHeadersSet.add(RestSecurityHeadersEnum.REST_CANONICAL_REQUEST_KEY.getNormalizedName());
        Assert.assertNotNull(restSignedHeadersBuilderV1.buildRestSignedHeaders(signedHeadersSet));
    }

}