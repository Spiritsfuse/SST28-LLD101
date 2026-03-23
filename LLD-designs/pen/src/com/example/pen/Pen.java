package com.example.pen;

public abstract class Pen {
    private String color;
    private final RefillStrategy refillStrategy;
    private final StartStrategy startStrategy;
    private boolean started;
    private int inkLevel;

    protected Pen(String color, RefillStrategy refillStrategy, StartStrategy startStrategy) {
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("Color is required.");
        }
        this.color = color;
        this.refillStrategy = refillStrategy;
        this.startStrategy = startStrategy;
        this.started = false;
        this.inkLevel = 100;
    }

    public abstract void write(String text);

    public void refill(String newColor) {
        refillStrategy.refill(this, newColor);
        afterRefill();
    }

    public void start() {
        startStrategy.start(this);
        this.started = true;
    }

    public void close() {
        this.started = false;
        System.out.println(getDisplayName() + " closed.");
    }

    public String getColor() {
        return color;
    }

    public int getInkLevel() {
        return inkLevel;
    }

    public boolean isStarted() {
        return started;
    }

    public RefillStrategy getRefillStrategy() {
        return refillStrategy;
    }

    public StartStrategy getStartStrategy() {
        return startStrategy;
    }

    void setColor(String color) {
        this.color = color;
    }

    void setInkLevel(int inkLevel) {
        this.inkLevel = inkLevel;
    }

    protected void ensureReadyToWrite(String text) {
        if (!started) {
            throw new IllegalStateException("Call start() before write().");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty.");
        }
        if (inkLevel <= 0) {
            throw new IllegalStateException("Ink is empty. Refill before writing.");
        }
    }

    protected void consumeInk(int amount) {
        if (amount <= 0) {
            return;
        }
        this.inkLevel = Math.max(0, this.inkLevel - amount);
    }

    protected void printWrite(String prefix, String content) {
        System.out.println(prefix + " [color=" + color + "] -> " + content + " (ink=" + inkLevel + "%)");
    }

    protected abstract String getDisplayName();

    protected abstract void afterRefill();
}
