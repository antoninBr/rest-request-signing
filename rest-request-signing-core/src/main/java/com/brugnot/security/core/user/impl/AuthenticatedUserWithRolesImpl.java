package com.brugnot.security.core.user.impl;

import com.brugnot.security.rest.commons.role.Role;
import com.brugnot.security.rest.commons.user.AuthenticatedUserWithRoles;

import java.util.Date;
import java.util.Set;

/**
 * Created by Antonin on 17/01/2017.
 */
public class AuthenticatedUserWithRolesImpl extends AuthenticatedUserImpl implements AuthenticatedUserWithRoles {

    private Set<Role> roles;

    public AuthenticatedUserWithRolesImpl(String userName, Date userAuthenticationDate, Set<Role> roles) {
        super(userName, userAuthenticationDate);
        this.roles = roles;
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
}
