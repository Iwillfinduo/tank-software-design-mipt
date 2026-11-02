package ru.mipt.bit.platformer.domain.entities;

import com.badlogic.gdx.math.GridPoint2;

public abstract class Tank implements Entity {
    private final GridPoint2 position;
    private final GridPoint2 destination;
    private float movementProgress = 1f;
    private float rotation;
    private int health = 100;

    protected Tank(int x, int y) {
        position = new GridPoint2(x, y);
        destination = new GridPoint2(x, y);
    }

    public GridPoint2 getPosition() { return position; }
    public GridPoint2 getDestination() { return destination; }
    public float getMovementProgress() { return movementProgress; }
    public float getRotation() { return rotation; }
    public boolean isMoving() { return movementProgress < 1f; }
    public void setMovementProgress(float value) { movementProgress = value; }
    public void setRotation(float value) { rotation = value; }
    public void reachDestination() { position.set(destination); }
    public int getHealth() { return health; }
    public boolean isAlive() { return health > 0; }
    public void takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("Damage must not be negative");
        health = Math.max(0, health - damage);
    }
}
