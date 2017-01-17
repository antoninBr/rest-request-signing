package com.brugnot.security.core.builder.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Antonin on 12/12/2016.
 */
public class RestCanonicalQueryStringBuilderV1Test {

    private RestCanonicalQueryStringBuilderV1 restCanonicalQueryStringBuilderV1;

    @Before
    public void setUp() throws Exception {

        restCanonicalQueryStringBuilderV1 = new RestCanonicalQueryStringBuilderV1();

    }

    @Test
    public void buildRestCanonicalQueryString() throws Exception {

        String canonicalQueryString = restCanonicalQueryStringBuilderV1.buildRestCanonicalQueryString("&a=1&c=3&b=2");

        Assert.assertNotNull(canonicalQueryString);
        Assert.assertEquals("a1b2c3",canonicalQueryString);


    }

    @Test
    public void buildRestCanonicalQueryStringWithNoParams() throws Exception {

        String canonicalQueryString = restCanonicalQueryStringBuilderV1.buildRestCanonicalQueryString("");

        Assert.assertNotNull(canonicalQueryString);
        Assert.assertEquals("",canonicalQueryString);


    }

    @Test
    public void buildRestCanonicalQueryStringWithNull() throws Exception {

        String canonicalQueryString = restCanonicalQueryStringBuilderV1.buildRestCanonicalQueryString(null);
        Assert.assertNotNull(canonicalQueryString);
        Assert.assertEquals("",canonicalQueryString);


    }

}