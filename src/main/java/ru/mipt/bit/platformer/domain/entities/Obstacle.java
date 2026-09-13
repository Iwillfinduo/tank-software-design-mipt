package ru.mipt.bit.platformer.domain.entities;

import com.badlogic.gdx.math.GridPoint2;

public class Obstacle implements Entity {

    private final GridPoint2 position;

    public Obstacle(int x, int y) {
        position = new GridPoint2(x, y);
    }

    @Override
    public GridPoint2 getPosition() {
        return position;
    }
}