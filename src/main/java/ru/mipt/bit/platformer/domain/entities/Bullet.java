package ru.mipt.bit.platformer.domain.entities;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.util.Direction;

/** A short-lived projectile owned and simulated by the world. */
public final class Bullet implements Entity {
    private final Tank owner;
    private final Vector2 position;
    private final Direction direction;
    private final float speed;
    private final int damage;
    private boolean alive = true;

    public Bullet(Tank owner, Direction direction) {
        this(owner, direction, 5f, 25);
    }

    public Bullet(Tank owner, Direction direction, float speed, int damage) {
        if (owner == null || direction == null || speed <= 0 || damage < 0)
            throw new IllegalArgumentException("Invalid bullet parameters");
        this.owner = owner;
        this.direction = direction;
        this.speed = speed;
        this.damage = damage;
        float tankX = owner.getPosition().x;
        float tankY = owner.getPosition().y;
        if (owner.isMoving()) {
            tankX += (owner.getDestination().x - tankX) * owner.getMovementProgress();
            tankY += (owner.getDestination().y - tankY) * owner.getMovementProgress();
        }
        GridPoint2 offset = direction.move(new GridPoint2(0, 0));
        this.position = new Vector2(
                tankX + 0.5f + offset.x * 0.5f,
                tankY + 0.5f + offset.y * 0.5f
        );
    }

    public Tank getOwner() { return owner; }
    public Direction getDirection() { return direction; }
    public Vector2 getWorldPosition() { return position; }
    public float getSpeed() { return speed; }
    public int getDamage() { return damage; }
    public boolean isAlive() { return alive; }
    public void remove() { alive = false; }

    public void update(float deltaTime) {
        if (!alive) return;
        position.x += direction.move(new GridPoint2(0, 0)).x * speed * deltaTime;
        position.y += direction.move(new GridPoint2(0, 0)).y * speed * deltaTime;
    }

    @Override
    public GridPoint2 getPosition() {
        return new GridPoint2(
                Math.round(position.x - 0.5f),
                Math.round(position.y - 0.5f)
        );
    }
}
