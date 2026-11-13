package ru.mipt.bit.platformer.domain.entities.states;

/**
 * State port for a tank's health-dependent behaviour.
 *
 * <p>Implementations contain policy, while {@code Tank} owns the current state
 * and delegates behaviour to it. This keeps future temporary states (for
 * example, a speed or fire-rate booster) out of controllers and the world.</p>
 */
public interface TankState {
    float movementSpeedMultiplier();

    boolean canShoot();

    float fireCooldownMultiplier();
}
