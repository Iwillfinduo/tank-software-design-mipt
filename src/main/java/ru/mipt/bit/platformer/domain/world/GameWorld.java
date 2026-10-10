package ru.mipt.bit.platformer.domain.world;

import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.domain.level.LevelFileLoader;
import ru.mipt.bit.platformer.domain.level.LevelGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWorld {
    private final Player player;
    private final ArrayList<Obstacle> obstacles;

    public GameWorld() {
        this(new Player(1, 1), List.of(new Obstacle(1, 3)));
    }

    public GameWorld(Player player, List<Obstacle> obstacles) {
        if (player == null || obstacles == null) {
            throw new IllegalArgumentException("Player and obstacles must not be null");
        }
        this.player = player;
        this.obstacles = new ArrayList<>(obstacles);
    }

    public GameWorld(int width, int height, int obstacleCount, Random random) {
        this(LevelGenerator.generate(width, height, obstacleCount, random));
    }

    private GameWorld(GameWorld generatedWorld) {
        this(generatedWorld.player, generatedWorld.obstacles);
    }


    public static GameWorld random(
            int width,
            int height,
            int obstacleCount,
            Random random
    ) {
        return new GameWorld(width, height, obstacleCount, random);
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
}
