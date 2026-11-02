package ru.mipt.bit.platformer.domain.controllers;

import ru.mipt.bit.platformer.domain.entities.Tank;
import ru.mipt.bit.platformer.domain.world.GameWorld;
import ru.mipt.bit.platformer.util.Direction;

/** Attempts to fire one bullet from a tank. */
public final class ShootCommand implements Command {
    private final GameWorld world;
    private final Tank tank;
    private final Direction direction;
    public ShootCommand(GameWorld world, Tank tank) {
        this.world = world;
        this.tank = tank;
        this.direction = null;
    }
    public ShootCommand(GameWorld world, Tank tank, Direction direction) {
        this.world = world;
        this.tank = tank;
        this.direction = direction;
    }
    @Override public void execute() {
        if (direction == null) world.shoot(tank);
        else world.shoot(tank, direction);
    }
}
