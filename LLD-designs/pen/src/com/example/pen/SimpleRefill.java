package com.example.pen;

public class SimpleRefill implements RefillStrategy {
    @Override
    public void refill(Pen pen, String color) {
        if (color != null && !color.trim().isEmpty()) {
            pen.setColor(color.trim());
        }
        pen.setInkLevel(100);
        System.out.println("Refill complete. Ink restored to 100% with color: " + pen.getColor());
    }
}
