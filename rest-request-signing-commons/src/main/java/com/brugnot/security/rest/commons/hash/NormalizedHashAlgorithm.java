package com.brugnot.security.rest.commons.hash;

/**
 * Created by Antonin on 07/12/2016.
 */
public enum NormalizedHashAlgorithm implements HashAlgorithm {


    SHA_256("SHA-256");

    private String hashAlgorithm;

    NormalizedHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public String getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public static HashAlgorithm createNormalizedHashAlgorithm(String hashAlgorithmAsString){

        for(NormalizedHashAlgorithm normalizedHashAlgorithm : values()){
            if(normalizedHashAlgorithm.getHashAlgorithm().equals(hashAlgorithmAsString)){
                return normalizedHashAlgorithm;
            }
        }

        return SHA_256;
    }
}
