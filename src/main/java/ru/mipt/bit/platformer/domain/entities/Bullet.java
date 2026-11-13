package ru.mipt.bit.platformer.domain.entities;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.util.Direction;
import java.util.ArrayList;
import java.util.List;

/** A short-lived projectile owned and simulated by the world. */
public final class Bullet implements Entity {
    private final Tank owner;
    private final Vector2 position;
    private final Direction direction;
    private final float speed;
    private final int damage;
    private final GridPoint2 initialCell;
    private boolean initialFrame = true;
    private boolean alive = true;
    private final List<GridPoint2> traversedCells = new ArrayList<>();

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
        GridPoint2 offset = direction.move(new GridPoint2(0, 0));
        this.position = new Vector2(tankX + offset.x, tankY + offset.y);
        this.initialCell = new GridPoint2(Math.round(position.x), Math.round(position.y));
        traversedCells.add(getPosition());
    }

    public Tank getOwner() { return owner; }
    public Direction getDirection() { return direction; }
    public Vector2 getWorldPosition() { return position; }
    public float getSpeed() { return speed; }
    public int getDamage() { return damage; }
    public boolean isAlive() { return alive; }
    /** The first simulation frame is rendered before the muzzle cell can collide. */
    public boolean isInitialFrame() { return initialFrame; }
    public void finishInitialFrame() { initialFrame = false; }
    public GridPoint2 getInitialCell() { return new GridPoint2(initialCell); }
    public void remove() { alive = false; }

    public void update(float deltaTime) {
        if (!alive) return;
        GridPoint2 start = getPosition();
        position.x += direction.move(new GridPoint2(0, 0)).x * speed * deltaTime;
        position.y += direction.move(new GridPoint2(0, 0)).y * speed * deltaTime;
        traversedCells.clear();
        traversedCells.add(start);
        int distance = Math.max(Math.abs(getPosition().x - start.x), Math.abs(getPosition().y - start.y));
        for (int i = 1; i <= distance; i++) {
            float fraction = (float) i / distance;
            traversedCells.add(new GridPoint2(
                    Math.round(start.x + (getPosition().x - start.x) * fraction),
                    Math.round(start.y + (getPosition().y - start.y) * fraction)));
        }
    }

    public List<GridPoint2> getTraversedCells() { return List.copyOf(traversedCells); }

    @Override
    public GridPoint2 getPosition() {
        return new GridPoint2(
                Math.round(position.x - 0.5f),
                Math.round(position.y - 0.5f)
        );
    }
}
