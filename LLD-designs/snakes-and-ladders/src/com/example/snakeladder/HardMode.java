package com.example.snakeladder;

import java.util.ArrayList;
import java.util.List;

public class HardMode implements GameMode {
    @Override
    public TurnResult makeMove(Player player, Dice dice, Board board) {
        int start = player.getPosition();
        int current = start;
        int consecutiveSixes = 0;
        List<Integer> rolls = new ArrayList<Integer>();

        while (true) {
            int roll = dice.rollDice();
            rolls.add(roll);

            if (roll == 6) {
                consecutiveSixes++;
                if (consecutiveSixes == 3) {
                    player.setPosition(start);
                    return new TurnResult(start, start, rolls, true);
                }
            }

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
        return "Hard";
    }

    private int applyRoll(int current, int roll, int lastCell, Board board) {
        int candidate = current + roll;
        if (candidate > lastCell) {
            return current;
        }
        return board.resolveJump(candidate);
    }
}
