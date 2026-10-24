package ru.mipt.bit.platformer.domain.controllers;

import ru.mipt.bit.platformer.domain.controllers.strategies.DirectionStrategy;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.domain.world.GameWorld;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public final class EnemyController implements Controller {
    private final Enemy enemy;
    private final GameWorld world;
    private final DirectionStrategy strategy;
    private final float speed;
    private final float moveDelay;
    private float delayRemaining;

    public EnemyController(Enemy enemy, GameWorld world, DirectionStrategy strategy, float speed) {
        this(enemy, world, strategy, speed, 0f, 0f);
    }

    public EnemyController(
            Enemy enemy,
            GameWorld world,
            DirectionStrategy strategy,
            float speed,
            float moveDelay
    ) {
        this(enemy, world, strategy, speed, moveDelay, 0f);
    }

    public EnemyController(
            Enemy enemy,
            GameWorld world,
            DirectionStrategy strategy,
            float speed,
            float moveDelay,
            float initialDelay
    ) {
        if (initialDelay < 0f) {
            throw new IllegalArgumentException("Initial delay must not be negative");
        }
        if (moveDelay < 0f) {
            throw new IllegalArgumentException("Move delay must not be negative");
        }
        this.enemy = enemy;
        this.world = world;
        this.strategy = strategy;
        this.speed = speed;
        this.moveDelay = moveDelay;
        this.delayRemaining = initialDelay;
    }

    @Override public void update(float deltaTime) {
        if (delayRemaining > 0f) {
            delayRemaining = Math.max(0f, delayRemaining - deltaTime);
            return;
        }

        if (!enemy.isMoving()) new MoveCommand(world, enemy, strategy.chooseDirection()).execute();
        if (enemy.isMoving()) {
            float progress = continueProgress(enemy.getMovementProgress(), deltaTime, speed);
            enemy.setMovementProgress(progress);
            if (isEqual(progress, 1f)) {
                world.finishMovement(enemy);
                delayRemaining = moveDelay;
            }
        }
    }
}
