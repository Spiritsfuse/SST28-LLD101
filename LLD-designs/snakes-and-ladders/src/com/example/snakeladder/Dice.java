package com.example.snakeladder;

import java.util.Random;

public class Dice {
    private final int sides;
    private final Random random;

    public Dice() {
        this(6, new Random());
    }

    public Dice(int sides, Random random) {
        if (sides < 2) {
            throw new IllegalArgumentException("Dice must have at least 2 sides.");
        }
        this.sides = sides;
        this.random = random;
    }

    public int rollDice() {
        return random.nextInt(sides) + 1;
    }
}
