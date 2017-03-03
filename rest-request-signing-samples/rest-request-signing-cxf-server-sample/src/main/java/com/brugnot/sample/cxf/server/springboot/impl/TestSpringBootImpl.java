package com.brugnot.sample.cxf.server.springboot.impl;

import com.brugnot.sample.commons.endpoints.Test;
import com.brugnot.sample.commons.endpoints.dto.Dto;
import org.springframework.stereotype.Component;

/**
 * Test Server Implementation using Spring-Boot
 * Created by Antonin on 09/12/2016.
 */
@Component
public class TestSpringBootImpl implements Test {


    public String getTestResponse(){
        return "Hello this is a test";
    }

    public String getTestResponseWithQueryParam(String value){
        return "Hello this is a test returning the value : "+value;
    }

    public String postWithDto(Dto dto) {
        return "Hello this is a test returning the String value of the received Dto : "+dto.getStringValue();
    }

}
