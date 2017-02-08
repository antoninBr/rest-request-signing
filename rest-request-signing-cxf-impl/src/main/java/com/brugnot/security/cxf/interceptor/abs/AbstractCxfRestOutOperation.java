package com.brugnot.security.cxf.interceptor.abs;

import com.brugnot.security.core.builder.*;
import com.brugnot.security.core.exception.builder.RestBuilderException;
import com.brugnot.security.cxf.commons.CXFRequestComponent;
import com.brugnot.security.cxf.interceptor.exception.RequestComponentExtractionException;
import com.brugnot.security.cxf.interceptor.exception.RequestPayloadExtractionException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.provider.ProviderFactory;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.Phase;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * Created by Antonin on 04/12/2016.
 */
public abstract class AbstractCxfRestOutOperation extends AbstractOutDatabindingInterceptor {

    public static final String BODY_INDEX = "BODY_INDEX";

    private RestCanonicalRequestBuilder restCanonicalRequestBuilder;

    private RestCanonicalQueryStringBuilder restCanonicalQueryStringBuilder;

    private RestCanonicalURIBuilder restCanonicalURIBuilder;

    private RestCanonicalHeadersBuilder restCanonicalHeadersBuilder;

    private RestRequestPayloadBuilder restRequestPayloadBuilder;

    private RestSignedHeadersBuilder restSignedHeadersBuilder;

    protected HashAlgorithm requestHashAlgorithm;

    protected HashAlgorithm payloadHashAlgorithm;

    public AbstractCxfRestOutOperation() {
        super(Phase.POST_LOGICAL);
    }

    protected String buildRestRequestFromMessage(Message message) throws RestBuilderException, RequestPayloadExtractionException, RequestComponentExtractionException {

            return restCanonicalRequestBuilder.buildHashedRestCanonicalRequest(
                    requestHashAlgorithm,
                    CXFRequestComponent.METHOD.getComponentAsString(message),
                    restCanonicalURIBuilder.buildRestCanonicalURI(CXFRequestComponent.REQUEST_URI.getComponentAsString(message)).toString(),
                    restCanonicalQueryStringBuilder.buildRestCanonicalQueryString(CXFRequestComponent.QUERY.getComponentAsString(message)),
                    restCanonicalHeadersBuilder.buildRestCanonicalHeaders(CXFRequestComponent.HEADERS.getComponentAsMap(message)),
                    restSignedHeadersBuilder.buildRestSignedHeaders(getRestSignedHeadersFromMessage(message)),
                    restRequestPayloadBuilder.buildRestRequestPayload(payloadHashAlgorithm, getPayloadDataFromMessage(message)));


    }

    private Set<String> getRestSignedHeadersFromMessage(Message message) {
        return null;
    }
    
    private byte[] getPayloadDataFromMessage(Message message) throws RequestPayloadExtractionException {

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
            int bodyIndex = (Integer) message.get(BODY_INDEX);
            Method aMethod = ori.getAnnotatedMethod();
            Annotation[] anns = aMethod == null || bodyIndex == -1 ? new Annotation[0]
                    : aMethod.getParameterAnnotations()[bodyIndex];
            Object body = objs.get(0);
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

            try {
                payloadData = IOUtils.readBytesFromStream(os.getInputStream());
            } catch (IOException e) {
                throw new RequestPayloadExtractionException("Error while Reading the Data from the Request Payload OutputStream",e);
            }

        }

        return payloadData;
    }

    private void writeBody(Object o, Message outMessage, Class<?> cls,
                           Type type, Annotation[] anns,
                           MultivaluedMap<String, Object> headers, OutputStream os) throws RequestPayloadExtractionException {
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
            } catch (Exception e) {
                throw new RequestPayloadExtractionException(
                        "Error, could not write payload body to output stream",e);
            }
        } else {
            throw new RequestPayloadExtractionException("Error, message body writer is null, could not extract the payload");
        }

    }

    @Inject
    public void setRestCanonicalRequestBuilder(RestCanonicalRequestBuilder restCanonicalRequestBuilder) {
        this.restCanonicalRequestBuilder = restCanonicalRequestBuilder;
    }

    @Inject
    public void setRestCanonicalQueryStringBuilder(RestCanonicalQueryStringBuilder restCanonicalQueryStringBuilder) {
        this.restCanonicalQueryStringBuilder = restCanonicalQueryStringBuilder;
    }

    @Inject
    public void setRestCanonicalURIBuilder(RestCanonicalURIBuilder restCanonicalURIBuilder) {
        this.restCanonicalURIBuilder = restCanonicalURIBuilder;
    }

    @Inject
    public void setRestCanonicalHeadersBuilder(RestCanonicalHeadersBuilder restCanonicalHeadersBuilder) {
        this.restCanonicalHeadersBuilder = restCanonicalHeadersBuilder;
    }

    @Inject
    public void setRestRequestPayloadBuilder(RestRequestPayloadBuilder restRequestPayloadBuilder) {
        this.restRequestPayloadBuilder = restRequestPayloadBuilder;
    }

    @Inject
    public void setRestSignedHeadersBuilder(RestSignedHeadersBuilder restSignedHeadersBuilder) {
        this.restSignedHeadersBuilder = restSignedHeadersBuilder;
    }

    public void setRequestHashAlgorithm(HashAlgorithm requestHashAlgorithm) {
        this.requestHashAlgorithm = requestHashAlgorithm;
    }

    public void setPayloadHashAlgorithm(HashAlgorithm payloadHashAlgorithm) {
        this.payloadHashAlgorithm = payloadHashAlgorithm;
    }
}
