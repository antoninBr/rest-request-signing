package com.brugnot.security.core.user.impl;

import com.brugnot.security.rest.commons.role.Role;
import com.brugnot.security.rest.commons.user.AuthenticatedUserWithRoles;

import java.security.PublicKey;
import java.util.Set;

/**
 * Created by Antonin on 17/01/2017.
 */
public class AuthenticatedUserWithRolesImpl extends AuthenticatedUserImpl implements AuthenticatedUserWithRoles {

    private Set<Role> roles;

    public AuthenticatedUserWithRolesImpl(String userName, PublicKey publicKey, String encryptedRequestKey) {
        super(userName, publicKey, encryptedRequestKey);
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
}
