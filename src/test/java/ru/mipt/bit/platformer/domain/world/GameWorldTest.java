package ru.mipt.bit.platformer.domain.world;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameWorldTest {
    @Test
    public void worldContainsPlayer() {
        GameWorld world = new GameWorld();

        assertNotNull(world.getPlayer());
    }

    @Test
    public void worldContainsObstacles() {
        GameWorld world = new GameWorld();

        assertNotNull(world.getObstacles());
    }

    @Test
    public void worldContainsOneObstacle() {
        GameWorld world = new GameWorld();

        assertEquals(1, world.getObstacles().size());
    }
}
