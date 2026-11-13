package ru.mipt.bit.platformer.domain.controllers;

import com.badlogic.gdx.math.GridPoint2;

/**
 * A functional interface for checking if a cell is blocked.
 */
@FunctionalInterface
public interface CellCollisionChecker {

    boolean isBlocked(GridPoint2 cell);
}
