package com.arcansecurity.skeerel.data.address;

import com.arcansecurity.skeerel.util.json.JSONObject;

import java.util.regex.Pattern;

/**
 * @author Florian Pradines
 */
public abstract class BaseAddress {

    private static final Pattern VALID_PHONE_REGEX = Pattern.compile("^\\+?[1-9]\\d{9,14}$");

    private final String name;

    private final String address;

    private final String addressLine2;

    private final String addressLine3;

    private final String zipCode;

    private final String city;

    private final Country country;

    private final String phone;

    BaseAddress(JSONObject json) {
        name = json.optString("name", null);
        address = json.optString("address", null);
        addressLine2 = json.optString("address_line_2", null);
        addressLine3 = json.optString("address_line_3", null);
        zipCode = json.optString("zip_code", null);
        city = json.optString("city", null);
        country = Country.fromAlpha2(json.optString("country_code", null));

        String phone = json.optString("phone_number");
        this.phone = VALID_PHONE_REGEX.matcher(phone).find() ? phone : null;
    }

    public String getName() {
        return name;
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
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressLine3='" + addressLine3 + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country=" + country +
                ", phone='" + phone + '\'' +
                '}';
    }

    public static BaseAddress build(JSONObject json) {
        if (null == json) {
            throw new IllegalArgumentException("address object cannot be null");
        }

        if (json.has("company_name")) {
            return new CompanyAddress(json);
        }

        return new IndividualAddress(json);
    }
}
