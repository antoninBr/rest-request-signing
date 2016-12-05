package com.brugnot.security.rest.commons.header;

/**
 * Created by Antonin on 27/11/2016.
 */
public interface RestSecurityHeaders {

    /**
     * Get the Normalized Name of the Header
     * @return the rest security header normalized name
     */
    String getNormalizedName();

    /**
     * Is the security header mandatory
     * @return is mandatory
     */
    boolean isMandatory();
}
