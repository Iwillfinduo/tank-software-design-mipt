package ru.mipt.bit.platformer.domain.render;

/** Shared application state read by all health-bar rendering decorators. */
public final class HealthBarVisibilityState implements HealthBarVisibility {
    private boolean visible;

    @Override
    public void toggleHealthBars() {
        visible = !visible;
    }

    @Override
    public boolean areHealthBarsVisible() {
        return visible;
    }
}
