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
        java.util.List<Command> commands = new java.util.ArrayList<>();
        collectCommands(deltaTime, commands);
        commands.forEach(Command::execute);
    }
    @Override public void collectCommands(float deltaTime, java.util.Collection<Command> commands) {
        if (enemy.isAlive() && random.nextFloat() < chancePerSecond * deltaTime)
            commands.add(new ShootCommand(world, enemy));
    }
}
