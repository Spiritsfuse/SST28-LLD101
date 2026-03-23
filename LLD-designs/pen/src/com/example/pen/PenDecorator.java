package com.example.pen;

public abstract class PenDecorator extends Pen {
    protected final Pen pen;

    protected PenDecorator(Pen pen) {
        super(pen.getColor(), pen.getRefillStrategy(), pen.getStartStrategy());
        this.pen = pen;
    }

    @Override
    public void write(String text) {
        pen.write(text);
    }

    @Override
    public void refill(String newColor) {
        pen.refill(newColor);
    }

    @Override
    public void start() {
        pen.start();
    }

    @Override
    public void close() {
        pen.close();
    }

    @Override
    protected String getDisplayName() {
        return pen.getClass().getSimpleName();
    }

    @Override
    protected void afterRefill() {
        // Delegated to wrapped pen.
    }
}
