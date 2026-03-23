package com.example.pen;

public enum PenType {
    INK,
    BALL,
    GEL;

    public static PenType fromInput(String value) {
        if (value == null) {
            return INK;
        }
        String normalized = value.trim().toUpperCase();
        if (normalized.startsWith("B")) {
            return BALL;
        }
        if (normalized.startsWith("G")) {
            return GEL;
        }
        return INK;
    }
}
