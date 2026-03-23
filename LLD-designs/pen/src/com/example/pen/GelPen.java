package com.example.pen;

public class GelPen extends Pen {
    public GelPen(String color, RefillStrategy refillStrategy, StartStrategy startStrategy) {
        super(color, refillStrategy, startStrategy);
    }

    @Override
    public void write(String text) {
        ensureReadyToWrite(text);
        consumeInk(10);
        printWrite("GelPen(bold)", text + "!");
    }

    @Override
    protected String getDisplayName() {
        return "GelPen";
    }

    @Override
    protected void afterRefill() {
        System.out.println("GelPen refill: gel chamber refreshed for dense strokes.");
    }
}
