package ru.mipt.bit.platformer.domain.controllers;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.domain.controllers.handlers.InputHandler;
import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.util.Direction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerControllerTest {
    @Test
    public void startsMovementInRequestedDirection() {
        Player player = new Player(2, 2);

        InputHandler<Direction> inputHandler =
                () -> Direction.RIGHT;

        PlayerController controller =
                new PlayerController(
                        player,
                        new ArrayList<>(),
                        inputHandler
                );

        controller.update(0f);

        assertTrue(player.isMoving());

        assertEquals(
                new GridPoint2(3, 2),
                player.getDestination()
        );

        assertEquals(
                Direction.RIGHT.getRotation(),
                player.getRotation(),
                0
        );
    }

    @Test
    public void doesNotMoveWithoutInput() {
        Player player = new Player(2, 2);

        InputHandler<Direction> inputHandler =
                () -> null;

        PlayerController controller =
                new PlayerController(
                        player,
                        new ArrayList<>(),
                        inputHandler
                );

        controller.update(0f);

        assertFalse(player.isMoving());

        assertEquals(
                new GridPoint2(2, 2),
                player.getPosition()
        );
    }

    @Test
    public void doesNotMoveIntoObstacle() {
        Player player = new Player(2, 2);

        Obstacle obstacle =
                new Obstacle(3, 2);

        InputHandler<Direction> inputHandler =
                () -> Direction.RIGHT;

        PlayerController controller =
                new PlayerController(
                        player,
                        List.of(obstacle),
                        inputHandler
                );

        controller.update(0f);

        assertFalse(player.isMoving());

        assertEquals(
                new GridPoint2(2, 2),
                player.getPosition()
        );
    }

    @Test
    public void movementReachesDestination() {
        Player player = new Player(2, 2);

        InputHandler<Direction> inputHandler =
                () -> Direction.RIGHT;

        PlayerController controller =
                new PlayerController(
                        player,
                        new ArrayList<>(),
                        inputHandler
                );

        controller.update(0f);

        assertTrue(player.isMoving());

        controller.update(10f);

        assertFalse(player.isMoving());

        assertEquals(
                new GridPoint2(3, 2),
                player.getPosition()
        );
    }

    @Test
    public void doesNotChangeDirectionWhileMoving() {
        Player player = new Player(2, 2);

        InputHandler<Direction> inputHandler =
                () -> Direction.RIGHT;

        PlayerController controller =
                new PlayerController(
                        player,
                        new ArrayList<>(),
                        inputHandler
                );

        controller.update(0f);

        assertEquals(
                new GridPoint2(3, 2),
                player.getDestination()
        );

        controller.update(0.1f);

        assertEquals(
                new GridPoint2(3, 2),
                player.getDestination()
        );
    }
}
