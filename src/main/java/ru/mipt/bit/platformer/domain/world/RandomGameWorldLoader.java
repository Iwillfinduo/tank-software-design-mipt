package ru.mipt.bit.platformer.domain.world;

import java.util.Objects;
import java.util.Random;

public final class RandomGameWorldLoader implements GameWorldLoader {

    private final int width;
    private final int height;
    private final int obstacleCount;
    private final Random random;

    public RandomGameWorldLoader(int width, int height, int obstacleCount) {
        this(width, height, obstacleCount, new Random());
    }

    public RandomGameWorldLoader(
            int width,
            int height,
            int obstacleCount,
            Random random
    ) {
        this.width = width;
        this.height = height;
        this.obstacleCount = obstacleCount;
        this.random = Objects.requireNonNull(random, "random");
    }

    @Override
    public GameWorld load() {
        return GameWorld.random(width, height, obstacleCount, random);
    }
}
