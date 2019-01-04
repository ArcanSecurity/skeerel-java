package com.arcansecurity.skeerel.data.payment;

public enum FraudRisk {
    NORMAL, ELEVATED, HIGHEST, UNKNOWN;

    public static FraudRisk fromString(String value) {
        for (FraudRisk enumVal : values()) {
            if (enumVal.toString().equalsIgnoreCase(value)) {
                return enumVal;
            }
        }

        return null;
    }
}
