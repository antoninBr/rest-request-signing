package com.brugnot.security.cxf.commons;

import com.brugnot.security.rest.commons.exception.RequestComponentExtractionException;
import com.brugnot.security.rest.commons.request.RequestComponent;
import org.apache.cxf.message.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Antonin on 20/01/2017.
 */
public enum CXFRequestComponent implements RequestComponent<Message>{

    METHOD(Message.HTTP_REQUEST_METHOD,false),
    QUERY(Message.QUERY_STRING,true),
    REQUEST_URI(Message.REQUEST_URI,false),
    HEADERS(Message.PROTOCOL_HEADERS,true),

    ;

    private String name;
    private boolean nullable;

    /**
     * Private Package Constructor
     * @param name
     * @param nullable
     */
    CXFRequestComponent(String name, boolean nullable) {
        this.name = name;
        this.nullable = nullable;
    }

    /**
     *
     * @param message
     * @return  extracted component
     * @throws RequestComponentExtractionException
     */
    public String getComponentAsString(Message message) throws RequestComponentExtractionException {

        Object object = message.get(this.name);

        if(object == null){
            if(this.nullable){
                return "";
            }else{
                throw new RequestComponentExtractionException("CXF Request component '"+this.name+"' extracted from Message is null and its not allowed");
            }
        }else{
            return object.toString();
        }
    }

    /**
     *
     * @param message
     * @return extracted component
     * @throws RequestComponentExtractionException
     */
    public Map getComponentAsMap(Message message) throws RequestComponentExtractionException {

        Object object = message.get(this.name);

        if(object == null){
            if(this.nullable){
                return new HashMap<String,List<String>>();

            }else{
                throw new RequestComponentExtractionException("CXF Request component '"+this.name+"' extracted from Message is null and its not allowed");
            }
        }else{
            return (Map<String, List<String>>) object;
        }

    }
}
