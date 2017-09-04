package com.arcansecurity.skeerel.crypto;

import com.arcansecurity.skeerel.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * @author Florian Pradines
 */
public final class HMacHelper {

    /**
     * This class should not be instantiated
     */
    private HMacHelper() {}

    public static SecretKey keyFromBytes(byte[] key) {
        return KeyHelper.fromBytes(key, "HmacSHA256");
    }

    public static String signBase64(String data, SecretKey key) throws GeneralSecurityException, IOException {
        Mac hmacInstance = Mac.getInstance("HmacSHA256");
        hmacInstance.init(key);

        return Base64.encodeBytes(hmacInstance.doFinal(data.getBytes("UTF-8")));
    }

    public static void verifyBase64(String message, String mac, SecretKey key) throws GeneralSecurityException, IOException {
        if (!mac.equals(HMacHelper.signBase64(message, key))) {
            throw new GeneralSecurityException("Mac signature does not match.");
        }
    }
}
