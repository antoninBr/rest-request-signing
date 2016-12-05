package com.brugnot.security.core.crypt;

import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestDecryptingException;
import com.brugnot.security.rest.commons.user.AuthenticatedUser;

/**
 * Created by Antonin on 27/11/2016.
 */
public interface HashedRestCanonicalRequestDecryptor {

    /**
     * Decrypt the hash rest canonical request for a specific user
     * @param user
     * @param cryptedHashedRestCanonicalRequest
     * @return EncryptionWrapper
     * @throws HashedRestCanonicalRequestDecryptingException
     */
    EncryptionWrapper decryptHashedRestCanonicalRequest(AuthenticatedUser user, String cryptedHashedRestCanonicalRequest) throws HashedRestCanonicalRequestDecryptingException;
}
