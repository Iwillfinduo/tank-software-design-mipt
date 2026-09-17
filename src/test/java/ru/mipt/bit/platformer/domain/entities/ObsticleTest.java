package ru.mipt.bit.platformer.domain.entities;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObsticleTest {

    @Test
    public void obstacleHasCorrectPosition() {
        GridPoint2 position = new GridPoint2(4, 5);

        Obstacle obstacle = new Obstacle(4,5);

        assertEquals(position, obstacle.getPosition());
    }
}
