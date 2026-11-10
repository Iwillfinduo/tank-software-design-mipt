package ru.mipt.bit.platformer.domain.world;

import com.badlogic.gdx.math.GridPoint2;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.util.Direction;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void removingDeadPlayerFreesItsCellAndNotifiesObservers() {
        Player player = new Player(1, 1);
        Enemy enemy = new Enemy(2, 1);
        GameWorld world = new GameWorld(player, java.util.List.of(), java.util.List.of(enemy), 4, 3);

        player.takeDamage(player.getMaxHealth());
        world.removeTank(player);

        assertTrue(world.startMovement(enemy, Direction.LEFT));
        assertEquals(new GridPoint2(1, 1), enemy.getDestination());
    }
}
