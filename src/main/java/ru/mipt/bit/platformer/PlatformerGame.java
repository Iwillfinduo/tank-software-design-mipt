package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.domain.controllers.handlers.GdxKeyInput;
import ru.mipt.bit.platformer.domain.controllers.handlers.InputHandler;
import ru.mipt.bit.platformer.domain.controllers.handlers.KeyInput;
import ru.mipt.bit.platformer.domain.controllers.handlers.MovementInputHandler;
import ru.mipt.bit.platformer.domain.controllers.PlayerController;
import ru.mipt.bit.platformer.domain.controllers.handlers.bindings.StandartMovementKeyBindings;
import ru.mipt.bit.platformer.domain.render.GameRenderer;
import ru.mipt.bit.platformer.domain.render.ObstacleRenderer;
import ru.mipt.bit.platformer.domain.render.PlayerRenderer;
import ru.mipt.bit.platformer.domain.world.GameWorld;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.TileMovement;


import java.util.Set;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class PlatformerGame implements ApplicationListener {

    private Batch batch;
    private TiledMap level;

    private GameWorld world;
    private PlayerController playerController;
    private GameRenderer gameRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();

        level = new TmxMapLoader().load("level.tmx");

        TiledMapTileLayer groundLayer = getSingleLayer(level);
        MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);

        TileMovement tileMovement =
                new TileMovement(groundLayer, Interpolation.smooth);

        world = new GameWorld();

        // Ох, сюда бы спринг или другой DI фреймворк
        StandartMovementKeyBindings bindings = new StandartMovementKeyBindings(
                Set.of(Input.Keys.UP, Input.Keys.W),
                Set.of(Input.Keys.LEFT, Input.Keys.A),
                Set.of(Input.Keys.DOWN, Input.Keys.S),
                Set.of(Input.Keys.RIGHT, Input.Keys.D)
        );

        KeyInput keyInput = new GdxKeyInput();

        InputHandler<Direction> movementInputHandler =
                new MovementInputHandler(keyInput, bindings);

        playerController =
                new PlayerController(
                        world.getPlayer(),
                        world.getObstacles(),
                        movementInputHandler,
                        0.4f
                );

        PlayerRenderer playerRenderer =
                new PlayerRenderer(groundLayer, tileMovement);

        ObstacleRenderer obstacleRenderer =
                new ObstacleRenderer(groundLayer, world.getObstacles().get(0));

        gameRenderer = new GameRenderer(
                levelRenderer,
                batch,
                playerRenderer,
                obstacleRenderer
        );
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        playerController.update(deltaTime);

        gameRenderer.render(world);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        level.dispose();
        batch.dispose();
    }
}