package com.brugnot.security.cxf.commons;

import com.brugnot.security.cxf.interceptor.exception.RequestComponentExtractionException;
import com.brugnot.security.rest.commons.request.RequestComponent;
import org.apache.cxf.message.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Antonin on 20/01/2017.
 */
public enum CXFRequestComponent implements RequestComponent{

    METHOD(Message.HTTP_REQUEST_METHOD,false),
    QUERY(Message.QUERY_STRING,true),
    REQUEST_URI(Message.REQUEST_URI,false),
    HEADERS(Message.PROTOCOL_HEADERS,true),

    ;


    private String name;
    private boolean nullable;


    CXFRequestComponent(String name, boolean nullable) {
        this.name = name;
        this.nullable = nullable;
    }


    public boolean isNullable() {
        return nullable;
    }

    public String getName() {
        return name;
    }

    public String getComponentAsString(Message message) throws RequestComponentExtractionException {

        Object object = message.get(this.name);

        if(object == null){
            if(this.nullable){
                return "";
            }else{
                throw new RequestComponentExtractionException("Request component '"+this.name+"' is null and its not allowed");
            }
        }else{
            return object.toString();
        }
    }

    public Map getComponentAsMap(Message message) throws RequestComponentExtractionException {

        Object object = message.get(this.name);

        if(object == null){
            if(this.nullable){
                return new HashMap<String,List<String>>();

            }else{
                throw new RequestComponentExtractionException("Request component '"+this.name+"' is null and its not allowed");
            }
        }else{
            return (Map<String, List<String>>) object;
        }

    }
}
