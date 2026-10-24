package ru.mipt.bit.platformer.domain.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.domain.entities.Enemy;
import ru.mipt.bit.platformer.util.TileMovement;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public final class EnemyRenderer implements EntityRenderer<Enemy> {
    private final Texture texture = new Texture("images/tank_red.png");
    private final TextureRegion graphics = new TextureRegion(texture);
    private final Rectangle rectangle = createBoundingRectangle(graphics);
    private final TileMovement movement;
    public EnemyRenderer(TiledMapTileLayer layer, TileMovement movement) { this.movement = movement; }
    @Override public void render(Batch batch, Enemy enemy) {
        movement.moveRectangleBetweenTileCenters(rectangle, enemy.getPosition(), enemy.getDestination(),
                enemy.getMovementProgress());
        drawTextureRegionUnscaled(batch, graphics, rectangle, enemy.getRotation());
    }
    @Override public void dispose() { texture.dispose(); }
}
