package ru.mipt.bit.platformer.domain.controllers.handlers.bindings;

import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovementKeyBindingsTest {

    private final StandartMovementKeyBindings bindings =
            new StandartMovementKeyBindings(
                    Set.of(Input.Keys.UP, Input.Keys.W),
                    Set.of(Input.Keys.LEFT, Input.Keys.A),
                    Set.of(Input.Keys.DOWN, Input.Keys.S),
                    Set.of(Input.Keys.RIGHT, Input.Keys.D)
            );

    @Test
    void containsAllUpKeys() {
        assertEquals(
                Set.of(Input.Keys.UP, Input.Keys.W),
                bindings.getUpKeys()
        );
    }

    @Test
    void containsAllLeftKeys() {
        assertEquals(
                Set.of(Input.Keys.LEFT, Input.Keys.A),
                bindings.getLeftKeys()
        );
    }

    @Test
    void containsAllDownKeys() {
        assertEquals(
                Set.of(Input.Keys.DOWN, Input.Keys.S),
                bindings.getDownKeys()
        );
    }

    @Test
    void containsAllRightKeys() {
        assertEquals(
                Set.of(Input.Keys.RIGHT, Input.Keys.D),
                bindings.getRightKeys()
        );
    }

    @Test
    void supportsMultipleKeysForOneDirection() {
        assertTrue(bindings.getUpKeys().contains(Input.Keys.UP));
        assertTrue(bindings.getUpKeys().contains(Input.Keys.W));
    }
}