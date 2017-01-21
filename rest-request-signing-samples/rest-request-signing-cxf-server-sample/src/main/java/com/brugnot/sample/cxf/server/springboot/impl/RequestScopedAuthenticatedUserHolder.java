package com.brugnot.sample.cxf.server.springboot.impl;

import com.brugnot.security.core.user.AuthenticatedUserHolder;
import com.brugnot.security.rest.commons.user.AuthenticatedUser;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Created by Antonin on 21/01/2017.
 */
@Component
@Scope(value = "request",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScopedAuthenticatedUserHolder implements AuthenticatedUserHolder {

    private AuthenticatedUser authenticatedUser;

    @Override
    public void hold(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public AuthenticatedUser getAuthenticatedUser() {
        return this.authenticatedUser;
    }
}
