package ru.mipt.bit.platformer.domain.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.domain.world.GameWorld;

public class GameRenderer {

    private final MapRenderer levelRenderer;
    private final Batch batch;
    private final PlayerRenderer playerRenderer;
    private final ObstacleRenderer obstacleRenderer;
    private final EnemyRenderer enemyRenderer;

    public GameRenderer(
            MapRenderer levelRenderer,
            Batch batch,
            PlayerRenderer playerRenderer,
            ObstacleRenderer obstacleRenderer
    ) {
        this(levelRenderer, batch, playerRenderer, obstacleRenderer, null);
    }

    public GameRenderer(MapRenderer levelRenderer, Batch batch, PlayerRenderer playerRenderer,
                        ObstacleRenderer obstacleRenderer, EnemyRenderer enemyRenderer) {
        this.levelRenderer = levelRenderer;
        this.batch = batch;
        this.playerRenderer = playerRenderer;
        this.obstacleRenderer = obstacleRenderer;
        this.enemyRenderer = enemyRenderer;
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


        batch.end();
    }

    public void dispose() {
        playerRenderer.dispose();
        obstacleRenderer.dispose();
        if (enemyRenderer != null) enemyRenderer.dispose();
    }
}
