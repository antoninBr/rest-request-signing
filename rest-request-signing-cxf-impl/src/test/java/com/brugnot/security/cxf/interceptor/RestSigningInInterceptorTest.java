package com.brugnot.security.cxf.interceptor;

import org.apache.cxf.interceptor.Fault;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Antonin on 26/03/2017.
 */

public class RestSigningInInterceptorTest extends AbstractInterceptorTest{

    @Test
    public void testWithoutHeaders() throws Exception {

        Mockito.when(testMessage.get(Mockito.anyString())).thenReturn(createEmptyHeadersMap());
        try {
            restSigningInInterceptor.handleMessage(testMessage);
        }catch (Fault e){
            if(!Fault.FAULT_CODE_CLIENT.equals(e.getFaultCode())){
                Assert.fail();
            }
        }
    }

    @Test
    public void testWithoutMandatorySecurityHeader() throws Exception {

        Mockito.when(testMessage.get(Mockito.anyString())).thenReturn(createHeadersMapWithoutSecurityOnes());
        try {
            restSigningInInterceptor.handleMessage(testMessage);
        }catch (Fault e){
            if(!Fault.FAULT_CODE_CLIENT.equals(e.getFaultCode())){
                Assert.fail();
            }
        }
    }

}