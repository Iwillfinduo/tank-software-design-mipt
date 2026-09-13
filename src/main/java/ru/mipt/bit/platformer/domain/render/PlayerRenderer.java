package ru.mipt.bit.platformer.domain.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class PlayerRenderer implements EntityRenderer<Player> {


    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;

    public PlayerRenderer(
            TiledMapTileLayer groundLayer,
            TileMovement tileMovement
    ) {
        this.tileMovement = tileMovement;

        texture = new Texture("images/tank_blue.png");
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
    }


    @Override
    public void render(Batch batch, Player player) {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                player.getPosition(),
                player.getDestination(),
                player.getMovementProgress()
        );

        drawTextureRegionUnscaled(
                batch,
                graphics,
                rectangle,
                player.getRotation()
        );
    }



    @Override
    public void dispose() {
        texture.dispose();
    }

}
