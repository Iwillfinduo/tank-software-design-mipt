package ru.mipt.bit.platformer.domain.level;

import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.domain.entities.EnemyTank;
import ru.mipt.bit.platformer.domain.world.GameWorld;
import java.util.ArrayList;
import java.util.List;

public final class LevelFileLoader {

    private LevelFileLoader() {
    }


    public static GameWorld load(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Level must contain at least one row");
        }

        int width = lines.get(0).length();
        if (width == 0) {
            throw new IllegalArgumentException("Level rows must not be empty");
        }

        Player player = null;
        List<Obstacle> obstacles = new ArrayList<>();
        List<Enemy> enemies = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            int gameY = lines.size() - 1 - y;
            String line = lines.get(y);
            if (line.length() != width) {
                throw new IllegalArgumentException("Level rows must have equal length");
            }
            for (int x = 0; x < width; x++) {
                char cell = line.charAt(x);
                if (cell == 'T') {
                    obstacles.add(new Obstacle(x, gameY));
                } else if (cell == 'E') {
                    enemies.add(new EnemyTank(x, gameY));
                } else if (cell == 'X') {
                    if (player != null) {
                        throw new IllegalArgumentException("Level must contain exactly one player start");
                    }
                    player = new Player(x, gameY);
                } else if (cell != '_') {
                    throw new IllegalArgumentException("Unknown level character: " + cell);
                }
            }
        }
        if (player == null) {
            throw new IllegalArgumentException("Level must contain one player start");
        }
        return new GameWorld(player, obstacles, enemies, width, lines.size());
    }

    public static GameWorld loadText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Level text must not be null");
        }
        return load(List.of(text.split("\\R")));
    }
}
