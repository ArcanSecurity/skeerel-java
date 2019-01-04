package com.arcansecurity.skeerel.data;

import com.arcansecurity.skeerel.util.json.JSONObject;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Florian Pradines
 */
public final class User {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final UUID uid;

    private final String firstName;

    private final String lastName;

    private final String mail;

    private final Boolean mailVerified;

    public User(JSONObject json) {
        if (null == json) {
            throw new IllegalArgumentException("user object cannot be null");
        }

        UUID uid;
        try {
            uid = UUID.fromString(json.optString("uid"));
        } catch (IllegalArgumentException ignore) {
            uid = null;
        }

        this.uid = uid;
        this.firstName = json.optString("first_name", null);
        this.lastName = json.optString("last_name", null);
        this.mail = VALID_EMAIL_ADDRESS_REGEX.matcher(json.optString("mail")).find() ? json.optString("mail") : null;
        this.mailVerified = json.optBoolean("mail_verified", null);
    }

    public UUID getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public boolean isMailVerified() {
        return mailVerified;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", mailVerified=" + mailVerified +
                '}';
    }
}
