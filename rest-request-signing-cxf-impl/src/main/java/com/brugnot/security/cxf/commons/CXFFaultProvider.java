package com.brugnot.security.cxf.commons;

import org.apache.cxf.interceptor.Fault;

import javax.xml.namespace.QName;

/**
 * Created by a.brugnot on 24/03/2017.
 */
public class CXFFaultProvider {

    public enum FaultSide {
        SERVER, CLIENT
    }

    public static Fault createFault(FaultSide faultSide, Throwable throwable){
        Fault fault = new Fault(throwable);
        switch (faultSide) {
            case CLIENT:
                fault.setFaultCode(new QName("Client"));
                break;
            case SERVER:
                fault.setFaultCode(new QName("Server"));
                break;

        }
        return fault;
    }


}
