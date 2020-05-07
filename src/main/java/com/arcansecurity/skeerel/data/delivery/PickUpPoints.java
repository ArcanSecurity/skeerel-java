package com.arcansecurity.skeerel.data.delivery;

import com.arcansecurity.skeerel.util.json.JSONArray;

import java.util.LinkedList;

public class PickUpPoints extends LinkedList<PickUpPoint> {

    public JSONArray toJson() {
        JSONArray array = new JSONArray();
        forEach(pickUpPoint -> array.put(pickUpPoint.toJson()));

        return array;
    }
}
