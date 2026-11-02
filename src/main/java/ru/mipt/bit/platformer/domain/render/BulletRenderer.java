package ru.mipt.bit.platformer.domain.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.domain.entities.Bullet;
import ru.mipt.bit.platformer.util.TileMovement;

public final class BulletRenderer implements EntityRenderer<Bullet> {
    private static final float TEXTURE_ROTATION_OFFSET = -90f;

    private final Texture texture;
    private final TextureRegion graphics;
    private final TileMovement movement;
    private final Rectangle rectangle = new Rectangle();

    public BulletRenderer(TiledMapTileLayer layer, TileMovement movement) {
        this.movement = movement;
        texture = new Texture("images/bullet.png");
        graphics = new TextureRegion(texture);
    }

    @Override public void render(Batch batch, Bullet bullet) {
        float centerX = bullet.getWorldPosition().x * movement.getTileWidth();
        float centerY = bullet.getWorldPosition().y * movement.getTileHeight();
        rectangle.set(
                centerX - texture.getWidth() / 2f,
                centerY - texture.getHeight() / 2f,
                texture.getWidth(),
                texture.getHeight()
        );
        batch.draw(
                graphics,
                rectangle.x,
                rectangle.y,
                texture.getWidth() / 2f,
                texture.getHeight() / 2f,
                rectangle.width,
                rectangle.height,
                1f,
                1f,
                bullet.getDirection().getRotation() + TEXTURE_ROTATION_OFFSET
        );
    }

    @Override public void dispose() { texture.dispose(); }
}
