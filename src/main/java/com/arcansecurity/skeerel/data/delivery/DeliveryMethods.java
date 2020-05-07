package com.arcansecurity.skeerel.data.delivery;

import com.arcansecurity.skeerel.util.json.JSONArray;

import java.util.LinkedList;

public class DeliveryMethods extends LinkedList<DeliveryMethod> {

    public JSONArray toJson() {
        JSONArray array = new JSONArray();
        forEach(deliveryMethod -> array.put(deliveryMethod.toJson()));

        return array;
    }
}
