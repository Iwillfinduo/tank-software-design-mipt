package ru.mipt.bit.platformer.domain.controllers;

import ru.mipt.bit.platformer.domain.controllers.handlers.KeyInput;
import ru.mipt.bit.platformer.domain.render.HealthBarVisibility;

public final class HealthBarsToggleController implements Controller {

    private final KeyInput input;
    private final int key;
    private final Command toggleCommand;
    private boolean wasPressed;

    public HealthBarsToggleController(KeyInput input, int key, HealthBarVisibility healthBars) {
        this.input = input;
        this.key = key;
        this.toggleCommand = new ToggleHealthBarsCommand(healthBars);
    }

    @Override
    public void update(float deltaTime) {
        boolean pressed = input.isPressed(key);
        if (pressed && !wasPressed) {
            toggleCommand.execute();
        }
        wasPressed = pressed;
    }
}
