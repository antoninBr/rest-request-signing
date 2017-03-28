package com.brugnot.security.cxf.commons;

import com.brugnot.security.cxf.interceptor.exception.RequestPayloadExtractionException;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Antonin on 03/03/2017.
 */
public class InPayloadDataProvider {

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
