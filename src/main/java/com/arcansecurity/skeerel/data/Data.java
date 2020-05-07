package com.arcansecurity.skeerel.data;

import com.arcansecurity.skeerel.data.delivery.Delivery;
import com.arcansecurity.skeerel.data.payment.Payment;
import com.arcansecurity.skeerel.data.payment.PaymentData;
import com.arcansecurity.skeerel.util.json.JSONObject;

public class Data {

    private final User user;

    private final String custom;

    private final Delivery delivery;

    private final PaymentData payment;

    public Data(JSONObject json) {
        if (null == json) {
            throw new IllegalArgumentException("Data cannot be parsed due to incorrect data");
        }

        user = new User(json.getJSONObject("user"));
        custom = json.optString("custom", null);

        if (json.has("payment")) {
            payment = new PaymentData(json.getJSONObject("payment"));
            delivery = json.has("delivery") ? new Delivery(json.getJSONObject("delivery")) : null;
        } else {
            payment = null;
            delivery = null;
        }
    }

    public User getUser() {
        return user;
    }

    public String getCustom() {
        return custom;
    }

    public PaymentData getPayment() {
        return payment;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    @Override
    public String toString() {
        return "Data{" +
                "user=" + user +
                ", custom='" + custom + '\'' +
                ", delivery=" + delivery +
                ", payment=" + payment +
                '}';
    }
}
