package com.brugnot.sample.commons.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Test Endpoint Interface for all Samples Implementations
 * Created by Antonin on 12/12/2016.
 */
@Path("/test")
public interface Test {

    @GET
    @Path("/simpleResponse")
    String getTestResponse();

    @GET
    @Path("/simpleResponseWithParam")
    String getTestResponseWithQueryParam(@QueryParam("value") String value);
}
