package ru.mipt.bit.platformer.utils;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.Direction;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DirectionTest {
    @Test
    public void upMovesPositionUp() {
        GridPoint2 position = new GridPoint2(3, 4);

        GridPoint2 result = Direction.UP.move(position);

        assertEquals(new GridPoint2(3, 5), result);
    }

    @Test
    public void downMovesPositionDown() {
        GridPoint2 position = new GridPoint2(3, 4);

        GridPoint2 result = Direction.DOWN.move(position);

        assertEquals(new GridPoint2(3, 3), result);
    }

    @Test
    public void leftMovesPositionLeft() {
        GridPoint2 position = new GridPoint2(3, 4);

        GridPoint2 result = Direction.LEFT.move(position);

        assertEquals(new GridPoint2(2, 4), result);
    }

    @Test
    public void rightMovesPositionRight() {
        GridPoint2 position = new GridPoint2(3, 4);

        GridPoint2 result = Direction.RIGHT.move(position);

        assertEquals(new GridPoint2(4, 4), result);
    }

    @Test
    public void directionsHaveRotation() {
        assertEquals(90f, Direction.UP.getRotation(), 0);
        assertEquals(-180f, Direction.LEFT.getRotation(), 0);
        assertEquals(-90f, Direction.DOWN.getRotation(), 0);
        assertEquals(0f, Direction.RIGHT.getRotation(), 0);
    }
}
