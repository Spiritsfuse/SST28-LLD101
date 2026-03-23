package com.example.snakeladder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Board {
    private final int size;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;
    private final Random random;

    public Board(int size, int snakeCount, int ladderCount) {
        this(size, snakeCount, ladderCount, new Random());
    }

    public Board(int size, int snakeCount, int ladderCount, Random random) {
        if (size < 2) {
            throw new IllegalArgumentException("Board size must be at least 2.");
        }
        int maxJumps = Math.max(1, (getLastCell(size) - 2) / 2);
        if (snakeCount > maxJumps || ladderCount > maxJumps) {
            throw new IllegalArgumentException("Too many snakes/ladders requested for board size " + size + ".");
        }

        this.size = size;
        this.random = random;
        this.snakes = new HashMap<Integer, Integer>();
        this.ladders = new HashMap<Integer, Integer>();

        Map<Integer, Integer> allJumps = new HashMap<Integer, Integer>();
        Set<Integer> occupiedCells = new HashSet<Integer>();

        this.snakes.putAll(createRandomSnakes(snakeCount, occupiedCells, allJumps));
        allJumps.putAll(this.snakes);

        this.ladders.putAll(createRandomLadders(ladderCount, occupiedCells, allJumps));
    }

    public int getSize() {
        return size;
    }

    public int getLastCell() {
        return size * size;
    }

    private int getLastCell(int boardSize) {
        return boardSize * boardSize;
    }

    public Map<Integer, Integer> getSnakes() {
        return new HashMap<Integer, Integer>(snakes);
    }

    public Map<Integer, Integer> getLadders() {
        return new HashMap<Integer, Integer>(ladders);
    }

    public Map<Integer, Integer> createRandomSnakes(int count, Set<Integer> occupiedCells, Map<Integer, Integer> existingJumps) {
        Map<Integer, Integer> generated = new HashMap<Integer, Integer>();
        int attempts = 0;
        int maxAttempts = count * 500;

        while (generated.size() < count && attempts < maxAttempts) {
            attempts++;
            int head = randomRange(2, getLastCell() - 1);
            int tail = randomRange(1, head - 1);

            if (!isDifferentRow(head, tail)) {
                continue;
            }
            if (occupiedCells.contains(head) || occupiedCells.contains(tail)) {
                continue;
            }
            if (existingJumps.containsKey(head) || generated.containsKey(head)) {
                continue;
            }
            if (createsCycle(existingJumps, generated, head, tail)) {
                continue;
            }

            generated.put(head, tail);
            occupiedCells.add(head);
            occupiedCells.add(tail);
        }

        if (generated.size() < count) {
            throw new IllegalStateException("Could not generate valid snakes with current constraints.");
        }
        return generated;
    }

    public Map<Integer, Integer> createRandomLadders(int count, Set<Integer> occupiedCells, Map<Integer, Integer> existingJumps) {
        Map<Integer, Integer> generated = new HashMap<Integer, Integer>();
        int attempts = 0;
        int maxAttempts = count * 500;

        while (generated.size() < count && attempts < maxAttempts) {
            attempts++;
            int start = randomRange(2, getLastCell() - 2);
            int end = randomRange(start + 1, getLastCell() - 1);

            if (!isDifferentRow(start, end)) {
                continue;
            }
            if (occupiedCells.contains(start) || occupiedCells.contains(end)) {
                continue;
            }
            if (existingJumps.containsKey(start) || generated.containsKey(start)) {
                continue;
            }
            if (createsCycle(existingJumps, generated, start, end)) {
                continue;
            }

            generated.put(start, end);
            occupiedCells.add(start);
            occupiedCells.add(end);
        }

        if (generated.size() < count) {
            throw new IllegalStateException("Could not generate valid ladders with current constraints.");
        }
        return generated;
    }

    public int resolveJump(int position) {
        Integer snakeTail = snakes.get(position);
        if (snakeTail != null) {
            return snakeTail;
        }
        Integer ladderEnd = ladders.get(position);
        if (ladderEnd != null) {
            return ladderEnd;
        }
        return position;
    }

    private int randomRange(int low, int high) {
        if (low > high) {
            return low;
        }
        return random.nextInt(high - low + 1) + low;
    }

    private boolean isDifferentRow(int cellA, int cellB) {
        int rowA = (cellA - 1) / size;
        int rowB = (cellB - 1) / size;
        return rowA != rowB;
    }

    private boolean createsCycle(
            Map<Integer, Integer> existingJumps,
            Map<Integer, Integer> generated,
            int from,
            int to) {
        Map<Integer, Integer> merged = new HashMap<Integer, Integer>();
        merged.putAll(existingJumps);
        merged.putAll(generated);

        int current = to;
        Set<Integer> visited = new HashSet<Integer>();
        while (merged.containsKey(current)) {
            if (current == from) {
                return true;
            }
            if (!visited.add(current)) {
                return true;
            }
            current = merged.get(current);
        }
        return current == from;
    }
}
