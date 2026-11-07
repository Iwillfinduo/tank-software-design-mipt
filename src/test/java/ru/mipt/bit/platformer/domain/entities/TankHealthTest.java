package ru.mipt.bit.platformer.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TankHealthTest {

    @Test
    void tanksStartWithFullHealthAndCanBeDamaged() {
        Player player = new Player(0, 0);

        assertEquals(100, player.getMaxHealth());
        assertEquals(100, player.getHealth());

        player.takeDamage(40);

        assertEquals(60, player.getHealth());
        assertTrue(player.isAlive());
    }
}
