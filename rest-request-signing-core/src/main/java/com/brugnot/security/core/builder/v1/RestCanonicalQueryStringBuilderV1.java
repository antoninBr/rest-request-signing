package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestCanonicalQueryStringBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestCanonicalQueryStringBuildingException;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Antonin on 12/12/2016.
 */
public class RestCanonicalQueryStringBuilderV1 extends AbstractBuilderV1 implements RestCanonicalQueryStringBuilder {

    /**
     * Query Parameters Constants
     */
    private static final String QUERY_PARAMS_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    public String buildRestCanonicalQueryString(String queryParametersString) throws RestCanonicalQueryStringBuildingException {

        Map<String,String> queryParameters = getQueryParametersMapFromString(queryParametersString);

        StringBuilder canonicalQuery = new StringBuilder();

        for(Map.Entry<String,String> orderedQueryParamEntry : queryParameters.entrySet()){
            canonicalQuery.append(orderedQueryParamEntry.getKey());
            canonicalQuery.append(orderedQueryParamEntry.getValue());
        }

        return canonicalQuery.toString();
    }

    private Map<String, String> getQueryParametersMapFromString(String queryParametersString) {

        Map<String,String> orderedQueryParams = new TreeMap();

        if(queryParametersString!=null){

            for(String keyValue : queryParametersString.split(QUERY_PARAMS_SEPARATOR)){

               if(keyValue.length()==0){
                   continue;
               }else{
                   String[] fields = keyValue.split(KEY_VALUE_SEPARATOR);
                   orderedQueryParams.put(fields[0],fields[1]);
               }

            }
        }

        return orderedQueryParams;
    }
}
