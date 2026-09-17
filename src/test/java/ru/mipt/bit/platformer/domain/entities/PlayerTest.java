package ru.mipt.bit.platformer.domain.entities;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    @Test
    public void playerHasInitialPosition() {
        GridPoint2 position = new GridPoint2(2, 3);

        Player player = new Player(2, 3);

        assertEquals(position, player.getPosition());
    }

    @Test
    public void playerIsNotMovingInitially() {
        Player player = new Player(2, 3);

        assertFalse(player.isMoving());
    }

    @Test
    public void playerIsMovingWhenProgressIsNotComplete() {
        Player player = new Player(2, 3);

        player.setMovementProgress(0.5f);

        assertTrue(player.isMoving());
    }

    @Test
    public void playerStopsMovingWhenDestinationIsReached() {
        Player player = new Player(2, 3);

        player.getDestination().set(3, 3);
        player.setMovementProgress(0.5f);

        player.reachDestination();

        assertEquals(
                new GridPoint2(3, 3),
                player.getPosition()
        );
        assertTrue(player.isMoving());
        assertEquals(
                new GridPoint2(3, 3),
                player.getDestination()
        );
    }
}
