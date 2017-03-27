package com.brugnot.security.core.exception.user;

import com.brugnot.security.core.exception.user.code.UserAuthenticationErrCode;

/**
 * Created by Antonin on 03/12/2016.
 */
public class UserAuthenticationException extends UserException {

    /**
     * User Authentication Error Code
     */
    UserAuthenticationErrCode errCode;

    /**
     * Exception Constructor
     * @param errCode
     * @param message
     */
    public UserAuthenticationException(UserAuthenticationErrCode errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    /**
     * Exception Constructor
     * @param errCode
     * @param message
     * @param cause
     */
    public UserAuthenticationException(UserAuthenticationErrCode errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    /**
     * Get the Error Code
     * @return errCode
     */
    public UserAuthenticationErrCode getErrCode() {
        return errCode;
    }
}
