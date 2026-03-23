package com.example.pen;

public class GripDecorator extends PenDecorator {
    public GripDecorator(Pen pen) {
        super(pen);
    }

    @Override
    public void start() {
        System.out.println("Grip support enabled for better comfort.");
        super.start();
    }

    @Override
    public void write(String text) {
        System.out.println("GripDecorator stabilizes handwriting.");
        super.write(text);
    }
}
