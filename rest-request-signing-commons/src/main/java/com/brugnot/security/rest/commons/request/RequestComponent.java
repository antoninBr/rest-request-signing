package com.brugnot.security.rest.commons.request;

/**
 * Request Component Interface
 * Will handle all components extraction for several Jax-Rs Implementations
 * Created by Antonin on 20/01/2017.
 */
public interface RequestComponent {

    /**
     * Is the Component Nullable
     * @return nullable
     */
   boolean isNullable();

    /**
     * Get the Request Component Normalized Name
     * @return component name
     */
   String getName();
}
