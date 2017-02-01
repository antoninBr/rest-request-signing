package com.brugnot.security.core.builder.v1;

import com.brugnot.security.core.builder.RestSignedHeadersBuilder;
import com.brugnot.security.core.builder.v1.abs.AbstractBuilderV1;
import com.brugnot.security.core.exception.builder.RestSignedHeadersBuildingException;
import org.perf4j.aop.Profiled;

import java.util.Set;

/**
 * Rest Signed Headers Builder V1
 * Created by Antonin on 12/12/2016.
 */
public class RestSignedHeadersBuilderV1 extends AbstractBuilderV1 implements RestSignedHeadersBuilder {

    @Profiled
    public String buildRestSignedHeaders(Set<String> restHeaders) throws RestSignedHeadersBuildingException {
        return "";
    }
}
