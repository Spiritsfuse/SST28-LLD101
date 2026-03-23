package com.example.pen;

public class CapStart implements StartStrategy {
    @Override
    public void start(Pen pen) {
        System.out.println("Cap removed for " + pen.getClass().getSimpleName() + ". Ready to write.");
    }
}
