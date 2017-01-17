package com.brugnot.security.rest.commons.user;

import java.security.PublicKey;

/**
 * Created by Antonin on 03/12/2016.
 */
public interface CandidateUser extends User {

    /**
     * Candidate user Public Key
     * @return publicKey
     */
    PublicKey getPublicKey();

    /**
     * Get the encrypted Request Decrypting Key
     */
    String getEncryptedRequestKey();

}
