package com.arcansecurity.skeerel.data.payment;

public enum ReasonNotCaptured {
    NEED_MANUAL_REVIEW;

    public static ReasonNotCaptured fromString(String value) {
        for (ReasonNotCaptured enumVal : values()) {
            if (enumVal.toString().equalsIgnoreCase(value)) {
                return enumVal;
            }
        }

        return null;
    }
}
