package ru.mipt.bit.platformer.domain.controllers.handlers.bindings;

import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovementKeyBindingsTest {

    private final StandardMovementKeyBindings bindings =
            new StandardMovementKeyBindings(
                    Set.of(Input.Keys.UP, Input.Keys.W),
                    Set.of(Input.Keys.LEFT, Input.Keys.A),
                    Set.of(Input.Keys.DOWN, Input.Keys.S),
                    Set.of(Input.Keys.RIGHT, Input.Keys.D)
            );

    @Test
    void containsAllUpKeys() {
        assertEquals(
                Set.of(Input.Keys.UP, Input.Keys.W),
                bindings.upKeys()
        );
    }

    @Test
    void containsAllLeftKeys() {
        assertEquals(
                Set.of(Input.Keys.LEFT, Input.Keys.A),
                bindings.leftKeys()
        );
    }

    @Test
    void containsAllDownKeys() {
        assertEquals(
                Set.of(Input.Keys.DOWN, Input.Keys.S),
                bindings.downKeys()
        );
    }

    @Test
    void containsAllRightKeys() {
        assertEquals(
                Set.of(Input.Keys.RIGHT, Input.Keys.D),
                bindings.rightKeys()
        );
    }

    @Test
    void supportsMultipleKeysForOneDirection() {
        assertTrue(bindings.upKeys().contains(Input.Keys.UP));
        assertTrue(bindings.upKeys().contains(Input.Keys.W));
    }
}