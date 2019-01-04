package com.arcansecurity.skeerel.data.delivery;

public enum Color {
    GREEN, ORANGE, RED;

    public static Color fromString(String value) {
        for (Color enumVal : values()) {
            if (enumVal.toString().equalsIgnoreCase(value)) {
                return enumVal;
            }
        }

        return null;
    }
}
