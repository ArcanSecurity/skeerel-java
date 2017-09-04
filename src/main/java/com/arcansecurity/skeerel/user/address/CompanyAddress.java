package com.arcansecurity.skeerel.user.address;

import com.arcansecurity.skeerel.util.json.JSONException;
import com.arcansecurity.skeerel.util.json.JSONObject;

/**
 * @author Florian Pradines
 */
class CompanyAddress extends BaseAddress {

    private String status;

    private String companyName;

    private String vatNumber;

    CompanyAddress(JSONObject address) {
        super(address);

        this.setStatus(address);
        this.setCompanyName(address);
        this.setVatNumber(address);
    }

    private void setStatus(JSONObject address) {
        try {
             status = address.getString("status");
        } catch (JSONException ignore) {}
    }

    private void setCompanyName(JSONObject address) {
        try {
            companyName = address.getString("company_name");
        } catch (JSONException ignore) {}
    }

    private void setVatNumber(JSONObject address) {
        try {
            vatNumber = address.getString("vat_number");
        } catch (JSONException ignore) {}
    }

    public String getStatus() {
        return status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    @Override
    public boolean isIndividual() {
        return false;
    }

    @Override
    public boolean isCompany() {
        return true;
    }

    @Override
    public String toString() {
        return "CompanyAddress{" +
                "status='" + status + '\'' +
                ", companyName='" + companyName + '\'' +
                ", vatNumber='" + vatNumber + '\'' +
                "} " + super.toString();
    }
}
