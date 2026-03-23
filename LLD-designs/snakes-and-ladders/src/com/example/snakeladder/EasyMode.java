package com.example.snakeladder;

import java.util.ArrayList;
import java.util.List;

public class EasyMode implements GameMode {
    @Override
    public TurnResult makeMove(Player player, Dice dice, Board board) {
        int current = player.getPosition();
        int start = current;
        List<Integer> rolls = new ArrayList<Integer>();

        while (true) {
            int roll = dice.rollDice();
            rolls.add(roll);
            current = applyRoll(current, roll, board.getLastCell(), board);
            if (roll != 6) {
                break;
            }
        }

        player.setPosition(current);
        return new TurnResult(start, current, rolls, false);
    }

    @Override
    public String getName() {
        return "Easy";
    }

    private int applyRoll(int current, int roll, int lastCell, Board board) {
        int candidate = current + roll;
        if (candidate > lastCell) {
            return current;
        }
        return board.resolveJump(candidate);
    }
}
