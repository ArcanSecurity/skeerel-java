package com.arcansecurity.skeerel.crypto;

import com.arcansecurity.skeerel.util.Base64;
import com.arcansecurity.skeerel.util.json.JSONException;
import com.arcansecurity.skeerel.util.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * @author Florian Pradines
 */
public final class AESHelper {

    private static final int IV_SIZE = 16;

    /**
     * This class should not be instantiated
     */
    private AESHelper() {}

    public static SecretKey keyFromBytes(byte[] key) {
        return KeyHelper.fromBytes(key, "AES");
    }

    public static JSONObject decrypt(String text, SecretKey key) throws GeneralSecurityException, IOException {
        byte[] cipher = Base64.decode(text);

        if (cipher.length <= IV_SIZE) {
            throw new GeneralSecurityException("IV is not contained in the ciphertext");
        }

        byte[] iv = Arrays.copyOfRange(cipher, 0, IV_SIZE);
        cipher = Arrays.copyOfRange(cipher, IV_SIZE, cipher.length);

        Cipher cipherInstance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherInstance.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv), Random.secureRandom);

        try {
            return new JSONObject(new String(cipherInstance.doFinal(cipher), "UTF-8"));
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }
}
