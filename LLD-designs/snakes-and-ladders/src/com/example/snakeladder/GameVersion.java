package com.example.snakeladder;

public enum GameVersion {
    EASY,
    HARD;

    public static GameVersion fromInput(String version) {
        if (version == null) {
            return EASY;
        }
        String normalized = version.trim().toUpperCase();
        if (normalized.startsWith("H")) {
            return HARD;
        }
        return EASY;
    }
}
