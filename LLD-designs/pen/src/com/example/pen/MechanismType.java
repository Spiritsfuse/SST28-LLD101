package com.example.pen;

public enum MechanismType {
    CAP,
    CLICK;

    public static MechanismType fromInput(String value) {
        if (value == null) {
            return CAP;
        }
        String normalized = value.trim().toUpperCase();
        if (normalized.startsWith("CL")) {
            return CLICK;
        }
        return CAP;
    }
}
