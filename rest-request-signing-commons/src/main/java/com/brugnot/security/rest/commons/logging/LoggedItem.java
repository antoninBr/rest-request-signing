package com.brugnot.security.rest.commons.logging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Antonin on 24/01/2017.
 */
public enum LoggedItem {

    MAP(true, "getSize",false),
    LIST(true, "getSize",false),
    SET(true,"size",true),
    STRING(false, null,true),
    BYTE_ARRAY(true,"length",false),
    COMPLEX_OBJECT(false,null,true);


    private boolean logSize;
    private String getSizeMethodName;
    private boolean logValue;

    LoggedItem(boolean logSize, String getSizeMethodName, boolean logValue) {
        this.logSize = logSize;
        this.getSizeMethodName = getSizeMethodName;
        this.logValue = logValue;
    }

    public int getObjectSize(Object object){

        try {
            Method method = object.getClass().getMethod(this.getSizeMethodName);
            return (Integer) method.invoke(object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return 0;
        }

    }

    public boolean isLogSize() {
        return logSize;
    }

    public boolean isLogValue() {
        return logValue;
    }
}
