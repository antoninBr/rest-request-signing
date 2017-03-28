package com.brugnot.security.cxf.interceptor.abs;

import com.brugnot.security.core.builder.*;
import com.brugnot.security.core.exception.builder.RestBuilderException;
import com.brugnot.security.cxf.commons.CXFRequestComponent;
import com.brugnot.security.cxf.commons.OutPayloadDataProvider;
import com.brugnot.security.rest.commons.exception.RequestComponentExtractionException;
import com.brugnot.security.cxf.interceptor.exception.RequestPayloadExtractionException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;
import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

import javax.inject.Inject;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Antonin on 04/12/2016.
 */
public abstract class AbstractCxfRestOutOperation extends AbstractOutDatabindingInterceptor {

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
     * The Hash Algorithms for the request and the request payload
     */
    protected HashAlgorithm requestHashAlgorithm;
    protected HashAlgorithm payloadHashAlgorithm;

    /**
     * Interceptor Constructor with Phase
     */
    public AbstractCxfRestOutOperation() {
        super(Phase.POST_LOGICAL);
    }

    /**
     *
     * @param message
     * @return
     * @throws RestBuilderException
     * @throws RequestPayloadExtractionException
     * @throws RequestComponentExtractionException
     */
    protected String buildRestRequestFromMessage(Message message) throws RestBuilderException, RequestPayloadExtractionException, RequestComponentExtractionException {

            return restCanonicalRequestBuilder.buildHashedRestCanonicalRequest(
                    requestHashAlgorithm,
                    CXFRequestComponent.METHOD.getComponentAsString(message),
                    restCanonicalURIBuilder.buildRestCanonicalURI(CXFRequestComponent.REQUEST_URI.getComponentAsString(message)).toString(),
                    restCanonicalQueryStringBuilder.buildRestCanonicalQueryString(CXFRequestComponent.QUERY.getComponentAsString(message)),
                    restCanonicalHeadersBuilder.buildRestCanonicalHeaders(CXFRequestComponent.HEADERS.getComponentAsMap(message)),
                    restSignedHeadersBuilder.buildRestSignedHeaders(getRestSignedHeadersFromMessage(message)),
                    restRequestPayloadBuilder.buildRestRequestPayload(payloadHashAlgorithm, OutPayloadDataProvider.getPayloadDataFromMessage(message)));


    }

    /**
     *
     * @param message
     * @return
     */
    private Set<String> getRestSignedHeadersFromMessage(Message message) {
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

    public void setRequestHashAlgorithm(HashAlgorithm requestHashAlgorithm) {
        this.requestHashAlgorithm = requestHashAlgorithm;
    }

    public void setPayloadHashAlgorithm(HashAlgorithm payloadHashAlgorithm) {
        this.payloadHashAlgorithm = payloadHashAlgorithm;
    }
}
