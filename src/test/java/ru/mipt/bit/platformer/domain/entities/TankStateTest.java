package ru.mipt.bit.platformer.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TankStateTest {
    @Test
    void healthThresholdsSelectLightMediumAndHeavyStates() {
        Player tank = new Player(0, 0);

        assertEquals(1f, tank.getMovementSpeedMultiplier());
        assertTrue(tank.canShoot());

        tank.takeDamage(30);
        assertEquals(0.5f, tank.getMovementSpeedMultiplier());
        assertTrue(tank.canShoot());

        tank.takeDamage(56);
        assertEquals(1f / 3f, tank.getMovementSpeedMultiplier());
        assertFalse(tank.canShoot());
    }

    @Test
    void boundaryHealthBelongsToTheSpecifiedStates() {
        Player tank = new Player(0, 0);

        tank.takeDamage(30);
        assertEquals(70, tank.getHealth());
        assertEquals(0.5f, tank.getMovementSpeedMultiplier());

        tank.takeDamage(55);
        assertEquals(15, tank.getHealth());
        assertEquals(0.5f, tank.getMovementSpeedMultiplier());

        tank.takeDamage(1);
        assertEquals(14, tank.getHealth());
        assertEquals(1f / 3f, tank.getMovementSpeedMultiplier());
    }

    @Test
    void damagedTanksActuallyMoveSlower() {
        Player light = new Player(0, 0);
        Player medium = new Player(0, 0);
        Player heavy = new Player(0, 0);
        light.getDestination().set(1, 0);
        medium.getDestination().set(1, 0);
        heavy.getDestination().set(1, 0);
        light.setMovementProgress(0f);
        medium.setMovementProgress(0f);
        heavy.setMovementProgress(0f);
        medium.takeDamage(30);
        heavy.takeDamage(86);

        light.updateMovement(0.5f, 1f);
        medium.updateMovement(0.5f, 1f);
        heavy.updateMovement(0.5f, 1f);

        assertEquals(0.5f, light.getMovementProgress());
        assertEquals(0.25f, medium.getMovementProgress());
        assertEquals(1f / 6f, heavy.getMovementProgress(), 0.0001f);
    }
}
