package com.arcansecurity.skeerel;

import com.arcansecurity.skeerel.data.Data;
import com.arcansecurity.skeerel.exception.SkeerelException;
import com.arcansecurity.skeerel.util.Request;
import com.arcansecurity.skeerel.util.json.JSONException;
import com.arcansecurity.skeerel.util.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Florian Pradines
 */
public class Skeerel {

    private static final String API_BASE = "https://api.skeerel.com/v2/";

    private final UUID websiteId;

    private final String websiteSecret;

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

    public Data getData(String token) throws SkeerelException {
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

        try {
            return new Data(json.getJSONObject("data"));
        } catch (JSONException e) {
            throw new SkeerelException("Unexpected error: status is ok, but cannot get data", e);
        }
    }

}
