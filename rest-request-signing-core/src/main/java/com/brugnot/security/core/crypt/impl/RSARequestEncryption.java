package com.brugnot.security.core.crypt.impl;

import com.brugnot.security.core.crypt.HashedRestCanonicalRequestDecryptor;
import com.brugnot.security.core.crypt.HashedRestCanonicalRequestEncryptor;
import com.brugnot.security.core.exception.crypt.RequestEncryptionException;

/**
 * Created by Antonin on 27/11/2016.
 */
public class RSARequestEncryption extends AbstractRequestEncryption implements HashedRestCanonicalRequestDecryptor,HashedRestCanonicalRequestEncryptor{

    private static final String RSA_ALGORITHM = "RSA/ECB/NoPadding";

    private static final String AES_ALGORITHM = "AES/CBC/NoPadding";

    public RSARequestEncryption() throws RequestEncryptionException {
        super(RSA_ALGORITHM,AES_ALGORITHM);
    }
}
