package ru.mipt.bit.platformer.domain.controllers;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.domain.controllers.handlers.InputHandler;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.util.Direction;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CellCollisionCheckerTest {

    @Test
    void playerControllerUsesCollisionCheckerContract() {
        Player player = new Player(2, 2);
        InputHandler<Direction> inputHandler = () -> Direction.RIGHT;
        CellCollisionChecker checker = cell -> cell.equals(new GridPoint2(3, 2));

        PlayerController controller =
                new PlayerController(player, checker, inputHandler, 0.4f);

        controller.update(0f);

        assertFalse(player.isMoving());
    }
}
