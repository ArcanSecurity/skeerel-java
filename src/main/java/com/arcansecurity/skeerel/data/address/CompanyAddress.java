package com.arcansecurity.skeerel.data.address;

import com.arcansecurity.skeerel.util.json.JSONObject;

/**
 * @author Florian Pradines
 */
class CompanyAddress extends BaseAddress {

    private final String status;

    private final String companyName;

    private final String vat;

    CompanyAddress(JSONObject json) {
        super(json);

        status = json.optString("status", null);
        companyName = json.optString("company_name", null);
        vat = json.optString("vat", null);
    }

    public String getStatus() {
        return status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getVat() {
        return vat;
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
                ", vat='" + vat + '\'' +
                '}' + super.toString();
    }
}
