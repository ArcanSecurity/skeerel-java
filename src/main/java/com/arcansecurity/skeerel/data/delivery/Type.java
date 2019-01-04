package com.arcansecurity.skeerel.data.delivery;

public enum Type {
    HOME, RELAY, COLLECT;

    public static Type fromString(String value) {
        for (Type enumVal : values()) {
            if (enumVal.toString().equalsIgnoreCase(value)) {
                return enumVal;
            }
        }

        return null;
    }
}
