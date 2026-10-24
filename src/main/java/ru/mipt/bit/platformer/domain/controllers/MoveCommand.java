package ru.mipt.bit.platformer.domain.controllers;

import ru.mipt.bit.platformer.domain.entities.Tank;
import ru.mipt.bit.platformer.domain.world.GameWorld;
import ru.mipt.bit.platformer.util.Direction;

/** Attempts one atomic cell move. The world reserves both cells for its duration. */
public final class MoveCommand implements Command {
    private final GameWorld world;
    private final Tank tank;
    private final Direction direction;

    public MoveCommand(GameWorld world, Tank tank, Direction direction) {
        this.world = world;
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        world.startMovement(tank, direction);
    }
}
