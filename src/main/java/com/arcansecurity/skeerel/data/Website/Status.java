package com.arcansecurity.skeerel.data.Website;

public enum Status {
    NOT_VERIFIED,
    PENDING,
    REJECTED,
    VERIFIED;

    public static Status fromString(String value) {
        for (Status enumVal : values()) {
            if (enumVal.toString().equalsIgnoreCase(value)) {
                return enumVal;
            }
        }

        return null;
    }
}
