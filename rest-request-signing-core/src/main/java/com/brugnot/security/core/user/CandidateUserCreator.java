package com.brugnot.security.core.user;

import com.brugnot.security.core.exception.user.UserAuthenticationException;
import com.brugnot.security.rest.commons.user.CandidateUser;

/**
 * Created by Antonin on 03/12/2016.
 */
public interface CandidateUserCreator {

    CandidateUser createCandidateUser(String candidateName, String encryptedRequestKey) throws UserAuthenticationException;
}
