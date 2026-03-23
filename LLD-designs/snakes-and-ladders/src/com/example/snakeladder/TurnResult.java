package com.example.snakeladder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TurnResult {
    private final int startPosition;
    private final int endPosition;
    private final List<Integer> rolls;
    private final boolean forfeited;

    public TurnResult(int startPosition, int endPosition, List<Integer> rolls, boolean forfeited) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.rolls = Collections.unmodifiableList(new ArrayList<Integer>(rolls));
        this.forfeited = forfeited;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public List<Integer> getRolls() {
        return rolls;
    }

    public boolean isForfeited() {
        return forfeited;
    }
}
