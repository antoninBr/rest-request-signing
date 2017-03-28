package com.brugnot.security.cxf.commons;

import com.brugnot.security.cxf.interceptor.exception.RequestPayloadExtractionException;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.provider.ProviderFactory;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by Antonin on 03/03/2017.
 */
public class OutPayloadDataProvider {

    public static final String BODY_INDEX = "BODY_INDEX";

    public static byte[] getPayloadDataFromMessage(Message message) throws RequestPayloadExtractionException {

        byte[] payloadData = null;

        MessageContentsList objs = MessageContentsList
                .getContentsList(message);
        if (objs != null && objs.size() != 0) {

            OperationResourceInfo operationResourceInfo = message
                    .getContent(OperationResourceInfo.class);
            CachedOutputStream os = new CachedOutputStream();

            MultivaluedMap<String, Object> headers = (MultivaluedMap<String, Object>) message
                    .get(Message.PROTOCOL_HEADERS);

            Method method = operationResourceInfo.getMethodToInvoke();
            int bodyIndex = (Integer) message.get(BODY_INDEX);
            Annotation[] annotations = getAnnotations(operationResourceInfo,bodyIndex);
            Object body = objs.get(0);
            if (bodyIndex != -1) {
                Class<?> paramClass = method.getParameterTypes()[bodyIndex];
                Type paramType = method.getGenericParameterTypes()[bodyIndex];

                boolean isAssignable = paramClass.isAssignableFrom(body
                        .getClass());
                writeBody(body, message, isAssignable ? paramClass
                                : body.getClass(),
                        isAssignable ? paramType : body.getClass(), annotations,
                        headers, os);
            } else {
                writeBody(body, message, body.getClass(),
                        body.getClass(), annotations, headers, os);
            }

            try {
                payloadData = IOUtils.readBytesFromStream(os.getInputStream());
            } catch (IOException e) {
                throw new RequestPayloadExtractionException("Error while Reading the Data from the Request Payload OutputStream",e);
            }

        }

        return payloadData;
    }

    private static Annotation[] getAnnotations(OperationResourceInfo operationResourceInfo, int bodyIndex){
        Method aMethod = operationResourceInfo.getAnnotatedMethod();
        Annotation[] anns = aMethod == null || bodyIndex == -1 ? new Annotation[0]
                : aMethod.getParameterAnnotations()[bodyIndex];
                return anns;
    }
    private static void writeBody(Object o, Message outMessage, Class<?> cls,
                           Type type, Annotation[] annotations,
                           MultivaluedMap<String, Object> headers, OutputStream os) throws RequestPayloadExtractionException {
        if (o == null) {
            return;
        }

        Class<Object> theClass = (Class<Object>) cls;

        MediaType contentType = JAXRSUtils.toMediaType((String) outMessage
                .get(Message.CONTENT_TYPE));
        MessageBodyWriter<Object> mbw = ProviderFactory.getInstance(outMessage)
                .createMessageBodyWriter(theClass, type, annotations, contentType,
                        outMessage);
        if (mbw != null) {
            try {
                mbw.writeTo(o, theClass, type, annotations, contentType, headers, os);
                if (os != null) {
                    os.flush();
                }
            } catch (Exception e) {
                throw new RequestPayloadExtractionException(
                        "Error, could not write payload body to output stream",e);
            }
        } else {
            throw new RequestPayloadExtractionException("Error, message body writer is null, could not extract the payload");
        }

    }
}
