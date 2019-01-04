package com.arcansecurity.skeerel.data.delivery;

import com.arcansecurity.skeerel.util.json.JSONArray;

import java.util.LinkedList;

public class PickUpPoints extends LinkedList<PickUpPoint> {

    public JSONArray toJson() {
        return new JSONArray(this);
    }
}
