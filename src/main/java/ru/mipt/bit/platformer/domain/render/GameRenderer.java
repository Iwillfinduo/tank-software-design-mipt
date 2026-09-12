package ru.mipt.bit.platformer.domain.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.world.GameWorld;

public class GameRenderer {

    private final MapRenderer levelRenderer;
    private final Batch batch;
    private final PlayerRenderer playerRenderer;
    private final ObstacleRenderer obstacleRenderer;

    public GameRenderer(
            MapRenderer levelRenderer,
            Batch batch,
            PlayerRenderer playerRenderer,
            ObstacleRenderer obstacleRenderer
    ) {
        this.levelRenderer = levelRenderer;
        this.batch = batch;
        this.playerRenderer = playerRenderer;
        this.obstacleRenderer = obstacleRenderer;
    }

    public void render(GameWorld world) {
        levelRenderer.render();
        batch.begin();
        playerRenderer.render(batch, world.getPlayer());
        for (Obstacle obstacle : world.getObstacles()) {
            obstacleRenderer.render(batch, obstacle);
        }


        batch.end();
    }

    public void dispose() {
        playerRenderer.dispose();
        obstacleRenderer.dispose();
    }
}
