package ru.mipt.bit.platformer.domain.entities.states;

/** Normal tank state: full movement and firing capability. */
public final class LightTankState implements TankState {
    @Override public float movementSpeedMultiplier() { return 1f; }
    @Override public boolean canShoot() { return true; }
    @Override public float fireCooldownMultiplier() { return 1f; }
}
