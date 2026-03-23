package com.example.snakeladder;

public class Player {
    private final int playerId;
    private final String name;
    private int position;

    public Player(int playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.position = 0;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    void setPosition(int position) {
        this.position = position;
    }

    public TurnResult makeTurn(GameMode mode, Dice dice, Board board) {
        return mode.makeMove(this, dice, board);
    }
}
