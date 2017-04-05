package com.brugnot.security.core.user.impl;

import com.brugnot.security.core.user.AuthenticatedUserCreator;
import com.brugnot.security.rest.commons.user.AuthenticatedUser;
import com.brugnot.security.rest.commons.user.CandidateUser;

import java.util.Date;

/**
 * Authenticated user Creator Implementation
 *
 * Created by Antonin on 17/01/2017.
 */
public class AuthenticatedUserCreatorImpl implements AuthenticatedUserCreator {

    public AuthenticatedUser createAuthenticatedUser(CandidateUser candidateUser) {
        return new AuthenticatedUserImpl(candidateUser.getUserName(),new Date());
    }
}
