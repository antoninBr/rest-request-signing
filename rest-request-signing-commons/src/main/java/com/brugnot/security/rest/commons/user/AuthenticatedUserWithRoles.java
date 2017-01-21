package com.brugnot.security.rest.commons.user;

import com.brugnot.security.rest.commons.role.Role;

/**
 * Authenticated User With One or Several Roles
 * Created by Antonin on 03/12/2016.
 */
public interface AuthenticatedUserWithRoles extends AuthenticatedUser {

    /**
     * Is the Authenticated has the specified Role
     * @return user Roles
     */
    boolean hasRole(Role role);
}
