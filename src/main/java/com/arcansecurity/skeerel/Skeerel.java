package com.arcansecurity.skeerel;


import com.arcansecurity.skeerel.crypto.AESHelper;
import com.arcansecurity.skeerel.crypto.HMacHelper;
import com.arcansecurity.skeerel.crypto.RSAHelper;
import com.arcansecurity.skeerel.crypto.Random;
import com.arcansecurity.skeerel.exception.SkeerelException;
import com.arcansecurity.skeerel.user.address.BaseAddress;
import com.arcansecurity.skeerel.user.User;
import com.arcansecurity.skeerel.util.Request;
import com.arcansecurity.skeerel.util.json.JSONObject;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Florian Pradines
 */
public class Skeerel {

    private static final String API_BASE = "https://api.skeerel.com/v2/";

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final UUID websiteId;

    private final String websiteSecret;

    private PrivateKey rsaPrivateKey;

    public Skeerel(UUID websiteId, String websiteSecret) {
        if (null == websiteId) {
            throw new IllegalArgumentException("websiteId cannot be null");
        }

        if (null == websiteSecret) {
            throw new IllegalArgumentException("websiteSecret cannot be null");
        }

        this.websiteId = websiteId;
        this.websiteSecret = websiteSecret;
    }

    public Skeerel(UUID websiteId, String websiteSecret, String rsaPrivateKey) throws GeneralSecurityException, IOException {
        this(websiteId, websiteSecret);

        if (null == rsaPrivateKey) {
            throw new IllegalArgumentException("rsaPrivateKey cannot be null");
        }

        this.rsaPrivateKey = RSAHelper.strToPrivateKey(rsaPrivateKey);
    }

    public String generateStateParameter() {
        return Random.generateSecureString(40);
    }

    public User getUser(String token) throws SkeerelException {
        if (null == token) {
            throw new IllegalArgumentException("token cannot be null");
        }

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", token);
        parameters.put("website_id", websiteId.toString());
        parameters.put("website_secret", websiteSecret);

        JSONObject json;
        try {
            json = Request.getJson(API_BASE + "me", parameters);
        } catch (Exception e) {
            throw new SkeerelException("An exception has occurred while making the request to the API", e);
        }

        if (!"ok".equals(json.optString("status"))) {
            throw new SkeerelException("Error " + json.optInt("error_code") + ": " + json.optString("message"));
        }

        UUID uid;
        try {
            uid = UUID.fromString(json.optString("uid"));
        } catch (IllegalArgumentException e) {
            throw new SkeerelException("Unexpected error: status is ok, but cannot get user id");
        }

        String mail = json.optString("mail");
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(mail).find()) {
            throw new SkeerelException("Unexpected error: status is ok, but cannot get user mail");
        }

        BaseAddress shippingAddress = null;
        BaseAddress billingAddress = null;
        if (null != rsaPrivateKey && json.has("addresses")) {
            JSONObject addresses = verifySignatureAndDecrypt(json.optJSONObject("addresses"));
            JSONObject jsonShippingAddress = addresses.optJSONObject("shipping_address");
            JSONObject jsonBillingAddress = addresses.optJSONObject("billing_address");

            if (null != jsonShippingAddress) {

            }

            if (null != jsonBillingAddress) {

            }
        }

        return null;
    }

    private JSONObject verifySignatureAndDecrypt(JSONObject complexCipher) throws SkeerelException {
        String encryptedKey;
        String cipher;
        String mac;
        if (null == complexCipher ||
                (encryptedKey = complexCipher.optString("encrypted_key")).equals("") ||
                (cipher = complexCipher.optString("cipher")).equals("") ||
                (mac = complexCipher.optString("mac")).equals("")) {
            throw new IllegalArgumentException("addresses to verify and decrypt must be a json object containing 'encrypted_key', 'cipher', 'mac'");
        }

        byte[] sessionKey = decryptSessionKey(encryptedKey);
        SecretKey aesKey = AESHelper.keyFromBytes(Arrays.copyOfRange(sessionKey, 0, 32));
        SecretKey hmacKey = HMacHelper.keyFromBytes(Arrays.copyOfRange(sessionKey, 32, sessionKey.length));

        try {
            HMacHelper.verifyBase64(cipher, mac, hmacKey);
        } catch (Exception e) {
            throw new SkeerelException("Cannot verify the hmac signature of addresses", e);
        }

        try {
            return AESHelper.decrypt(cipher, aesKey);
        } catch (Exception e) {
            throw new SkeerelException("Cannot decrypt addresses", e);
        }
    }

    private byte[] decryptSessionKey(String encryptedKey) throws SkeerelException {
        try {
            byte[] sessionKey = RSAHelper.decryptToBytes(encryptedKey, rsaPrivateKey);
            if (64 != sessionKey.length) {
                throw new SkeerelException("Session key must be 64 bytes long, got " + sessionKey.length);
            }

            return sessionKey;
        } catch (Exception e) {
            throw new SkeerelException("Cannot decrypt session key with the provided RSA private key", e);
        }
    }
}
