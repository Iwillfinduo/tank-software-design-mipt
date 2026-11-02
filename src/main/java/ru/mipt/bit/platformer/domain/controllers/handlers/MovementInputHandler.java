package ru.mipt.bit.platformer.domain.controllers.handlers;

import ru.mipt.bit.platformer.domain.controllers.handlers.bindings.StandardMovementKeyBindings;
import ru.mipt.bit.platformer.util.Direction;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MovementInputHandler implements InputHandler<Direction> {

    private final KeyInput keyInput;
    private final Map<Set<Integer>, Direction> directionBindings;

    public MovementInputHandler(
            KeyInput keyInput,
            StandardMovementKeyBindings bindings
    ) {
        this.keyInput = keyInput;
        directionBindings = new LinkedHashMap<>();
        directionBindings.put(bindings.upKeys(), Direction.UP);
        directionBindings.put(bindings.leftKeys(), Direction.LEFT);
        directionBindings.put(bindings.downKeys(), Direction.DOWN);
        directionBindings.put(bindings.rightKeys(), Direction.RIGHT);
    }

    @Override
    public Direction handle() {
        return directionBindings.entrySet().stream()
                .filter(binding -> anyPressed(binding.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    private boolean anyPressed(Set<Integer> keys) {
        return keys.stream().anyMatch(keyInput::isPressed);
    }
}