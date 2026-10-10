package ru.mipt.bit.platformer.domain.controllers.handlers;

import ru.mipt.bit.platformer.domain.controllers.handlers.bindings.StandardMovementKeyBindings;
import ru.mipt.bit.platformer.util.Direction;

import java.util.Set;

public class MovementInputHandler implements InputHandler<Direction> {

    private final KeyInput keyInput;
    private final StandardMovementKeyBindings bindings;

    public MovementInputHandler(
            KeyInput keyInput,
            StandardMovementKeyBindings bindings
    ) {
        this.keyInput = keyInput;
        this.bindings = bindings;
    }
    @Override
    public Direction handle() {
        if (anyPressed(bindings.upKeys())) {
            return Direction.UP;
        }

        if (anyPressed(bindings.leftKeys())) {
            return Direction.LEFT;
        }

        if (anyPressed(bindings.downKeys())) {
            return Direction.DOWN;
        }

        if (anyPressed(bindings.rightKeys())) {
            return Direction.RIGHT;
        }

        return null;
    }

    private boolean anyPressed(Set<Integer> keys) {
        return keys.stream().anyMatch(keyInput::isPressed);
    }
}