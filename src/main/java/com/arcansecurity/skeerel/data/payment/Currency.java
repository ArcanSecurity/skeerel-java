package com.arcansecurity.skeerel.data.payment;

public enum Currency {
    EUR;

    public static Currency fromString(String value) {
        for (Currency enumVal : values()) {
            if (enumVal.toString().equalsIgnoreCase(value)) {
                return enumVal;
            }
        }

        return null;
    }
}
