package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestCanonicalQueryStringBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestCanonicalQueryStringBuildingException;
import com.brugnot.security.rest.commons.logging.DebugLogType;
import com.brugnot.security.rest.commons.logging.LoggedItem;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * Rest Canonical Query String Builder (V1)
 * Created by Antonin on 12/12/2016.
 */
public class RestCanonicalQueryStringBuilderV1 extends AbstractBuilderV1 implements RestCanonicalQueryStringBuilder {

    /**
     * Query Parameters Constants
     */
    private static final String QUERY_PARAMS_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestCanonicalQueryStringBuilderV1.class);

    @Profiled
    public String buildRestCanonicalQueryString(String queryParametersString) throws RestCanonicalQueryStringBuildingException {

        LOGGER.debug(createItemDebugLog(DebugLogType.INPUT_ARGUMENT,"queryParametersString", LoggedItem.STRING,queryParametersString));

        Map<String,String> queryParameters = getQueryParametersMapFromString(queryParametersString);

        StringBuilder canonicalQueryBuilder = new StringBuilder();

        for(Map.Entry<String,String> orderedQueryParamEntry : queryParameters.entrySet()){
            canonicalQueryBuilder.append(orderedQueryParamEntry.getKey());
            canonicalQueryBuilder.append(orderedQueryParamEntry.getValue());
        }

        String canonicalQuery = canonicalQueryBuilder.toString();

        LOGGER.debug(createItemDebugLog(DebugLogType.OUTPUT,"canonicalQuery", LoggedItem.STRING,canonicalQuery));

        return canonicalQuery;
    }

    private Map<String, String> getQueryParametersMapFromString(String queryParametersString) {

        Map<String,String> orderedQueryParams = new TreeMap();

        if(queryParametersString!=null){

            for(String keyValue : queryParametersString.split(QUERY_PARAMS_SEPARATOR)){

               if(keyValue.length()!=0){
                   String[] fields = keyValue.split(KEY_VALUE_SEPARATOR);
                   orderedQueryParams.put(fields[0],fields[1]);
               }

            }
        }

        return orderedQueryParams;
    }
}
