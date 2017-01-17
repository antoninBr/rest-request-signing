package com.brugnot.security.core.crypt;

import com.brugnot.security.core.crypt.wrapper.DecryptionWrapper;
import com.brugnot.security.core.exception.crypt.HashedRestCanonicalRequestDecryptingException;
import com.brugnot.security.rest.commons.user.CandidateUser;

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
    DecryptionWrapper decryptHashedRestCanonicalRequest(CandidateUser user, String cryptedHashedRestCanonicalRequest) throws HashedRestCanonicalRequestDecryptingException;
}
