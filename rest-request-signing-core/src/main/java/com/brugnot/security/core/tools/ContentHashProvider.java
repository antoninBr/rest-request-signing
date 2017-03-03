package com.brugnot.security.core.tools;

import com.brugnot.security.core.exception.digest.HashCreationException;
import com.brugnot.security.rest.commons.hash.HashAlgorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Content Hash Provider
 * Created by A505878 on 01/03/2017.
 */
public class ContentHashProvider {


    /**
     * Hash Algorithm to use
     */
    private String hashAlgorithm;

    /**
     * Constructor using the provided HashAlgorithm
     * @param hashAlgorithm
     */
    public ContentHashProvider(HashAlgorithm hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm.getHashAlgorithm();
    }

    /**
     * Create the Hexadecimal String of the Hash of the provided Data
     * @param data
     * @return The Hexadecimal String of the Hash of the Data
     * @throws HashCreationException
     */
    public String createHexadecimalHash(byte[] data) throws HashCreationException {

        StringBuilder digestDataString = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);

            byte[] digestData = md.digest(data);

            for (int i = 0; i < digestData.length; i++) {
                if ((0xff & digestData[i]) < 0x10) {
                    digestDataString.append("0"
                            + Integer.toHexString((0xFF & digestData[i])));
                } else {
                    digestDataString.append(Integer.toHexString(0xFF & digestData[i]));
                }
            }

            return digestDataString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new HashCreationException("Error while creating data hash : algorithm not recognize",e);
        }
    }
}
