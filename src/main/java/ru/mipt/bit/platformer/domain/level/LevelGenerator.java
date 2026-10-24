package ru.mipt.bit.platformer.domain.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.domain.entities.EnemyTank;
import ru.mipt.bit.platformer.domain.world.GameWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class LevelGenerator {

    private LevelGenerator() {
    }

    public static GameWorld generate(int width, int height, int obstacleCount, Random random) {
        return generate(width, height, obstacleCount, 0, random);
    }

    public static GameWorld generate(int width, int height, int obstacleCount, int enemyCount, Random random) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Level dimensions must be positive");
        }
        if (obstacleCount < 0 || enemyCount < 0 || obstacleCount + enemyCount >= width * height) {
            throw new IllegalArgumentException("Obstacle count must leave one free cell for the player");
        }
        if (random == null) {
            throw new IllegalArgumentException("Random source must not be null");
        }

        List<GridPoint2> cells = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells.add(new GridPoint2(x, y));
            }
        }
        Collections.shuffle(cells, random);

        GridPoint2 playerCell = cells.get(0);
        List<Obstacle> obstacles = new ArrayList<>();
        for (int i = 1; i <= obstacleCount; i++) {
            GridPoint2 cell = cells.get(i);
            obstacles.add(new Obstacle(cell.x, cell.y));
        }
        List<Enemy> enemies = new ArrayList<>();
        for (int i = obstacleCount + 1; i <= obstacleCount + enemyCount; i++) {
            GridPoint2 cell = cells.get(i);
            enemies.add(new EnemyTank(cell.x, cell.y));
        }
        return new GameWorld(new Player(playerCell.x, playerCell.y), obstacles, enemies, width, height);
    }
}
