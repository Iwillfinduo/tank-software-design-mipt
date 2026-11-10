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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.domain.controllers.handlers.GdxKeyInput;
import ru.mipt.bit.platformer.domain.controllers.handlers.InputHandler;
import ru.mipt.bit.platformer.domain.controllers.handlers.KeyInput;
import ru.mipt.bit.platformer.domain.controllers.handlers.MovementInputHandler;
import ru.mipt.bit.platformer.domain.controllers.CompositeController;
import ru.mipt.bit.platformer.domain.controllers.Controller;
import ru.mipt.bit.platformer.domain.controllers.PlayerController;
import ru.mipt.bit.platformer.domain.controllers.EnemyController;
import ru.mipt.bit.platformer.domain.controllers.PlayerShootController;
import ru.mipt.bit.platformer.domain.controllers.EnemyShootController;
import ru.mipt.bit.platformer.domain.controllers.HealthBarsToggleController;
import ru.mipt.bit.platformer.domain.controllers.strategies.RandomDirectionStrategy;
import ru.mipt.bit.platformer.domain.render.EnemyRenderer;
import ru.mipt.bit.platformer.domain.controllers.handlers.bindings.StandardMovementKeyBindings;
import ru.mipt.bit.platformer.domain.render.GameRenderer;
import ru.mipt.bit.platformer.domain.render.ObstacleRenderer;
import ru.mipt.bit.platformer.domain.render.PlayerRenderer;
import ru.mipt.bit.platformer.domain.render.BulletRenderer;
import ru.mipt.bit.platformer.domain.render.HealthBarRendererDecorator;
import ru.mipt.bit.platformer.domain.entities.Player;
import ru.mipt.bit.platformer.domain.world.GameWorld;
import ru.mipt.bit.platformer.domain.world.FileGameWorldLoader;
import ru.mipt.bit.platformer.domain.world.GameWorldLoader;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.ResourceReader;
import ru.mipt.bit.platformer.util.TileMovement;


import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class PlatformerGame implements ApplicationListener {

    private Batch batch;
    private TiledMap level;

    private GameWorld world;
    private CompositeController gameController;
    private GameRenderer gameRenderer;
    private final GameWorldLoader worldLoader;

    public PlatformerGame() {
        this(new FileGameWorldLoader(
                new ResourceReader(),
                "maps/test-map-with-enemies.txt"
        ));
    }

    public PlatformerGame(GameWorldLoader worldLoader) {
        this.worldLoader = Objects.requireNonNull(worldLoader, "worldLoader");
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        world = worldLoader.load();
        level = new TmxMapLoader().load("maps/level.tmx");
        resizeGroundLayer(level, world.getWidth(), world.getHeight());

        TiledMapTileLayer groundLayer = getSingleLayer(level);
        MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);

        TileMovement tileMovement =
                new TileMovement(groundLayer, Interpolation.smooth);

        // Ох, сюда бы спринг или другой DI фреймворк
        StandardMovementKeyBindings bindings = new StandardMovementKeyBindings(
                Set.of(Input.Keys.UP, Input.Keys.W),
                Set.of(Input.Keys.LEFT, Input.Keys.A),
                Set.of(Input.Keys.DOWN, Input.Keys.S),
                Set.of(Input.Keys.RIGHT, Input.Keys.D)
        );

        KeyInput keyInput = new GdxKeyInput();

        InputHandler<Direction> movementInputHandler =
                new MovementInputHandler(keyInput, bindings);

        List<Controller> controllers = new ArrayList<>();
        controllers.add(new PlayerController(world.getPlayer(), world, movementInputHandler, 0.4f));
        controllers.add(new PlayerShootController(world, world.getPlayer(), keyInput, Input.Keys.SPACE));
        Random enemyDelayRandom = new Random();
        for (ru.mipt.bit.platformer.domain.entities.Enemy enemy : world.getEnemies()) {
            controllers.add(new EnemyController(
                    enemy,
                    world,
                    new RandomDirectionStrategy(),
                    0.4f,
                    1.0f,
                    enemyDelayRandom.nextFloat() * 1.0f
            ));
            controllers.add(new EnemyShootController(world, enemy, enemyDelayRandom, 0.15f));
        }
        PlayerRenderer basePlayerRenderer =
                new PlayerRenderer(groundLayer, tileMovement);

        ObstacleRenderer obstacleRenderer = new ObstacleRenderer(groundLayer);
        EnemyRenderer baseEnemyRenderer = new EnemyRenderer(groundLayer, tileMovement);
        BulletRenderer bulletRenderer = new BulletRenderer(tileMovement);
        HealthBarRendererDecorator<Player> playerRenderer =
                new HealthBarRendererDecorator<>(basePlayerRenderer, tileMovement);
        HealthBarRendererDecorator<ru.mipt.bit.platformer.domain.entities.Enemy> enemyRenderer =
                new HealthBarRendererDecorator<>(baseEnemyRenderer, tileMovement);

        gameRenderer = new GameRenderer(
                levelRenderer,
                batch,
                playerRenderer,
                obstacleRenderer, enemyRenderer, bulletRenderer, world
        );
        controllers.add(new HealthBarsToggleController(keyInput, Input.Keys.L, gameRenderer));
        gameController = new CompositeController(controllers);
    }

    private static void resizeGroundLayer(TiledMap map, int width, int height) {
        if (width <= 0 || height <= 0) return;

        TiledMapTileLayer source = getSingleLayer(map);
        if (source.getWidth() == width && source.getHeight() == height) return;

        TiledMapTileLayer resized = new TiledMapTileLayer(
                width,
                height,
                source.getTileWidth(),
                source.getTileHeight()
        );
        resized.setName(source.getName());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = source.getCell(x % source.getWidth(), y % source.getHeight());
                if (cell != null) resized.setCell(x, y, cell);
            }
        }

        map.getLayers().remove(source);
        map.getLayers().add(resized);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        // Frame phases: collect input, execute commands, advance the model, render.
        List<ru.mipt.bit.platformer.domain.controllers.Command> commands =
                gameController.collectCommands(deltaTime);
        gameController.executeCommands(commands);
        world.liveTick(deltaTime);

        gameRenderer.render();
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