package com.arcansecurity.skeerel.crypto;


import com.arcansecurity.skeerel.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.io.IOException;
import java.security.*;
import java.security.spec.*;

/**
 * @author Florian Pradines
 */
public final class RSAHelper {

    private RSAHelper() {}

    public static PrivateKey strToPrivateKey(String key) throws GeneralSecurityException, IOException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(key));
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePrivate(keySpec);
    }

    public static byte[] decryptToBytes(String text, PrivateKey privateKey) throws GeneralSecurityException, IOException {
        AlgorithmParameters algp = AlgorithmParameters.getInstance("OAEP");
        AlgorithmParameterSpec paramSpec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
                PSource.PSpecified.DEFAULT);

        algp.init(paramSpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey, algp);
        return cipher.doFinal(Base64.decode(text));
    }
}
