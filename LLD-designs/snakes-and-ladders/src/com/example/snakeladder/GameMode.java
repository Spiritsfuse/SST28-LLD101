package com.example.snakeladder;

public interface GameMode {
    TurnResult makeMove(Player player, Dice dice, Board board);

    String getName();
}
