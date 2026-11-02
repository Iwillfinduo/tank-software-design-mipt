package ru.mipt.bit.platformer.domain.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.domain.world.GameWorld;
import ru.mipt.bit.platformer.domain.world.WorldObserver;
import ru.mipt.bit.platformer.domain.entities.Bullet;
import ru.mipt.bit.platformer.domain.entities.Entity;
import java.util.ArrayList;
import java.util.List;

public class GameRenderer implements WorldObserver {

    private final MapRenderer levelRenderer;
    private final Batch batch;
    private final EntityRenderer<Player> playerRenderer;
    private final EntityRenderer<Obstacle> obstacleRenderer;
    private final EntityRenderer<Enemy> enemyRenderer;
    private EntityRenderer<Bullet> bulletRenderer;
    private final List<Bullet> bullets = new ArrayList<>();

    public GameRenderer(
            MapRenderer levelRenderer,
            Batch batch,
            EntityRenderer<Player> playerRenderer,
            EntityRenderer<Obstacle> obstacleRenderer
    ) {
        this(levelRenderer, batch, playerRenderer, obstacleRenderer, null);
    }

    public GameRenderer(
            MapRenderer levelRenderer,
            Batch batch,
            EntityRenderer<Player> playerRenderer,
            EntityRenderer<Obstacle> obstacleRenderer,
            EntityRenderer<Enemy> enemyRenderer
    ) {
        this.levelRenderer = levelRenderer;
        this.batch = batch;
        this.playerRenderer = playerRenderer;
        this.obstacleRenderer = obstacleRenderer;
        this.enemyRenderer = enemyRenderer;
        this.bulletRenderer = null;
    }

    public GameRenderer(MapRenderer levelRenderer, Batch batch,
                        EntityRenderer<Player> playerRenderer,
                        EntityRenderer<Obstacle> obstacleRenderer,
                        EntityRenderer<Enemy> enemyRenderer,
                        EntityRenderer<Bullet> bulletRenderer,
                        GameWorld world) {
        this(levelRenderer, batch, playerRenderer, obstacleRenderer, enemyRenderer);
        this.bulletRenderer = bulletRenderer;
        world.addObserver(this);
    }

    @Override public void entityAdded(Entity entity) {
        if (entity instanceof Bullet) bullets.add((Bullet) entity);
    }
    @Override public void entityRemoved(Entity entity) {
        if (entity instanceof Bullet) bullets.remove(entity);
    }

    public void render(GameWorld world) {
        levelRenderer.render();
        batch.begin();
        playerRenderer.render(batch, world.getPlayer());
        for (Obstacle obstacle : world.getObstacles()) {
            obstacleRenderer.render(batch, obstacle);
        }
        if (enemyRenderer != null) {
            for (Enemy enemy : world.getEnemies()) enemyRenderer.render(batch, enemy);
        }
        if (bulletRenderer != null) for (Bullet bullet : bullets) bulletRenderer.render(batch, bullet);

        batch.end();
    }

    public void dispose() {
        playerRenderer.dispose();
        obstacleRenderer.dispose();
        if (enemyRenderer != null) enemyRenderer.dispose();
        if (bulletRenderer != null) bulletRenderer.dispose();
    }
}
