package com.brugnot.security.core.builder.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Antonin on 08/02/2017.
 */
public class RestCanonicalHeadersBuilderV1Test {

    /**
     * The Rest Canonical Query String Builder (V1) to Test
     */
    private RestCanonicalHeadersBuilderV1 restCanonicalHeadersBuilderV1;

    @Before
    public void setUp() throws Exception {

        restCanonicalHeadersBuilderV1 = new RestCanonicalHeadersBuilderV1();

    }

    @Test
    public void testBuildRestCanonicalHeadersString1() throws Exception {

        Map<String,List<String>> headers = new HashMap<>();
        headers.put("header1",Arrays.asList("titi","toto","tutu"));
        headers.put("header2",Arrays.asList("like"));
        headers.put("header3",Arrays.asList("roff"));
        String canonicalHeadersString = restCanonicalHeadersBuilderV1.buildRestCanonicalHeaders(headers);

        Assert.assertEquals("header1tititototutuheader2likeheader3roff",canonicalHeadersString);

    }

    @Test
    public void testBuildRestCanonicalHeadersString2() throws Exception {

        Map<String,List<String>> headers = new HashMap<>();
        headers.put("header1",Arrays.asList("titi "," toto","tutu"));
        String canonicalHeadersString = restCanonicalHeadersBuilderV1.buildRestCanonicalHeaders(headers);

        Assert.assertEquals("header1tititototutu",canonicalHeadersString);

    }
}
