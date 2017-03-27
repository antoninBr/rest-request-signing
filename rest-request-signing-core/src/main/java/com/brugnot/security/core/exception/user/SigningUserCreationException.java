package com.brugnot.security.core.exception.user;

import com.brugnot.security.core.exception.user.code.SigningUserCreationErrCode;

/**
 * Created by Antonin on 03/12/2016.
 */
public class SigningUserCreationException extends UserException {

    /**
     * Signing User Creation Error Code
     */
    private SigningUserCreationErrCode errCode;

    /**
     * Exception Constructor
     * @param errCode
     * @param message
     */
    public SigningUserCreationException(SigningUserCreationErrCode errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    /**
     * Exception Constructor
     * @param errCode
     * @param message
     * @param cause
     */
    public SigningUserCreationException(SigningUserCreationErrCode errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    /**
     * Get the Error Code
     * @return errCode
     */
    public SigningUserCreationErrCode getErrCode() {
        return errCode;
    }
}
