package ru.mipt.bit.platformer.domain.controllers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.domain.controllers.handlers.InputHandler;
import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.domain.world.GameWorld;
import ru.mipt.bit.platformer.util.Direction;

import java.util.List;
import java.util.Objects;


public class PlayerController implements Controller {

    private final Player player;
    private final CellCollisionChecker collisionChecker;
    private final InputHandler<Direction> inputHandler;
    private final float movementSpeed;
    private final GameWorld world;

    public PlayerController(
            Player player,
            List<? extends Obstacle> obstacles,
            InputHandler<Direction> inputHandler,
            float movementSpeed
    ) {
        this(
                player,
                cell -> obstacles.stream().anyMatch(obstacle -> obstacle.getPosition().equals(cell)),
                inputHandler,
                movementSpeed,
                null
        );
    }

    public PlayerController(
            Player player,
            CellCollisionChecker collisionChecker,
            InputHandler<Direction> inputHandler,
            float movementSpeed
    ) {
        this(player, collisionChecker, inputHandler, movementSpeed, null);
    }

    public PlayerController(Player player, GameWorld world, InputHandler<Direction> inputHandler,
                             float movementSpeed) {
        this(player, world::isBlocked, inputHandler, movementSpeed, world);
    }

    private PlayerController(Player player, CellCollisionChecker collisionChecker,
                             InputHandler<Direction> inputHandler,
                             float movementSpeed, GameWorld world) {
        this.player = Objects.requireNonNull(player, "player");
        this.collisionChecker = Objects.requireNonNull(collisionChecker, "collisionChecker");
        this.inputHandler = Objects.requireNonNull(inputHandler, "inputHandler");
        this.movementSpeed = movementSpeed;
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        Direction direction = inputHandler.handle();

        if (!player.isMoving() && direction != null) {
            startMovement(direction);
        }

        if (world != null) {
            world.updateMovement(player, deltaTime, movementSpeed);
        } else if (player.updateMovement(deltaTime, movementSpeed)) {
            player.reachDestination();
        }
    }

    private void startMovement(Direction direction) {
        if (world != null) {
            new MoveCommand(world, player, direction).execute();
            return;
        }
        GridPoint2 destination = direction.move(player.getPosition());

        if (collisionChecker.isBlocked(destination)) return;

        player.getDestination().set(destination);
        player.setRotation(direction.getRotation());
        player.setMovementProgress(0f);
    }

}
