package com.brugnot.security.core.user;

import com.brugnot.security.core.exception.user.SigningUserCreationException;
import com.brugnot.security.rest.commons.user.SigningUser;
import com.brugnot.security.rest.commons.user.User;

/**
 * Created by Antonin on 03/12/2016.
 */
public interface SigningUserCreator<U extends User> {

    SigningUser createSigningUser(U user) throws SigningUserCreationException;
}
