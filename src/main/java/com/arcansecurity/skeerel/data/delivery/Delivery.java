package com.arcansecurity.skeerel.data.delivery;

import com.arcansecurity.skeerel.data.address.BaseAddress;
import com.arcansecurity.skeerel.util.json.JSONObject;

public final class Delivery {

    private final String methodId;

    private final BaseAddress shippingAddress;

    private final String pickUpPointId;

    public Delivery(JSONObject json) {
        if (null == json) {
            throw new IllegalArgumentException("delivery object cannot be null");
        }

        methodId = json.optString("method_id", null);
        shippingAddress = BaseAddress.build(json.optJSONObject("shipping_address"));
        pickUpPointId = json.optString("pick_up_point_id", null);
    }

    public String getMethodId() {
        return methodId;
    }

    public BaseAddress getShippingAddress() {
        return shippingAddress;
    }

    public String getPickUpPointId() {
        return pickUpPointId;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "methodId='" + methodId + '\'' +
                ", shippingAddress=" + shippingAddress +
                ", pickUpPointId='" + pickUpPointId + '\'' +
                '}';
    }
}
