package ru.mipt.bit.platformer.domain.entities.states;

/** Damaged tank state: movement is twice as slow, firing remains available. */
public final class MediumTankState implements TankState {
    @Override public float movementSpeedMultiplier() { return 0.5f; }
    @Override public boolean canShoot() { return true; }
    @Override public float fireCooldownMultiplier() { return 1f; }
}
