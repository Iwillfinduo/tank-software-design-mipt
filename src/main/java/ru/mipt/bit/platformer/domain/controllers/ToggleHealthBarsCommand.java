package ru.mipt.bit.platformer.domain.controllers;

import ru.mipt.bit.platformer.domain.render.HealthBarVisibility;

public final class ToggleHealthBarsCommand implements Command {

    private final HealthBarVisibility healthBars;

    public ToggleHealthBarsCommand(HealthBarVisibility healthBars) {
        this.healthBars = healthBars;
    }

    @Override
    public void execute() {
        healthBars.toggleHealthBars();
    }
}
