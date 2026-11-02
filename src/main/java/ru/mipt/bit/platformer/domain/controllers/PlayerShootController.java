package ru.mipt.bit.platformer.domain.controllers;

import ru.mipt.bit.platformer.domain.controllers.handlers.KeyInput;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.domain.world.GameWorld;

/** Converts the fire key into one command per press (not one per rendered frame). */
public final class PlayerShootController implements Controller {
    public static final float DEFAULT_COOLDOWN = 0.5f;

    private final GameWorld world;
    private final Player player;
    private final KeyInput input;
    private final int key;
    private final float cooldown;
    private float cooldownRemaining;
    private boolean wasPressed;

    public PlayerShootController(GameWorld world, Player player, KeyInput input, int key) {
        this(world, player, input, key, DEFAULT_COOLDOWN);
    }

    public PlayerShootController(
            GameWorld world,
            Player player,
            KeyInput input,
            int key,
            float cooldown
    ) {
        if (cooldown < 0f) {
            throw new IllegalArgumentException("Cooldown must not be negative");
        }
        this.world = world;
        this.player = player;
        this.input = input;
        this.key = key;
        this.cooldown = cooldown;
    }

    @Override public void update(float deltaTime) {
        if (deltaTime < 0f) {
            throw new IllegalArgumentException("Delta time must not be negative");
        }
        cooldownRemaining = Math.max(0f, cooldownRemaining - deltaTime);
        boolean pressed = input.isPressed(key);
        if (pressed && !wasPressed && cooldownRemaining == 0f) {
            new ShootCommand(world, player).execute();
            cooldownRemaining = cooldown;
        }
        wasPressed = pressed;
    }
}
