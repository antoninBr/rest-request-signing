package com.brugnot.security.core.user;

import com.brugnot.security.rest.commons.user.AuthenticatedUser;

/**
 * Created by Antonin on 17/01/2017.
 */
public interface AuthenticatedUserHolder {

    void hold(AuthenticatedUser authenticatedUser);

    AuthenticatedUser getAuthenticatedUser();
}
