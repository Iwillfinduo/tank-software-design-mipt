package ru.mipt.bit.platformer.domain.controllers.handlers;

import ru.mipt.bit.platformer.domain.controllers.handlers.bindings.StandartMovementKeyBindings;
import ru.mipt.bit.platformer.util.Direction;

import java.util.Set;

public class MovementInputHandler implements InputHandler<Direction> {

    private final KeyInput keyInput;
    private final StandartMovementKeyBindings bindings;

    public MovementInputHandler(
            KeyInput keyInput,
            StandartMovementKeyBindings bindings
    ) {
        this.keyInput = keyInput;
        this.bindings = bindings;
    }
    @Override
    public Direction handle() {
        if (anyPressed(bindings.getUpKeys())) {
            return Direction.UP;
        }

        if (anyPressed(bindings.getLeftKeys())) {
            return Direction.LEFT;
        }

        if (anyPressed(bindings.getDownKeys())) {
            return Direction.DOWN;
        }

        if (anyPressed(bindings.getRightKeys())) {
            return Direction.RIGHT;
        }

        return null;
    }

    private boolean anyPressed(Set<Integer> keys) {
        return keys.stream().anyMatch(keyInput::isPressed);
    }
}