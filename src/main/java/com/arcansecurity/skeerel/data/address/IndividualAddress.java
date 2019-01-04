package com.arcansecurity.skeerel.data.address;

import com.arcansecurity.skeerel.util.json.JSONObject;

/**
 * @author Florian Pradines
 */
class IndividualAddress extends BaseAddress {

    IndividualAddress(JSONObject json) {
        super(json);
    }

    @Override
    public boolean isIndividual() {
        return true;
    }

    @Override
    public boolean isCompany() {
        return false;
    }

    @Override
    public String toString() {
        return "IndividualAddress{}" + super.toString();
    }
}
