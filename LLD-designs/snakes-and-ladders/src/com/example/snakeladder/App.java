package com.example.snakeladder;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter board size n: ");
        int n = scanner.nextInt();

        System.out.print("Enter number of players x: ");
        int x = scanner.nextInt();

        System.out.print("Enter version (easy/hard): ");
        String version = scanner.next();

        Game game = GameFactory.createGame(n, x, version);
        game.printBoardLayout();

        while (!game.isGameOver()) {
            game.makeTurn();
        }

        game.printSummary();
        scanner.close();
    }
}
