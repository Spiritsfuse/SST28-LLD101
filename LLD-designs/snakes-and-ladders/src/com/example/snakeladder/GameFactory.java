package com.example.snakeladder;

import java.util.ArrayList;
import java.util.List;

public final class GameFactory {
    private GameFactory() {
    }

    public static Game createGame(int n, int playerCount, String version) {
        if (playerCount < 2) {
            throw new IllegalArgumentException("At least 2 players are required.");
        }

        Board board = new Board(n, n, n);
        Dice dice = new Dice();
        GameMode mode = buildMode(GameVersion.fromInput(version));
        List<Player> players = createPlayers(playerCount);
        return new Game(board, players, mode, dice);
    }

    private static GameMode buildMode(GameVersion version) {
        if (version == GameVersion.HARD) {
            return new HardMode();
        }
        return new EasyMode();
    }

    private static List<Player> createPlayers(int playerCount) {
        List<Player> players = new ArrayList<Player>();
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player(i, "Player-" + i));
        }
        return players;
    }
}
