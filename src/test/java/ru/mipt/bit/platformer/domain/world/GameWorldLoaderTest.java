package ru.mipt.bit.platformer.domain.world;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.ResourceReader;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameWorldLoaderTest {

    @Test
    void fileLoaderBuildsWorldFromConfiguredResource() {
        ResourceReader reader = new ResourceReader(
                new ClassLoader() {
                    @Override
                    public java.io.InputStream getResourceAsStream(String resourceName) {
                        return new ByteArrayInputStream(
                                "__T\n_X_".getBytes(StandardCharsets.UTF_8)
                        );
                    }
                },
                StandardCharsets.UTF_8
        );

        GameWorld world = new FileGameWorldLoader(reader, "level.txt").load();

        assertEquals(new GridPoint2(1, 0), world.getPlayer().getPosition());
        assertEquals(new GridPoint2(2, 1), world.getObstacles().get(0).getPosition());
    }

    @Test
    void fileLoaderRequiresItsDependencies() {
        assertThrows(
                NullPointerException.class,
                () -> new FileGameWorldLoader(null, "level.txt")
        );
        assertThrows(
                NullPointerException.class,
                () -> new FileGameWorldLoader(new ResourceReader(), null)
        );
    }

    @Test
    void randomLoaderCreatesConfiguredWorld() {
        GameWorld world = new RandomGameWorldLoader(4, 3, 5, new Random(7)).load();

        assertEquals(5, world.getObstacles().size());
        assertNotEquals(
                world.getPlayer().getPosition(),
                world.getObstacles().get(0).getPosition()
        );
    }

    @Test
    void randomLoaderRequiresRandomSource() {
        assertThrows(
                NullPointerException.class,
                () -> new RandomGameWorldLoader(4, 3, 5, null)
        );
    }
}
