package com.brugnot.security.cxf.interceptor.abs;

import com.brugnot.security.core.HashAlgorithm;
import com.brugnot.security.core.com.brugnot.security.core.builder.*;
import com.brugnot.security.core.exception.builder.RestBuilderException;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.provider.ProviderFactory;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.Phase;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Antonin on 04/12/2016.
 */
public abstract class AbstractCxfRestOutOperation extends AbstractOutDatabindingInterceptor {

    private RestCanonicalRequestBuilder restCanonicalRequestBuilder;

    private RestCanonicalQueryStringBuilder restCanonicalQueryStringBuilder;

    private RestCanonicalURIBuilder restCanonicalURIBuilder;

    private RestCanonicalHeadersBuilder restCanonicalHeadersBuilder;

    private RestRequestPayloadBuilder restRequestPayloadBuilder;

    private RestSignedHeadersBuilder restSignedHeadersBuilder;

    public AbstractCxfRestOutOperation() {
        super(Phase.POST_LOGICAL);
    }

    protected String buildRestRequestFromMessage(Message message) throws RestBuilderException{

            return restCanonicalRequestBuilder.buildHashedRestCanonicalRequest(
                    null,
                    message.get(Message.HTTP_REQUEST_METHOD).toString(),
                    restCanonicalQueryStringBuilder.buildRestCanonicalQueryString(null),
                    restCanonicalURIBuilder.buildRestCanonicalURI(message.get(Message.REQUEST_URI).toString()).toString(),
                    restCanonicalHeadersBuilder.buildRestCanonicalHeaders((Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS)),
                    restSignedHeadersBuilder.buildRestSignedHeaders(getRestSignedHeadersFromMessage(message)),
                    restRequestPayloadBuilder.buildRestRequestPayload(null, getPayloadDataFromMessage(message)));


    }

    private Set<String> getRestSignedHeadersFromMessage(Message message) {
        return null;
    }
    
    private byte[] getPayloadDataFromMessage(Message message){

        byte[] payloadData = null;

        MessageContentsList objs = MessageContentsList
                .getContentsList(message);
        if (objs != null && objs.size() != 0) {

            OperationResourceInfo ori = message
                    .getContent(OperationResourceInfo.class);
            CachedOutputStream os = new CachedOutputStream();

            MultivaluedMap<String, Object> headers = (MultivaluedMap<String, Object>) message
                    .get(Message.PROTOCOL_HEADERS);
            
            Method method = ori.getMethodToInvoke();
            int bodyIndex = (Integer) message.get("BODY_INDEX");
            Method aMethod = ori.getAnnotatedMethod();
            Annotation[] anns = aMethod == null || bodyIndex == -1 ? new Annotation[0]
                    : aMethod.getParameterAnnotations()[bodyIndex];
            Object body = objs.get(0);
            try {
                if (bodyIndex != -1) {
                    Class<?> paramClass = method.getParameterTypes()[bodyIndex];
                    Type paramType = method.getGenericParameterTypes()[bodyIndex];

                    boolean isAssignable = paramClass.isAssignableFrom(body
                            .getClass());
                    writeBody(body, message, isAssignable ? paramClass
                                    : body.getClass(),
                            isAssignable ? paramType : body.getClass(), anns,
                            headers, os);
                } else {
                    writeBody(body, message, body.getClass(),
                            body.getClass(), anns, headers, os);
                }
            } catch (Exception ex) {
                throw new Fault(ex);
            }


            
            try {
                payloadData = IOUtils.readBytesFromStream(os.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return payloadData;
    }

    private void writeBody(Object o, Message outMessage, Class<?> cls,
                           Type type, Annotation[] anns,
                           MultivaluedMap<String, Object> headers, OutputStream os) {
        if (o == null) {
            return;
        }

        Class<Object> theClass = (Class<Object>) cls;

        MediaType contentType = JAXRSUtils.toMediaType((String) outMessage
                .get(Message.CONTENT_TYPE));
        MessageBodyWriter<Object> mbw = ProviderFactory.getInstance(outMessage)
                .createMessageBodyWriter(theClass, type, anns, contentType,
                        outMessage);
        if (mbw != null) {
            try {
                mbw.writeTo(o, theClass, type, anns, contentType, headers, os);
                if (os != null) {
                    os.flush();
                }
            } catch (Exception ex) {
                throw new RuntimeException(
                        "Error, could not write body to output stream");
            }
        } else {
            throw new RuntimeException("Error, message body writer is null");
        }

    }

}
