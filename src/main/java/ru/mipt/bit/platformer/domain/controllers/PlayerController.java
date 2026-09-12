package ru.mipt.bit.platformer.domain.controllers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Player;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerController implements Controller {

    private static final float MOVEMENT_SPEED = 0.4f;

    private final Player player;
    private final List<Obstacle> obstacles;
    private final InputController inputController;

    public PlayerController(
            Player player,
            List<Obstacle> obstacles,
            InputController inputController
    ) {
        this.player = player;
        this.obstacles = obstacles;
        this.inputController = inputController;
    }

    @Override
    public void update(float deltaTime) {
        Direction direction = inputController.getDirection();

        if (!player.isMoving() && direction != null) {
            startMovement(direction);
        }

        updateMovement(deltaTime);
    }

    private void startMovement(Direction direction) {
        GridPoint2 destination =
                direction.move(player.getPosition());
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getPosition().equals(destination)) {
                return;
            }
        }


        player.getDestination().set(destination);
        player.setRotation(direction.getRotation());
        player.setMovementProgress(0f);
    }

    private void updateMovement(float deltaTime) {
        float progress = player.getMovementProgress();

        if (isEqual(progress, 1f)) {
            return;
        }

        progress = continueProgress(
                progress,
                deltaTime,
                MOVEMENT_SPEED
        );

        player.setMovementProgress(progress);

        if (isEqual(progress, 1f)) {
            player.reachDestination();
        }
    }
}
