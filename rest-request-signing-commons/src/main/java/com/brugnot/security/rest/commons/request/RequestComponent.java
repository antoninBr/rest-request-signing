package com.brugnot.security.rest.commons.request;

import com.brugnot.security.rest.commons.exception.RequestComponentExtractionException;

import java.util.Map;

/**
 * Request Component Interface
 * Will handle all components extraction for several Jax-Rs Implementations
 * Created by Antonin on 20/01/2017.
 */
public interface RequestComponent<C> {

    /**
     * Get Request Component as String
     *
     * @param componentHolder
     * @return extracted component as String
     * @throws RequestComponentExtractionException
     *
     * */
   String getComponentAsString(C componentHolder) throws RequestComponentExtractionException;

    /**
     * Get Request Component as Map
     *
     * @param componentHolder
     * @return extracted component as Map
     * @throws RequestComponentExtractionException
     *
     */
   Map getComponentAsMap(C componentHolder) throws RequestComponentExtractionException;

}
