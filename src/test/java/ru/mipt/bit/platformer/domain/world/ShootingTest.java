package ru.mipt.bit.platformer.domain.world;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.domain.controllers.PlayerShootController;
import ru.mipt.bit.platformer.domain.entities.*;
import ru.mipt.bit.platformer.util.Direction;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ShootingTest {
    @Test
    void playerCannotShootAgainBeforeCooldownExpires() {
        Player player = new Player(1, 1);
        GameWorld world = new GameWorld(player, List.of());
        ru.mipt.bit.platformer.domain.controllers.handlers.KeyInput input =
                Mockito.mock(ru.mipt.bit.platformer.domain.controllers.handlers.KeyInput.class);
        Mockito.when(input.isPressed(Input.Keys.SPACE)).thenReturn(true, false, true, false, true);
        PlayerShootController controller =
                new PlayerShootController(world, player, input, Input.Keys.SPACE, 1f);

        controller.update(0f);
        controller.update(0.1f);
        controller.update(0.1f);

        assertEquals(1, world.getBullets().size());

        controller.update(0.8f);
        controller.update(0f);

        assertEquals(2, world.getBullets().size());
    }
    @Test
    void bulletStartsInFrontOfShooter() {
        Player player = new Player(2, 2);
        GameWorld world = new GameWorld(player, List.of());

        Bullet bullet = world.shoot(player, Direction.RIGHT);

        assertEquals(new com.badlogic.gdx.math.GridPoint2(3, 2), bullet.getPosition());
    }

    @Test
    void bulletStartsExactlyOneCellAheadInEveryDirection() {
        for (Direction direction : Direction.values()) {
            Player player = new Player(2, 2);
            Bullet bullet = new GameWorld(player, List.of()).shoot(player, direction);
            assertEquals(direction.move(new com.badlogic.gdx.math.GridPoint2(2, 2)), bullet.getPosition());
        }
    }

    @Test
    void shootingWhileMovingUsesTankCellCoordinate() {
        Player player = new Player(2, 2);
        GameWorld world = new GameWorld(player, List.of());
        world.startMovement(player, Direction.RIGHT);
        player.setMovementProgress(.5f);
        assertEquals(new com.badlogic.gdx.math.GridPoint2(3, 2),
                world.shoot(player, Direction.RIGHT).getPosition());
    }

    @Test
    void bulletMovesAndDamagesEnemy() {
        Player player = new Player(1, 1);
        Enemy enemy = new Enemy(2, 1);
        GameWorld world = new GameWorld(player, List.of(), List.of(enemy), 5, 5);

        assertNotNull(world.shoot(player, Direction.RIGHT));
        world.updateBullets(.2f);

        assertEquals(100, enemy.getHealth());
        assertEquals(1, world.getBullets().size());

        world.updateBullets(0f);

        assertEquals(75, enemy.getHealth());
        assertTrue(world.getBullets().isEmpty());
    }

    @Test
    void bulletDamagesTankInIntermediateCell() {
        Player player = new Player(1, 1);
        Enemy enemy = new Enemy(3, 1);
        GameWorld world = new GameWorld(player, List.of(), List.of(enemy), 10, 3);

        world.shoot(player, Direction.RIGHT);
        world.updateBullets(1f);

        assertEquals(75, enemy.getHealth());
        assertTrue(world.getBullets().isEmpty());
    }

    @Test
    void observerSeesBulletLifecycle() {
        GameWorld world = new GameWorld(new Player(1, 1), List.of(), List.of(), 3, 3);
        AtomicInteger added = new AtomicInteger();
        AtomicInteger removed = new AtomicInteger();
        world.addObserver(new WorldObserver() {
            public void entityAdded(Entity entity) { if (entity instanceof Bullet) added.incrementAndGet(); }
            public void entityRemoved(Entity entity) { if (entity instanceof Bullet) removed.incrementAndGet(); }
        });

        world.shoot(world.getPlayer(), Direction.RIGHT);
        world.updateBullets(1f);

        assertEquals(1, added.get());
        assertEquals(1, removed.get());
    }
}
