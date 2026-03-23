package com.example.pen;

public class BallPen extends Pen {
    public BallPen(String color, RefillStrategy refillStrategy, StartStrategy startStrategy) {
        super(color, refillStrategy, startStrategy);
    }

    @Override
    public void write(String text) {
        ensureReadyToWrite(text);
        consumeInk(8);
        printWrite("BallPen(smooth)", text.toUpperCase());
    }

    @Override
    protected String getDisplayName() {
        return "BallPen";
    }

    @Override
    protected void afterRefill() {
        System.out.println("BallPen refill: cartridge reset for smoother rolling tip.");
    }
}
