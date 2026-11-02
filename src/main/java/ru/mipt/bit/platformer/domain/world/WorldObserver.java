package ru.mipt.bit.platformer.domain.world;

import ru.mipt.bit.platformer.domain.entities.Entity;

/** Receives entity lifecycle events emitted by a game world. */
public interface WorldObserver {
    void entityAdded(Entity entity);
    void entityRemoved(Entity entity);
}
