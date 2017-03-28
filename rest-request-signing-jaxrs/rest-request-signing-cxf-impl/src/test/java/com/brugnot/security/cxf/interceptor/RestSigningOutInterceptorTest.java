package com.brugnot.security.cxf.interceptor;

import com.brugnot.security.core.exception.user.SigningUserCreationException;
import com.brugnot.security.rest.commons.user.KeystoreUser;
import org.apache.cxf.interceptor.Fault;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by a.brugnot on 28/03/2017.
 */
public class RestSigningOutInterceptorTest extends AbstractInterceptorTest {

    @Test
    public void unknownUser() throws Exception {

        Mockito.when(signingUserCreator.createSigningUser(Mockito.any(KeystoreUser.class))).thenThrow(
                SigningUserCreationException.class);
        try {
            restSigningOutInterceptor.handleMessage(testMessage);
        }catch (Fault e){
            if(!Fault.FAULT_CODE_CLIENT.equals(e.getFaultCode())){
                Assert.fail();
            }else{
                if(!SigningUserCreationException.class.equals(e.getCause().getClass())){
                    Assert.fail();
                }
            }
        }

    }
}
