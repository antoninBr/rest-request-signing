package com.brugnot.security.core.timestamp;

import com.brugnot.security.core.exception.timestamp.RestSignatureTimestampException;

/**
 * Created by Antonin on 09/12/2016.
 */
public interface RestSignatureTimestampService {

    String createTimestamp() throws RestSignatureTimestampException;

    boolean validateTimestamp(String timestamp) throws RestSignatureTimestampException;
}
