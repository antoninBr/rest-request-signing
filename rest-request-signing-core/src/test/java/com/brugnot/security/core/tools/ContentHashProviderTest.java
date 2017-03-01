package com.brugnot.security.core.tools;


import com.brugnot.security.rest.commons.hash.NormalizedHashAlgorithm;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by A505878 on 01/03/2017.
 */
public class ContentHashProviderTest {

    private byte[] testContent = new byte[]{1,2,3,4,5,6,7,8,9};

    @Test
    public void testHashingTowardsAllNormalizedHashAlgorithms() throws Exception {

        for (NormalizedHashAlgorithm normalizedHashAlgorithm : NormalizedHashAlgorithm.values()){
            ContentHashProvider contentHashProvider = new ContentHashProvider(normalizedHashAlgorithm);
            String hexadecimalHash = contentHashProvider.createHexaHash(testContent);
            Assert.assertNotNull(hexadecimalHash);

        }

    }
}
