package com.brugnot.sample.cxf.server.springboot.impl;

import com.brugnot.sample.commons.endpoints.Test;
import org.springframework.stereotype.Component;

/**
 * Test Server Implementation using Spring-Boot
 * Created by Antonin on 09/12/2016.
 */
@Component
@org.apache.cxf.interceptor.InInterceptors (interceptors = {"com.brugnot.security.cxf.interceptor.RestSigningInInterceptor" })
public class TestSpringBootImpl implements Test {


    public String getTestResponse(){
        return "Hello this is a test";
    }

    public String getTestResponseWithQueryParam(String value){
        return "Hello this is a test returning the value : "+value;
    }

}
