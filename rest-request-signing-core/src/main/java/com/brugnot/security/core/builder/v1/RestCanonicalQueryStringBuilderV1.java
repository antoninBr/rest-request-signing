package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestCanonicalQueryStringBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestCanonicalQueryStringBuildingException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Antonin on 12/12/2016.
 */
public class RestCanonicalQueryStringBuilderV1 extends AbstractBuilderV1 implements RestCanonicalQueryStringBuilder {

    public String buildRestCanonicalQueryString(Map<String, String> queryParameters) throws RestCanonicalQueryStringBuildingException {

        StringBuilder canonicalQuery = new StringBuilder();

        Map<String,String> orderedQueryParams = new TreeMap();

        if(queryParameters==null) queryParameters = new HashMap<String, String>();

        for(Map.Entry<String,String> unorderedQueryParamEntry : queryParameters.entrySet()){

            orderedQueryParams.put(unorderedQueryParamEntry.getKey(),unorderedQueryParamEntry.getValue());

        }

        for(Map.Entry<String,String> orderedQueryParamEntry : orderedQueryParams.entrySet()){
            canonicalQuery.append(orderedQueryParamEntry.getKey());
            canonicalQuery.append(orderedQueryParamEntry.getValue());
        }

        return canonicalQuery.toString();
    }
}
