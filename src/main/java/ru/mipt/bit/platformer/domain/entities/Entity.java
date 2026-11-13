package ru.mipt.bit.platformer.domain.entities;

import com.badlogic.gdx.math.GridPoint2;

/** Domain entity port consumed by world observers and render adapters. */
public interface Entity {
    GridPoint2 getPosition();
}
