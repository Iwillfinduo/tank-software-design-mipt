package ru.mipt.bit.platformer.domain.controllers.strategies;

import ru.mipt.bit.platformer.util.Direction;

/** Application port for interchangeable enemy decision policies. */
public interface DirectionStrategy {
    Direction chooseDirection();
}
