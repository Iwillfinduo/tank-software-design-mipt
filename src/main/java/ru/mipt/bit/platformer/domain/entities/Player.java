package ru.mipt.bit.platformer.domain.entities;


import com.badlogic.gdx.math.GridPoint2;

public class Player implements Entity {

    private final GridPoint2 position;
    private final GridPoint2 destination;

    private float movementProgress;
    private float rotation;

    public Player(int x, int y) {
        position = new GridPoint2(x, y);
        destination = new GridPoint2(x, y);
        movementProgress = 1f;
    }

    @Override
    public GridPoint2 getPosition() {
        return position;
    }

    public GridPoint2 getDestination() {
        return destination;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isMoving() {
        return movementProgress < 1f;
    }

    public void setMovementProgress(float progress) {
        movementProgress = progress;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void reachDestination() {
        position.set(destination);
    }
}