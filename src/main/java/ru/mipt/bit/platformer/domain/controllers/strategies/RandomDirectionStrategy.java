package ru.mipt.bit.platformer.domain.controllers.strategies;

import ru.mipt.bit.platformer.util.Direction;

import java.util.Objects;
import java.util.Random;

public final class RandomDirectionStrategy implements DirectionStrategy {
    private final Random random;

    public RandomDirectionStrategy() {
        this(new Random());
    }

    public RandomDirectionStrategy(Random random) {
        this.random = Objects.requireNonNull(random);
    }

    @Override
    public Direction chooseDirection() {
        return Direction.values()[random.nextInt(Direction.values().length)];
    }
}
