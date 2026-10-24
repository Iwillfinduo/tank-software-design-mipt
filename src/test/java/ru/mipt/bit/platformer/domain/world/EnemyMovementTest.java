package ru.mipt.bit.platformer.domain.world;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.domain.controllers.EnemyController;
import ru.mipt.bit.platformer.domain.controllers.MoveCommand;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.domain.entities.EnemyTank;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.domain.controllers.strategies.DirectionStrategy;
import ru.mipt.bit.platformer.util.Direction;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class EnemyMovementTest {
    @Test
    void randomLevelPlacesEnemiesOnDistinctFreeCells() {
        GameWorld world = GameWorld.random(8, 6, 10, 4, new java.util.Random(3));
        assertEquals(4, world.getEnemies().size());
        assertTrue(world.getEnemies().stream().allMatch(EnemyTank.class::isInstance));
        assertEquals(15, world.getObstacles().size() + world.getEnemies().size()
                + 1, 15);
    }

    @Test
    void bothCellsAreReservedUntilMovementFinishes() {
        Enemy first = new Enemy(1, 1);
        Enemy second = new Enemy(3, 1);
        GameWorld world = new GameWorld(new Player(0, 0), List.of(), List.of(first, second), 5, 3);
        new MoveCommand(world, first, Direction.RIGHT).execute();
        assertTrue(first.isMoving());
        assertFalse(world.startMovement(second, Direction.LEFT));
        first.setMovementProgress(1f);
        world.finishMovement(first);
        assertTrue(world.startMovement(second, Direction.RIGHT));
    }

    @Test
    void boundaryIsBlocked() {
        Enemy enemy = new Enemy(0, 0);
        GameWorld world = new GameWorld(new Player(2, 2), List.of(), List.of(enemy), 3, 3);
        assertFalse(world.startMovement(enemy, Direction.LEFT));
        assertFalse(world.startMovement(enemy, Direction.DOWN));
    }

    @Test
    void waitsBeforeStartingNextMovement() {
        Enemy enemy = new Enemy(1, 1);
        GameWorld world = new GameWorld(new Player(0, 0), List.of(), List.of(enemy), 4, 4);
        DirectionStrategy strategy = () -> Direction.RIGHT;
        EnemyController controller = new EnemyController(enemy, world, strategy, 1f, 1f);

        controller.update(0f);
        controller.update(1f);
        controller.update(0f);

        assertFalse(enemy.isMoving());
        assertEquals(new com.badlogic.gdx.math.GridPoint2(2, 1), enemy.getPosition());

        controller.update(1f);
        controller.update(0f);

        assertTrue(enemy.isMoving());
        assertEquals(new com.badlogic.gdx.math.GridPoint2(3, 1), enemy.getDestination());
    }
}
