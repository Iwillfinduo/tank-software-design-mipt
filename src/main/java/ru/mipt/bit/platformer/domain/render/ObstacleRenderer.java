package ru.mipt.bit.platformer.domain.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.domain.entities.Obstacle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class ObstacleRenderer implements EntityRenderer<Obstacle> {

    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;

    public ObstacleRenderer(
            TiledMapTileLayer groundLayer,
            Obstacle obstacle
    ) {
        texture = new Texture("images/greenTree.png");
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);

        moveRectangleAtTileCenter(
                groundLayer,
                rectangle,
                obstacle.getPosition()
        );
    }

    @Override
    public void render(Batch batch, Obstacle obstacle) {
        drawTextureRegionUnscaled(
                batch,
                graphics,
                rectangle,
                0f
        );
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

}