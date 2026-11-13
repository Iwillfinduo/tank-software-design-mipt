package ru.mipt.bit.platformer.domain.world;
/**
 * A functional interface for loading a game world.
 */
@FunctionalInterface
public interface GameWorldLoader {

    GameWorld load();
}
