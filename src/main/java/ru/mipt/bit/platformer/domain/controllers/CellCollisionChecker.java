package ru.mipt.bit.platformer.domain.controllers;

import com.badlogic.gdx.math.GridPoint2;

@FunctionalInterface
public interface CellCollisionChecker {

    boolean isBlocked(GridPoint2 cell);
}
