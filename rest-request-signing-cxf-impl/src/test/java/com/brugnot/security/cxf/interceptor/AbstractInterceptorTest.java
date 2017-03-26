package com.brugnot.security.cxf.interceptor;

import com.brugnot.security.core.builder.*;
import com.brugnot.security.core.crypt.HashedRestCanonicalRequestDecryptor;
import com.brugnot.security.core.crypt.HashedRestCanonicalRequestEncryptor;
import com.brugnot.security.rest.commons.hash.NormalizedHashAlgorithm;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Antonin on 26/03/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractInterceptorTest {

    protected RestSignedHeadersBuilder restSignedHeadersBuilder = Mockito.mock(RestSignedHeadersBuilder.class);
    protected RestRequestPayloadBuilder restRequestPayloadBuilder  = Mockito.mock(RestRequestPayloadBuilder.class);
    protected RestCanonicalQueryStringBuilder restCanonicalQueryStringBuilder = Mockito.mock(RestCanonicalQueryStringBuilder.class);
    protected RestCanonicalURIBuilder restCanonicalURIBuilder = Mockito.mock(RestCanonicalURIBuilder.class);
    protected RestCanonicalHeadersBuilder restCanonicalHeadersBuilder = Mockito.mock(RestCanonicalHeadersBuilder.class);
    protected RestCanonicalRequestBuilder restCanonicalRequestBuilder = Mockito.mock(RestCanonicalRequestBuilder.class);

    protected HashedRestCanonicalRequestDecryptor hashedRestCanonicalRequestDecryptor = Mockito.mock(HashedRestCanonicalRequestDecryptor.class);
    protected HashedRestCanonicalRequestEncryptor hashedRestCanonicalRequestEncryptor = Mockito.mock(HashedRestCanonicalRequestEncryptor.class);

    protected RestSigningInInterceptor restSigningInInterceptor;
    protected RestSigningOutInterceptor restSigningOutInterceptor;

    @Before
    public void setUp() throws Exception {
        restSigningInInterceptor = createRestSigningInInterceptor();
        restSigningOutInterceptor = createRestSigningOutInterceptor();

    }

    private RestSigningOutInterceptor createRestSigningOutInterceptor() {

        RestSigningOutInterceptor restSigningOutInterceptor = new RestSigningOutInterceptor();
        restSigningOutInterceptor.setRestCanonicalHeadersBuilder(restCanonicalHeadersBuilder);
        restSigningOutInterceptor.setRestCanonicalQueryStringBuilder(restCanonicalQueryStringBuilder);
        restSigningOutInterceptor.setRestCanonicalRequestBuilder(restCanonicalRequestBuilder);
        restSigningOutInterceptor.setRestCanonicalURIBuilder(restCanonicalURIBuilder);
        restSigningOutInterceptor.setRestRequestPayloadBuilder(restRequestPayloadBuilder);
        restSigningOutInterceptor.setRestSignedHeadersBuilder(restSignedHeadersBuilder);
        restSigningOutInterceptor.setHashedRestCanonicalRequestEncryptor(hashedRestCanonicalRequestEncryptor);
        restSigningOutInterceptor.setPayloadHashAlgorithm(NormalizedHashAlgorithm.SHA_256);
        restSigningOutInterceptor.setRequestHashAlgorithm(NormalizedHashAlgorithm.SHA_256);

        return restSigningOutInterceptor;
    }

    private RestSigningInInterceptor createRestSigningInInterceptor() {

        RestSigningInInterceptor restSigningInInterceptor = new RestSigningInInterceptor();
        restSigningInInterceptor.setRestCanonicalHeadersBuilder(restCanonicalHeadersBuilder);
        restSigningInInterceptor.setRestCanonicalQueryStringBuilder(restCanonicalQueryStringBuilder);
        restSigningInInterceptor.setRestCanonicalRequestBuilder(restCanonicalRequestBuilder);
        restSigningInInterceptor.setRestCanonicalURIBuilder(restCanonicalURIBuilder);
        restSigningInInterceptor.setRestRequestPayloadBuilder(restRequestPayloadBuilder);
        restSigningInInterceptor.setRestSignedHeadersBuilder(restSignedHeadersBuilder);
        restSigningInInterceptor.setHashedRestCanonicalRequestDecryptor(hashedRestCanonicalRequestDecryptor);
        return restSigningInInterceptor;
    }
}
