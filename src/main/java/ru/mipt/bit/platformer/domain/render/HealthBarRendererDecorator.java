package ru.mipt.bit.platformer.domain.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.domain.entities.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

public final class HealthBarRendererDecorator<T extends Tank>
        implements EntityRenderer<T> {

    private static final float BAR_WIDTH = 56f;
    private static final float BAR_HEIGHT = 7f;
    private static final float BAR_OFFSET_Y = 68f;

    private final EntityRenderer<T> delegate;
    private final TileMovement tileMovement;
    private final Texture pixel;
    private final Rectangle position = new Rectangle();
    private final HealthBarVisibility visibility;

    public HealthBarRendererDecorator(
            EntityRenderer<T> delegate,
            TileMovement tileMovement,
            HealthBarVisibility visibility
    ) {
        this.delegate = delegate;
        this.tileMovement = tileMovement;
        this.visibility = visibility;
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        pixel = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void render(Batch batch, T tank) {
        delegate.render(batch, tank);
        if (!visibility.areHealthBarsVisible() || !tank.isAlive()) return;

        tileMovement.moveRectangleBetweenTileCenters(
                position,
                tank.getPosition(),
                tank.getDestination(),
                tank.getMovementProgress()
        );
        float centerX = position.x + position.width / 2f;
        float left = centerX - BAR_WIDTH / 2f;
        float bottom = position.y + BAR_OFFSET_Y;
        float healthRatio = (float) tank.getHealth() / tank.getMaxHealth();

        Color originalColor = batch.getColor().cpy();
        try {
            batch.setColor(Color.DARK_GRAY);
            batch.draw(pixel, left, bottom, BAR_WIDTH, BAR_HEIGHT);
            batch.setColor(tank.getHealth() > tank.getMaxHealth() / 3
                    ? Color.GREEN
                    : Color.RED);
            batch.draw(pixel, left, bottom, BAR_WIDTH * healthRatio, BAR_HEIGHT);
        } finally {
            batch.setColor(originalColor);
        }
    }

    @Override
    public void dispose() {
        delegate.dispose();
        pixel.dispose();
    }

    public boolean areHealthBarsVisible() {
        return visibility.areHealthBarsVisible();
    }
}
