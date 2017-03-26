package com.brugnot.security.cxf.interceptor.abs;

import com.brugnot.security.core.builder.*;
import com.brugnot.security.core.exception.builder.RestBuilderException;
import com.brugnot.security.cxf.commons.CXFRequestComponent;
import com.brugnot.security.cxf.commons.InPayloadDataProvider;
import com.brugnot.security.rest.commons.exception.RequestComponentExtractionException;
import com.brugnot.security.cxf.interceptor.exception.RequestPayloadExtractionException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import javax.inject.Inject;
import java.util.Set;
import java.util.TreeSet;

/**
 * Abstract Cxf Rest In Operation that extends an Interceptor
 * Created by Antonin on 17/01/2017.
 */
public abstract class AbstractCxfRestInOperation extends
        AbstractPhaseInterceptor<Message> {

    /**
     * All the Builders needed
     */
    private RestCanonicalRequestBuilder restCanonicalRequestBuilder;
    private RestCanonicalQueryStringBuilder restCanonicalQueryStringBuilder;
    private RestCanonicalURIBuilder restCanonicalURIBuilder;
    private RestCanonicalHeadersBuilder restCanonicalHeadersBuilder;
    private RestRequestPayloadBuilder restRequestPayloadBuilder;
    private RestSignedHeadersBuilder restSignedHeadersBuilder;

    /**
     * Interceptor Constructor with Phase
     */
    public AbstractCxfRestInOperation() {
        super(Phase.READ);
    }

    /**
     * Build the Canonical Rest Request From the Message
     * (The request is also hashed)
     * @param message
     * @param requestHashAlgorithm
     * @param payloadHashAlgorithm
     * @return hashed canonical rest request
     * @throws RequestComponentExtractionException
     * @throws RestBuilderException
     * @throws RequestPayloadExtractionException
     */
    protected String buildRestRequestFromMessage(Message message,HashAlgorithm requestHashAlgorithm, HashAlgorithm payloadHashAlgorithm) throws RequestComponentExtractionException, RestBuilderException, RequestPayloadExtractionException {

        return restCanonicalRequestBuilder.buildHashedRestCanonicalRequest(
                requestHashAlgorithm,
                CXFRequestComponent.METHOD.getComponentAsString(message),
                restCanonicalURIBuilder.buildRestCanonicalURI(CXFRequestComponent.REQUEST_URI.getComponentAsString(message)).toString(),
                restCanonicalQueryStringBuilder.buildRestCanonicalQueryString(CXFRequestComponent.QUERY.getComponentAsString(message)),
                restCanonicalHeadersBuilder.buildRestCanonicalHeaders(CXFRequestComponent.HEADERS.getComponentAsMap(message)),
                restSignedHeadersBuilder.buildRestSignedHeaders(getRestSignedHeadersFromMessage()),
                restRequestPayloadBuilder.buildRestRequestPayload(payloadHashAlgorithm, InPayloadDataProvider.getInContentData(message)));
    }

    private Set<String> getRestSignedHeadersFromMessage() {
        return new TreeSet<String>();
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


}
