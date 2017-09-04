package com.arcansecurity.skeerel.crypto;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Florian Pradines
 */
public final class KeyHelper {

    /**
     * This class should not be instantiated
     */
    private KeyHelper() {}

    public static SecretKey fromBytes(byte[] key, String algorithm) {
        return new SecretKeySpec(key, 0, key.length, algorithm);
    }
}
