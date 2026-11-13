package ru.mipt.bit.platformer.domain.entities.states;

/** Critically damaged tank state: movement is three times slower and firing is disabled. */
public final class HeavyTankState implements TankState {
    @Override public float movementSpeedMultiplier() { return 1f / 3f; }
    @Override public boolean canShoot() { return false; }
    @Override public float fireCooldownMultiplier() { return 1f; }
}
