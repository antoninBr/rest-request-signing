package com.brugnot.security.core.crypt;

import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestEncryptingException;
import com.brugnot.security.rest.commons.user.SigningUser;

/**
 * Created by Antonin on 27/11/2016.
 */
public interface HashedRestCanonicalRequestEncryptor {

    /**
     * Encrypt the hash rest canonical request for a specific user
     * @param user
     * @param hashedRestCanonicalRequest
     * @return EncryptionWrapper
     * @throws HashedRestCanonicalRequestEncryptingException
     */
    EncryptionWrapper encryptHashedRestCanonicalRequest(SigningUser user, String hashedRestCanonicalRequest) throws HashedRestCanonicalRequestEncryptingException;
}
