package com.brugnot.security.core.user;

import com.brugnot.security.rest.commons.user.AuthenticatedUser;
import com.brugnot.security.rest.commons.user.CandidateUser;

/**
 * Created by Antonin on 17/01/2017.
 */
public interface AuthenticatedUserCreator {

    AuthenticatedUser createAuthenticatedUser(CandidateUser candidateUser);
}
