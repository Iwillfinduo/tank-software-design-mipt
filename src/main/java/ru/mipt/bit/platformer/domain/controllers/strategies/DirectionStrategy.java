package ru.mipt.bit.platformer.domain.controllers.strategies;

import ru.mipt.bit.platformer.util.Direction;

public interface DirectionStrategy {
    Direction chooseDirection();
}
