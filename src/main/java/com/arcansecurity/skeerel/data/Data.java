package com.arcansecurity.skeerel.data;

import com.arcansecurity.skeerel.data.delivery.Delivery;
import com.arcansecurity.skeerel.data.payment.Payment;
import com.arcansecurity.skeerel.util.json.JSONObject;

public class Data {

    private final User user;

    private final Payment payment;

    private final Delivery delivery;

    public Data(JSONObject json) {
        user = new User(json.getJSONObject("user"));

        if (json.has("payment")) {
            payment = new Payment(json.getJSONObject("payment"));
            delivery = json.has("delivery") ? new Delivery(json.getJSONObject("delivery")) : null;
        } else {
            payment = null;
            delivery = null;
        }
    }

    public User getUser() {
        return user;
    }

    public Payment getPayment() {
        return payment;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    @Override
    public String toString() {
        return "Data{" +
                "user=" + user +
                ", payment=" + payment +
                ", delivery=" + delivery +
                '}';
    }
}
