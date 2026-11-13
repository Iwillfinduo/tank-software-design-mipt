package ru.mipt.bit.platformer.domain.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.domain.entities.states.HeavyTankState;
import ru.mipt.bit.platformer.domain.entities.states.LightTankState;
import ru.mipt.bit.platformer.domain.entities.states.MediumTankState;
import ru.mipt.bit.platformer.domain.entities.states.TankState;
import static com.badlogic.gdx.math.MathUtils.isEqual;

public abstract class Tank implements Entity {
    private static final int MAX_HEALTH = 100;
    private final GridPoint2 position;
    private final GridPoint2 destination;
    private float movementProgress = 1f;
    private float rotation;
    private int health = MAX_HEALTH;
    private TankState state = new LightTankState();

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
                movementSpeed / state.movementSpeedMultiplier()
        );
        return movementProgress == 1f;
    }
    public int getHealth() { return health; }
    public int getMaxHealth() { return MAX_HEALTH; }
    /** Returns the current State-pattern object controlling tank behaviour. */
    public TankState getState() { return state; }
    public float getMovementSpeedMultiplier() { return state.movementSpeedMultiplier(); }
    public boolean canShoot() { return state.canShoot() && isAlive(); }
    public float getFireCooldownMultiplier() { return state.fireCooldownMultiplier(); }
    public boolean isAlive() { return health > 0; }
    public void takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("Damage must not be negative");
        health = Math.max(0, health - damage);
        updateState();
    }

    /** Creates a projectile; the world is responsible for registering it. */
    public Bullet shoot(Direction direction) {
        return canShoot() && direction != null ? new Bullet(this, direction) : null;
    }

    public Bullet shoot() {
        Direction direction = Direction.RIGHT;
        if (isEqual(rotation, Direction.UP.getRotation())) direction = Direction.UP;
        else if (isEqual(rotation, Direction.LEFT.getRotation())) direction = Direction.LEFT;
        else if (isEqual(rotation, Direction.DOWN.getRotation())) direction = Direction.DOWN;
        return shoot(direction);
    }

    private void updateState() {
        float healthRatio = (float) health / MAX_HEALTH;
        if (healthRatio > 0.70f) {
            state = new LightTankState();
        } else if (healthRatio >= 0.15f) {
            state = new MediumTankState();
        } else {
            state = new HeavyTankState();
        }
    }
}
