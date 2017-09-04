package com.arcansecurity.skeerel.user.address;

import com.arcansecurity.skeerel.util.json.JSONException;
import com.arcansecurity.skeerel.util.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Florian Pradines
 */
class IndividualAddress extends BaseAddress {

    private Title title;

    private String lastName;

    private String firstName;

    private String birthDate;

    IndividualAddress(JSONObject address) {
        super(address);

        this.setTitle(address);
        this.setLastName(address);
        this.setFirstName(address);
        this.setBirthDate(address);
    }

    private void setTitle(JSONObject address) {
        title = Title.fromInt(address.optInt("title"));
    }

    private void setLastName(JSONObject address) {
        try {
            lastName = address.getString("last_name");
        } catch (JSONException ignore) {}
    }

    private void setFirstName(JSONObject address) {
        try {
            firstName = address.getString("first_name");
        } catch (JSONException ignore) {}
    }

    private void setBirthDate(JSONObject address) {
        try {
            String birthDate = address.optString("birth_date");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(birthDate);
            this.birthDate = birthDate;
        } catch (ParseException ignore) {}
    }

    public Title getTitle() {
        return title;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getBirthDate() {
        return birthDate;
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
        return "IndividualAddress{" +
                "title=" + title +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                "} " + super.toString();
    }

    public enum Title {
        MISTER, MISS;

        public static Title fromInt(int title) {
            switch (title) {
                case 0:
                    return MISTER;
                case 1:
                    return MISS;
                default:
                    return null;
            }
        }
    }
}
