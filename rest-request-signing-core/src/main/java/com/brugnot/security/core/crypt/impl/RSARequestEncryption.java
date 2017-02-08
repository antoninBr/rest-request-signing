package com.brugnot.security.core.crypt.impl;

import com.brugnot.security.core.crypt.HashedRestCanonicalRequestDecryptor;
import com.brugnot.security.core.crypt.HashedRestCanonicalRequestEncryptor;

/**
 * Created by Antonin on 27/11/2016.
 */
public class RSARequestEncryption extends AbstractRequestEncryption implements HashedRestCanonicalRequestDecryptor,HashedRestCanonicalRequestEncryptor{

    private static final String KEY_ENCRYPTION_ALGORITHM = "RSA/ECB/NoPadding";

    private static final String REQUEST_ENCRYPTION_ALGORITHM = "AES/CTR/NoPadding";

    public RSARequestEncryption() {
        super(KEY_ENCRYPTION_ALGORITHM, REQUEST_ENCRYPTION_ALGORITHM);
    }
}
