package com.brugnot.security.rest.commons.user;

import com.brugnot.security.rest.commons.role.Role;

import java.util.Set;

/**
 * Created by Antonin on 03/12/2016.
 */
public interface AuthenticatedUserWithRoles extends AuthenticatedUser {

    /**
     * Get the Authenticated user Roles
     * @return user Roles
     */
    Set<Role> getRoles();
}
