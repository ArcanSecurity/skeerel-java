package com.arcansecurity.skeerel;

import com.arcansecurity.skeerel.data.Data;
import com.arcansecurity.skeerel.data.Website.Website;
import com.arcansecurity.skeerel.data.payment.Payment;
import com.arcansecurity.skeerel.exception.SkeerelException;
import com.arcansecurity.skeerel.util.Request;
import com.arcansecurity.skeerel.util.json.JSONArray;
import com.arcansecurity.skeerel.util.json.JSONException;
import com.arcansecurity.skeerel.util.json.JSONObject;

import java.util.*;

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

        Map<String, String> parameters = new HashMap<>();
        parameters.put("access_token", token);

        JSONObject json = makeRequest("me", parameters);

        try {
            return new Data(json.getJSONObject("data"));
        } catch (JSONException e) {
            throw new SkeerelException("Unexpected error: status is ok, but cannot get data", e);
        }
    }

    public Payment getPayment(UUID paymentId) throws SkeerelException {
        if (null == paymentId) {
            throw new IllegalArgumentException("payment id cannot be null");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("payment_id", paymentId.toString());

        JSONObject json = makeRequest("payment/get", parameters);

        try {
            return new Payment(json.getJSONObject("data"));
        } catch (JSONException e) {
            throw new SkeerelException("Unexpected error: status is ok, but cannot get data", e);
        }
    }

    public List<Payment> listPayments() throws SkeerelException {
        return listPayments(null, null, null);
    }

    public List<Payment> listPayments(Boolean live) throws SkeerelException {
        return listPayments(live, null, null);
    }

    public List<Payment> listPayments(Integer first, Integer limit) throws SkeerelException {
        return listPayments(null, first, limit);
    }

    public List<Payment> listPayments(Boolean live, Integer first, Integer limit) throws SkeerelException {
        Map<String, String> parameters = new HashMap<>();

        if (live != null) {
            parameters.put("live", live.toString());
        }

        if (first != null) {
            parameters.put("first", first.toString());
        }

        if (limit != null) {
            parameters.put("limit", limit.toString());
        }

        JSONArray jsonPayments = makeRequest("payment/list", parameters).optJSONArray("data");
        List<Payment> payments = new LinkedList<>();
        for (int i = 0; i < jsonPayments.length(); ++i) {
            payments.add(new Payment(jsonPayments.getJSONObject(i)));
        }

        return payments;
    }

    public boolean refundPayment(UUID paymentId) throws SkeerelException {
        return refundPayment(paymentId, (Long) null);
    }

    public boolean refundPayment(UUID paymentId, Integer amount) throws SkeerelException {
        return refundPayment(paymentId, amount != null ? Long.valueOf(amount) : null);
    }

    public boolean refundPayment(UUID paymentId, Long amount) throws SkeerelException {
        if (null == paymentId) {
            throw new IllegalArgumentException("payment id cannot be null");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("payment_id", paymentId.toString());

        if (amount != null) {
            if (amount <= 0) {
                throw new IllegalArgumentException("amount to refund must be higher than 0");
            }
            parameters.put("amount", amount.toString());
        }

        makeRequest("payment/refund", parameters);
        return true;
    }

    public boolean capturePayment(UUID paymentId) throws SkeerelException {
        if (null == paymentId) {
            throw new IllegalArgumentException("payment id cannot be null");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("payment_id", paymentId.toString());

        makeRequest("payment/capture", parameters);
        return true;
    }

    public boolean rejectPayment(UUID paymentId) throws SkeerelException {
        if (null == paymentId) {
            throw new IllegalArgumentException("payment id cannot be null");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("payment_id", paymentId.toString());

        makeRequest("payment/reject", parameters);
        return true;
    }

    public Website getWebsiteDetails() throws SkeerelException {
        return new Website(makeRequest("website/details").optJSONObject("data"));
    }

    private JSONObject makeRequest(String path) throws SkeerelException {
        return makeRequest(path, null);
    }

    private JSONObject makeRequest(String path, Map<String, String> additionalParameters) throws SkeerelException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("website_id", websiteId.toString());
        parameters.put("website_secret", websiteSecret);

        if (additionalParameters != null) {
            parameters.putAll(additionalParameters);
        }

        JSONObject json;
        try {
            json = Request.getJson(API_BASE + path, parameters);
            System.out.println(json);
        } catch (Exception e) {
            throw new SkeerelException("An exception has occurred while making the request to the API", e);
        }

        if (!"ok".equals(json.optString("status"))) {
            throw new SkeerelException("Error " + json.optInt("error_code") + ": " + json.optString("message"));
        }

        return json;
    }
}
