package com.brugnot.security.rest.commons.user;

import java.util.Date;

/**
 * Created by Antonin on 17/01/2017.
 */
public interface AuthenticatedUser extends User{

    /**
     * Get this user Authentication Date
     * @return authenticationDate
     */
    Date getUserAuthenticationDate();

}
