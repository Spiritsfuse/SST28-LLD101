package com.example.pen;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter pen type (ink/ball/gel): ");
            String penType = scanner.nextLine();

            System.out.print("Enter mechanism (cap/click): ");
            String mechanism = scanner.nextLine();

            System.out.print("Enter initial color: ");
            String color = scanner.nextLine();

            System.out.print("Use grip decorator? (yes/no): ");
            boolean withGrip = scanner.nextLine().trim().equalsIgnoreCase("yes");

            Pen pen = PenFactory.createPen(penType, mechanism, color, withGrip);

            try {
                pen.write("This should fail because start() was not called.");
            } catch (IllegalStateException ex) {
                System.out.println("Expected check: " + ex.getMessage());
            }

            pen.start();
            pen.write("Design patterns make systems extensible");
            pen.write("Second line from " + pen.getClass().getSimpleName());

            System.out.print("Enter refill color (or press Enter to keep same): ");
            String refillColor = scanner.nextLine();
            pen.refill(refillColor);

            pen.write("After refill ink is full again");
            pen.close();
        }
    }
}
