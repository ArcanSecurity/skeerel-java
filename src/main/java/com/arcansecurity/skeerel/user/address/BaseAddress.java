package com.arcansecurity.skeerel.user.address;

import com.arcansecurity.skeerel.util.Country;
import com.arcansecurity.skeerel.util.json.JSONException;
import com.arcansecurity.skeerel.util.json.JSONObject;

import java.util.regex.Pattern;

/**
 * @author Florian Pradines
 */
public abstract class BaseAddress {

    private static final Pattern VALID_PHONE_REGEX = Pattern.compile("^\\+?[1-9]\\d{9,14}$");

    private String address;

    private String addressLine2;

    private String addressLine3;

    private String zipCode;

    private String city;

    private Country country;

    private String phone;

    BaseAddress(JSONObject address) {
        this.setAddress(address);
        this.setAddressLine2(address);
        this.setAddressLine3(address);
        this.setZipCode(address);
        this.setCity(address);
        this.setCountry(address);
        this.setPhone(address);
    }

    private void setAddress(JSONObject address) {
        try {
            this.address = address.getString("address");
        } catch (JSONException ignore) {}
    }

    private void setAddressLine2(JSONObject address) {
        try {
            addressLine2 = address.getString("address_line_2");
        } catch (JSONException ignore) {}
    }

    private void setAddressLine3(JSONObject address) {
        try {
            addressLine3 = address.getString("address_line_3");
        } catch (JSONException ignore) {}
    }

    private void setZipCode(JSONObject address) {
        try {
            zipCode = address.getString("zip_code");
        } catch (JSONException ignore) {}
    }

    private void setCity(JSONObject address) {
        try {
            city = address.getString("city");
        } catch (JSONException ignore) {}
    }

    private void setCountry(JSONObject address){
        country = Country.fromAlpha2(address.optString("country_code"));
    }

    private void setPhone(JSONObject address) {
        String phone = address.optString("phone_number");
        if (VALID_PHONE_REGEX.matcher(phone).find()) {
            this.phone = phone;
        }
    }

    public String getAddress() {
        return address;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public Country getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public abstract boolean isIndividual();

    public abstract boolean isCompany();

    @Override
    public String toString() {
        return "BaseAddress{" +
                "address='" + address + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressLine3='" + addressLine3 + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country=" + country +
                ", phone='" + phone + '\'' +
                '}';
    }

    public static BaseAddress build(JSONObject address) {
        if (null == address) {
            throw new IllegalArgumentException("address cannot be null");
        }

        if (address.has("company_name")) {
            return new CompanyAddress(address);
        }

        return new IndividualAddress(address);
    }
}
