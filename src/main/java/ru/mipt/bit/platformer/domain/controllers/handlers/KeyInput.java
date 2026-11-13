package ru.mipt.bit.platformer.domain.controllers.handlers;

/** Input port isolating application controllers from a concrete keyboard API. */
public interface KeyInput {
    boolean isPressed(int key);
}
