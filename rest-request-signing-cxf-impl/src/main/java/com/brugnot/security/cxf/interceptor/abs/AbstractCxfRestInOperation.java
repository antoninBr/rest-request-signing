package com.brugnot.security.cxf.interceptor.abs;

import com.brugnot.security.cxf.interceptor.exception.RequestPayloadExtractionException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Antonin on 17/01/2017.
 */
public abstract class AbstractCxfRestInOperation extends
        AbstractPhaseInterceptor<Message> {

    protected HashAlgorithm payloadHashAlgorithm;

    protected HashAlgorithm requestHashAlgorithm;

    public AbstractCxfRestInOperation() {
        super(Phase.READ);
    }


    protected String buildRestRequestFromMessage(Message message) {
        return null;
    }
    /**
     * Get Inbound Message Content Data (create a copy of the message
     * inputStream)
     *
     * @param message
     * @return content data
     */
    public static byte[] getInContentData(Message message) throws RequestPayloadExtractionException {

        byte[] contentData = null;

        try {
            // Create a Copy of the Input Stream
            CachedOutputStream cachedOutputStream = new CachedOutputStream();
            InputStream is = message.getContent(InputStream.class);
            IOUtils.copy(is, cachedOutputStream);

            message.setContent(InputStream.class, cachedOutputStream.getInputStream());

            if (cachedOutputStream.getInputStream() != null) {
                contentData = IOUtils.readBytesFromStream(cachedOutputStream.getInputStream());
            }

        } catch (IOException e) {
            throw new RequestPayloadExtractionException("Error while Reading the Data from the Request Payload OutputStream",e);
        }

        return contentData;
    }

}
