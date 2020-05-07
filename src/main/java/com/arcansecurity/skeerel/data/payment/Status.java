package com.arcansecurity.skeerel.data.payment;

public enum Status {
    PENDING,
    REVIEWING,
    CANCELED,
    PAID,
    REFUNDED,
    REJECTED,
    PARTIALLY_REFUNDED,
    REFUND_FAILED,
    DISPUTED,
    DISPUTE_LOST,
    DISPUTE_REVIEW;

    public static Status fromString(String value) {
        for (Status enumVal : values()) {
            if (enumVal.toString().equalsIgnoreCase(value)) {
                return enumVal;
            }
        }

        return null;
    }
}
