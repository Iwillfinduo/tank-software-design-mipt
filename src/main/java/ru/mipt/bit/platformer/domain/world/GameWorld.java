package ru.mipt.bit.platformer.domain.world;

import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.domain.entities.Tank;
import ru.mipt.bit.platformer.domain.level.LevelFileLoader;
import ru.mipt.bit.platformer.domain.level.LevelGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;

public class GameWorld {
    private final Player player;
    private final ArrayList<Obstacle> obstacles;
    private final ArrayList<Enemy> enemies;
    private final int width;
    private final int height;
    private final Set<GridPoint2> reserved = new HashSet<>();

    public GameWorld() {
        this(new Player(1, 1), List.of(new Obstacle(1, 3)), List.of(), -1, -1);
    }

    public GameWorld(Player player, List<Obstacle> obstacles) {
        this(player, obstacles, List.of(), -1, -1);
    }

    public GameWorld(Player player, List<Obstacle> obstacles, List<Enemy> enemies,
                     int width, int height) {
        if (player == null || obstacles == null) {
            throw new IllegalArgumentException("Player and obstacles must not be null");
        }
        this.player = player;
        this.obstacles = new ArrayList<>(obstacles);
        this.enemies = new ArrayList<>(enemies);
        this.width = width;
        this.height = height;
    }

    public GameWorld(int width, int height, int obstacleCount, Random random) {
        this(LevelGenerator.generate(width, height, obstacleCount, random));
    }

    private GameWorld(GameWorld generatedWorld) {
        this(generatedWorld.player, generatedWorld.obstacles, generatedWorld.enemies,
                generatedWorld.width, generatedWorld.height);
    }


    public static GameWorld random(
            int width,
            int height,
            int obstacleCount,
            Random random
    ) {
        return new GameWorld(width, height, obstacleCount, random);
    }

    public static GameWorld random(int width, int height, int obstacleCount, int enemyCount, Random random) {
        return new GameWorld(LevelGenerator.generate(width, height, obstacleCount, enemyCount, random));
    }



    public static GameWorld fromText(String text) {
        return LevelFileLoader.loadText(text);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Enemy> getEnemies() { return enemies; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public boolean isInside(GridPoint2 cell) {
        return width < 0 || (cell.x >= 0 && cell.y >= 0 && cell.x < width && cell.y < height);
    }

    public boolean startMovement(Tank tank, Direction direction) {
        if (tank == null || direction == null || tank.isMoving()) return false;
        GridPoint2 destination = direction.move(tank.getPosition());
        if (!isInside(destination) || reserved.contains(tank.getPosition())
                || reserved.contains(destination) || isBlocked(destination, tank)) return false;
        reserved.add(new GridPoint2(tank.getPosition()));
        reserved.add(new GridPoint2(destination));
        tank.getDestination().set(destination);
        tank.setRotation(direction.getRotation());
        tank.setMovementProgress(0f);
        return true;
    }

    private boolean isBlocked(GridPoint2 cell, Tank moving) {
        for (Obstacle obstacle : obstacles) if (obstacle.getPosition().equals(cell)) return true;
        if (player != moving && (player.getPosition().equals(cell) || player.getDestination().equals(cell))) return true;
        for (Enemy enemy : enemies) {
            if (enemy != moving && (enemy.getPosition().equals(cell) || enemy.getDestination().equals(cell))) return true;
        }
        return false;
    }

    public void finishMovement(Tank tank) {
        reserved.remove(tank.getPosition());
        reserved.remove(tank.getDestination());
        tank.reachDestination();
    }
}
