package com.brugnot.security.core.tools;

import java.util.Arrays;

/**
 * Padding Operation Class
 * Created by A505878 on 01/03/2017.
 */
public class PaddingOperation {

    /**
     * UnPad an AES Key byte Array
     * Remove all the zeros before the AES Key (with a beginning zero safety)
     * @param aesKeyByteArray
     * @return processed aesKeyByteArray
     *
     */
    public byte[] unPadAESKeyByteArray(byte[] aesKeyByteArray) {

        //Count the number of zeros
        int i = 0;
        while(aesKeyByteArray[i] == 0) {
            i++;
        }

        //Get for aesKeyLength approximation
        int aesKeyLength = aesKeyByteArray.length - i;

        //Affine the aesKeyLength (key beginning with zeros safety)
        if (aesKeyLength <= 16) {
            aesKeyLength = 16;
        } else if (aesKeyLength <= 24) {
            aesKeyLength = 24;
        } else {
            aesKeyLength = 32;
        }

        //Return the aes key content
        return Arrays.copyOfRange(aesKeyByteArray, aesKeyByteArray.length - aesKeyLength, aesKeyByteArray.length);
    }
}
