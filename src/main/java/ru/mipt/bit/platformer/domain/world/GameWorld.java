package ru.mipt.bit.platformer.domain.world;

import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.domain.entities.Tank;
import ru.mipt.bit.platformer.domain.entities.Bullet;
import ru.mipt.bit.platformer.domain.entities.Entity;
import ru.mipt.bit.platformer.domain.level.LevelFileLoader;
import ru.mipt.bit.platformer.domain.level.LevelGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class GameWorld {
    private final Player player;
    private final ArrayList<Obstacle> obstacles;
    private final ArrayList<Enemy> enemies;
    private final int width;
    private final int height;
    private final Set<GridPoint2> reserved = new HashSet<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final List<WorldObserver> observers = new CopyOnWriteArrayList<>();

    public GameWorld() {
        this(new Player(1, 1), List.of(new Obstacle(1, 3)), List.of(), -1, -1);
    }

    public GameWorld(Player player, List<Obstacle> obstacles) {
        this(player, obstacles, List.of(), -1, -1);
    }

    public GameWorld(Player player, List<Obstacle> obstacles, List<Enemy> enemies,
                     int width, int height) {
        if (player == null || obstacles == null) {
            throw new IllegalArgumentException("Player and obstacles must not be null");
        }
        this.player = player;
        this.obstacles = new ArrayList<>(obstacles);
        this.enemies = new ArrayList<>(enemies);
        this.width = width;
        this.height = height;
    }

    public GameWorld(int width, int height, int obstacleCount, Random random) {
        this(LevelGenerator.generate(width, height, obstacleCount, random));
    }

    private GameWorld(GameWorld generatedWorld) {
        this(generatedWorld.player, generatedWorld.obstacles, generatedWorld.enemies,
                generatedWorld.width, generatedWorld.height);
    }


    public static GameWorld random(
            int width,
            int height,
            int obstacleCount,
            Random random
    ) {
        return new GameWorld(width, height, obstacleCount, random);
    }

    public static GameWorld random(int width, int height, int obstacleCount, int enemyCount, Random random) {
        return new GameWorld(LevelGenerator.generate(width, height, obstacleCount, enemyCount, random));
    }



    public static GameWorld fromText(String text) {
        return LevelFileLoader.loadText(text);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Enemy> getEnemies() { return enemies; }
    public List<Bullet> getBullets() { return bullets; }
    public List<Bullet> getProjectiles() { return bullets; }
    public void addObserver(WorldObserver observer) {
        if (observer == null) throw new IllegalArgumentException("Observer must not be null");
        observers.add(observer);
        observer.entityAdded(player);
        obstacles.forEach(observer::entityAdded);
        enemies.forEach(observer::entityAdded);
        bullets.forEach(observer::entityAdded);
    }
    public void removeObserver(WorldObserver observer) { observers.remove(observer); }
    public void registerObserver(WorldObserver observer) { addObserver(observer); }
    public void unregisterObserver(WorldObserver observer) { removeObserver(observer); }
    private void added(Entity entity) { observers.forEach(o -> o.entityAdded(entity)); }
    private void removed(Entity entity) { observers.forEach(o -> o.entityRemoved(entity)); }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Bullet shoot(Tank shooter) {
        if (shooter == null || !shooter.isAlive()) return null;
        return shoot(shooter, rotationDirection(shooter.getRotation()));
    }

    public Bullet shoot(Tank shooter, Direction direction) {
        if (shooter == null || direction == null || !shooter.isAlive()) return null;
        Bullet bullet = new Bullet(shooter, direction);
        bullets.add(bullet);
        added(bullet);
        return bullet;
    }

    public void updateBullets(float deltaTime) {
        if (deltaTime < 0) throw new IllegalArgumentException("Delta time must not be negative");
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            handleBulletCollision(bullet);
            bullet.update(deltaTime);
            handleBulletCollision(bullet);
            if (!bullet.isAlive()) {
                iterator.remove();
                removed(bullet);
            }
        }
    }

    public void liveTick(float deltaTime) { updateBullets(deltaTime); }
    public void updateProjectiles(float deltaTime) { updateBullets(deltaTime); }

    private boolean isObstacle(GridPoint2 cell) {
        return obstacles.stream().anyMatch(o -> o.getPosition().equals(cell));
    }

    private void handleBulletCollision(Bullet bullet) {
        if (!bullet.isAlive()) return;
        GridPoint2 cell = bullet.getPosition();
        if (!isInside(cell) || isObstacle(cell)) {
            bullet.remove();
            return;
        }

        Bullet otherBullet = bulletAt(cell, bullet);
        if (otherBullet != null) {
            bullet.remove();
            otherBullet.remove();
            return;
        }

        Tank target = targetAt(cell, bullet.getOwner());
        if (target != null) {
            target.takeDamage(bullet.getDamage());
            bullet.remove();
            removeDefeatedEnemy(target);
        }
    }

    private Bullet bulletAt(GridPoint2 cell, Bullet excluded) {
        for (Bullet other : bullets) {
            if (other != excluded && other.isAlive() && other.getPosition().equals(cell)) {
                return other;
            }
        }
        return null;
    }

    private void removeDefeatedEnemy(Tank target) {
        if (target instanceof Enemy enemy && !enemy.isAlive() && enemies.remove(enemy)) {
            removed(enemy);
        }
    }

    private Tank targetAt(GridPoint2 cell, Tank owner) {
        if (player != owner && player.isAlive() && player.getPosition().equals(cell)) return player;
        for (Enemy enemy : enemies)
            if (enemy != owner && enemy.isAlive() && enemy.getPosition().equals(cell)) return enemy;
        return null;
    }
    private Direction rotationDirection(float rotation) {
        if (isEqual(rotation, Direction.UP.getRotation())) return Direction.UP;
        if (isEqual(rotation, Direction.LEFT.getRotation())) return Direction.LEFT;
        if (isEqual(rotation, Direction.DOWN.getRotation())) return Direction.DOWN;
        return Direction.RIGHT;
    }

    public boolean isInside(GridPoint2 cell) {
        return width < 0 || (cell.x >= 0 && cell.y >= 0 && cell.x < width && cell.y < height);
    }

    public boolean startMovement(Tank tank, Direction direction) {
        if (tank == null || direction == null || tank.isMoving()) return false;
        GridPoint2 destination = direction.move(tank.getPosition());
        if (!isInside(destination) || reserved.contains(tank.getPosition())
                || reserved.contains(destination) || isBlocked(destination, tank)) return false;
        reserved.add(new GridPoint2(tank.getPosition()));
        reserved.add(new GridPoint2(destination));
        tank.getDestination().set(destination);
        tank.setRotation(direction.getRotation());
        tank.setMovementProgress(0f);
        return true;
    }

    public boolean isBlocked(GridPoint2 cell) {
        return isBlocked(cell, null);
    }

    private boolean isBlocked(GridPoint2 cell, Tank moving) {
        for (Obstacle obstacle : obstacles) if (obstacle.getPosition().equals(cell)) return true;
        if (player != moving && (player.getPosition().equals(cell) || player.getDestination().equals(cell))) return true;
        for (Enemy enemy : enemies) {
            if (enemy != moving && (enemy.getPosition().equals(cell) || enemy.getDestination().equals(cell))) return true;
        }
        return false;
    }

    public void finishMovement(Tank tank) {
        reserved.remove(tank.getPosition());
        reserved.remove(tank.getDestination());
        tank.reachDestination();
    }
}
