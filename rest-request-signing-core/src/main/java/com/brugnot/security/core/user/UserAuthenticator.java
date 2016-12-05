package com.brugnot.security.core.user;

import com.brugnot.security.core.exception.user.UserAuthenticationException;
import com.brugnot.security.rest.commons.user.AuthenticatedUser;

import java.security.Principal;

/**
 * Created by Antonin on 03/12/2016.
 */
public interface UserAuthenticator {

    AuthenticatedUser authenticateUser(String userName, String encryptedRequestKey) throws UserAuthenticationException;
}
