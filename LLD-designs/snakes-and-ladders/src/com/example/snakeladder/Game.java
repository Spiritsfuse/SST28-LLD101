package com.example.snakeladder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Game {
    private final Board board;
    private final Queue<Player> players;
    private final List<Player> winners;
    private final GameMode mode;
    private final Dice dice;

    public Game(Board board, List<Player> players, GameMode mode, Dice dice) {
        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players are required.");
        }
        this.board = board;
        this.players = new ArrayDeque<Player>(players);
        this.winners = new ArrayList<Player>();
        this.mode = mode;
        this.dice = dice;
    }

    public void runGame() {
        while (!isGameOver()) {
            makeTurn();
        }
        printSummary();
    }

    public void makeTurn() {
        if (isGameOver()) {
            return;
        }

        Player currentPlayer = players.poll();
        TurnResult result = currentPlayer.makeTurn(mode, dice, board);
        int preJumpPosition = calculateCandidatePosition(result.getStartPosition(), result.getRolls());

        System.out.println(currentPlayer.getName()
                + " rolls " + result.getRolls()
                + " | " + result.getStartPosition() + " -> " + result.getEndPosition()
                + (result.isForfeited() ? " | three consecutive sixes, turn forfeited" : "")
                + jumpMessage(preJumpPosition, result.getEndPosition()));

        if (isWinner(currentPlayer)) {
            winners.add(currentPlayer);
            System.out.println("Winner: " + currentPlayer.getName());
            return;
        }

        players.offer(currentPlayer);
    }

    public boolean isWinner(Player player) {
        return player.getPosition() == board.getLastCell();
    }

    public boolean isGameOver() {
        return players.size() < 2;
    }

    public void printBoardLayout() {
        System.out.println("Board size: " + board.getSize() + " x " + board.getSize());
        System.out.println("Snakes (head -> tail): " + formatMap(board.getSnakes()));
        System.out.println("Ladders (start -> end): " + formatMap(board.getLadders()));
        System.out.println("Mode: " + mode.getName());
    }

    public void printSummary() {
        System.out.println("--- Game Over ---");
        if (winners.isEmpty()) {
            System.out.println("No winners yet.");
        } else {
            for (int i = 0; i < winners.size(); i++) {
                Player winner = winners.get(i);
                System.out.println((i + 1) + ". " + winner.getName() + " (Player-" + winner.getPlayerId() + ")");
            }
        }

        if (!players.isEmpty()) {
            Player remaining = players.peek();
            System.out.println("Remaining player in play: " + remaining.getName());
        }
    }

    private int calculateCandidatePosition(int start, List<Integer> rolls) {
        int current = start;
        for (int roll : rolls) {
            int candidate = current + roll;
            if (candidate <= board.getLastCell()) {
                current = candidate;
            }
        }
        return current;
    }

    private String jumpMessage(int preJumpPosition, int endPosition) {
        if (preJumpPosition == endPosition) {
            return "";
        }
        if (preJumpPosition > endPosition) {
            return " | snake bite: " + preJumpPosition + " -> " + endPosition;
        }
        return " | ladder climb: " + preJumpPosition + " -> " + endPosition;
    }

    private String formatMap(Map<Integer, Integer> map) {
        if (map.isEmpty()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder("[");
        boolean first = true;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (!first) {
                builder.append(", ");
            }
            builder.append(entry.getKey()).append("->").append(entry.getValue());
            first = false;
        }
        builder.append("]");
        return builder.toString();
    }
}
