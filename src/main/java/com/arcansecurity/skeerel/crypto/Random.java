package com.arcansecurity.skeerel.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Florian Pradines
 *
 *     Singleton class to avoid the instantiation of multiple
 *     SecureRandom since it is very costly.
 */
public final class Random {

    /**
     * Instance of SecureRandom
     */
    public static final SecureRandom secureRandom = new SecureRandom();

    private Random() {}

    public static String generateSecureString(int numBytes) {
        return new BigInteger(numBytes*8, secureRandom).toString(32);
    }
}
