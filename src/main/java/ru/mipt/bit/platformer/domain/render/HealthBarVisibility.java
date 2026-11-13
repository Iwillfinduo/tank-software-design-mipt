package ru.mipt.bit.platformer.domain.render;

/** UI port used by the application command to toggle health-bar rendering. */
public interface HealthBarVisibility {

    void toggleHealthBars();

    boolean areHealthBarsVisible();
}
