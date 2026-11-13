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

/** LibGDX output adapter and observer of the world's entity lifecycle. */
public class GameRenderer implements WorldObserver {

    private final MapRenderer levelRenderer;
    private final Batch batch;
    private final EntityRenderer<Player> playerRenderer;
    private final EntityRenderer<Obstacle> obstacleRenderer;
    private final EntityRenderer<Enemy> enemyRenderer;
    private EntityRenderer<Bullet> bulletRenderer;
    private final List<Entity> entities = new ArrayList<>();

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
        if (!entities.contains(entity)) entities.add(entity);
    }
    @Override public void entityRemoved(Entity entity) {
        entities.remove(entity);
    }

    public void render() {
        levelRenderer.render();
        batch.begin();
        for (Entity entity : List.copyOf(entities)) {
            if (entity instanceof Player player && player.isAlive()) playerRenderer.render(batch, player);
            else if (entity instanceof Obstacle obstacle) obstacleRenderer.render(batch, obstacle);
            else if (entity instanceof Enemy enemy && enemy.isAlive() && enemyRenderer != null)
                enemyRenderer.render(batch, enemy);
            else if (entity instanceof Bullet bullet && bullet.isAlive() && bulletRenderer != null)
                bulletRenderer.render(batch, bullet);
        }

        batch.end();
    }

    public void dispose() {
        playerRenderer.dispose();
        obstacleRenderer.dispose();
        if (enemyRenderer != null) enemyRenderer.dispose();
        if (bulletRenderer != null) bulletRenderer.dispose();
    }

}
