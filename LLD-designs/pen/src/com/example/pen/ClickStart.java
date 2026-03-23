package com.example.pen;

public class ClickStart implements StartStrategy {
    @Override
    public void start(Pen pen) {
        System.out.println("Click mechanism activated for " + pen.getClass().getSimpleName() + ". Ready to write.");
    }
}
