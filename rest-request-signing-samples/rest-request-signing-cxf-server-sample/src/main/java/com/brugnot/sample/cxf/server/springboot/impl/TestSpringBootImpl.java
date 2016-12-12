package com.brugnot.sample.cxf.server.springboot.impl;

import com.brugnot.sample.commons.endpoints.Test;
import org.springframework.stereotype.Component;

/**
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

}
