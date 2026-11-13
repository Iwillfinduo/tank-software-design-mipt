package ru.mipt.bit.platformer.domain.world;

import ru.mipt.bit.platformer.domain.entities.Entity;

/** Output port publishing entity lifecycle changes from the domain world. */
public interface WorldObserver {
    void entityAdded(Entity entity);
    void entityRemoved(Entity entity);
}
