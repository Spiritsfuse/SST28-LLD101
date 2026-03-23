package com.example.pen;

public class InkPen extends Pen {
    public InkPen(String color, RefillStrategy refillStrategy, StartStrategy startStrategy) {
        super(color, refillStrategy, startStrategy);
    }

    @Override
    public void write(String text) {
        ensureReadyToWrite(text);
        consumeInk(12);
        printWrite("InkPen(flow)", text);
    }

    @Override
    protected String getDisplayName() {
        return "InkPen";
    }

    @Override
    protected void afterRefill() {
        System.out.println("InkPen refill: reservoir and nib flow restored.");
    }
}
