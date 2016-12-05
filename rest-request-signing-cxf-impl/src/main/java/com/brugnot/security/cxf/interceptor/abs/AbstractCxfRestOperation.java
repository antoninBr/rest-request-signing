package com.brugnot.security.cxf.interceptor.abs;

import com.brugnot.security.core.HashAlgorithm;
import com.brugnot.security.core.com.brugnot.security.core.builder.*;
import com.brugnot.security.core.exception.builder.RestBuilderException;
import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

import java.util.List;
import java.util.Map;

/**
 * Created by Antonin on 04/12/2016.
 */
public abstract class AbstractCxfRestOperation extends AbstractOutDatabindingInterceptor {

    private RestCanonicalRequestBuilder restCanonicalRequestBuilder;

    private RestCanonicalQueryStringBuilder restCanonicalQueryStringBuilder;

    private RestCanonicalURIBuilder restCanonicalURIBuilder;

    private RestCanonicalHeadersBuilder restCanonicalHeadersBuilder;

    private RestRequestPayloadBuilder restRequestPayloadBuilder;

    private RestSignedHeadersBuilder restSignedHeadersBuilder;

    public AbstractCxfRestOperation() {
        super(Phase.MARSHAL);
    }

    protected String buildRestRequestFromMessage(Message message){

        try {
            return restCanonicalRequestBuilder.buildHashedRestCanonicalRequest(
                    null,
                    message.get(Message.HTTP_REQUEST_METHOD).toString(),
                    restCanonicalQueryStringBuilder.buildRestCanonicalQueryString(null),
                    restCanonicalURIBuilder.buildRestCanonicalURI(message.get(Message.REQUEST_URI).toString()).toString(),
                    restCanonicalHeadersBuilder.buildRestCanonicalHeaders((Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS)),
                    restSignedHeadersBuilder.buildRestSignedHeaders(null),
                    restRequestPayloadBuilder.buildRestRequestPayload(null, null));
        }catch (RestBuilderException e){

        }

    }

}
