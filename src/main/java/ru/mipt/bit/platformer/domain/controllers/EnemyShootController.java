package ru.mipt.bit.platformer.domain.controllers;

import java.util.Random;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.domain.world.GameWorld;

/** Small random firing behavior for enemies. */
public final class EnemyShootController implements Controller {
    private final GameWorld world;
    private final Enemy enemy;
    private final Random random;
    private final float chancePerSecond;
    public EnemyShootController(GameWorld world, Enemy enemy, Random random, float chancePerSecond) {
        if (chancePerSecond < 0) throw new IllegalArgumentException("Chance must not be negative");
        this.world = world; this.enemy = enemy; this.random = random; this.chancePerSecond = chancePerSecond;
    }
    @Override public void update(float deltaTime) {
        if (enemy.isAlive() && random.nextFloat() < chancePerSecond * deltaTime)
            new ShootCommand(world, enemy).execute();
    }
}
