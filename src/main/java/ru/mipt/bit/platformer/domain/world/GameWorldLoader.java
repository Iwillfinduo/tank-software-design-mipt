package ru.mipt.bit.platformer.domain.world;

@FunctionalInterface
public interface GameWorldLoader {

    GameWorld load();
}
