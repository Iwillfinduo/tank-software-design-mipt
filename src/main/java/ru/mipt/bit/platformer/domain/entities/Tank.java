package ru.mipt.bit.platformer.domain.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public abstract class Tank implements Entity {
    private static final int MAX_HEALTH = 100;
    private final GridPoint2 position;
    private final GridPoint2 destination;
    private float movementProgress = 1f;
    private float rotation;
    private int health = MAX_HEALTH;

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
    public boolean updateMovement(float deltaTime, float movementSpeed) {
        if (deltaTime < 0f || movementSpeed <= 0f) {
            throw new IllegalArgumentException("Delta time must not be negative and speed must be positive");
        }
        if (!isMoving()) return false;

        movementProgress = GdxGameUtils.continueProgress(
                movementProgress,
                deltaTime,
                movementSpeed
        );
        return movementProgress == 1f;
    }
    public int getHealth() { return health; }
    public int getMaxHealth() { return MAX_HEALTH; }
    public boolean isAlive() { return health > 0; }
    public void takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("Damage must not be negative");
        health = Math.max(0, health - damage);
    }
}
