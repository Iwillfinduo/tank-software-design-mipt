package ru.mipt.bit.platformer.domain.world;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.domain.level.LevelFileLoader;
import ru.mipt.bit.platformer.domain.level.LevelGenerator;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LevelWorldTest {

    @Test
    void fileLevelContainsTreesAndPlayerStart() {
        GameWorld world = LevelFileLoader.load(List.of(
                "___T__T___",
                "__TT__TTTT",
                "_________T",
                "TTTT__T__T",
                "_____X____",
                "__________"
        ));

        assertEquals(new GridPoint2(5, 1), world.getPlayer().getPosition());
        assertEquals(15, world.getObstacles().size());
    }

    @Test
    void generatedLevelUsesDistinctFreePlayerCell() {
        GameWorld world = LevelGenerator.generate(5, 4, 10, new Random(42));

        assertEquals(10, world.getObstacles().size());
        assertNotEquals(
                world.getPlayer().getPosition(),
                world.getObstacles().get(0).getPosition()
        );
    }

    @Test
    void fileLevelRejectsUnknownCharactersAndMissingPlayer() {
        assertThrows(IllegalArgumentException.class,
                () -> LevelFileLoader.load(List.of("X?T", "___")));
        assertThrows(IllegalArgumentException.class,
                () -> LevelFileLoader.load(List.of("___", "_T_")));
    }

    @Test
    void fileLevelRejectsMalformedRowsAndDuplicatePlayers() {
        assertThrows(IllegalArgumentException.class,
                () -> LevelFileLoader.load(List.of("X__", "__")));
        assertThrows(IllegalArgumentException.class,
                () -> LevelFileLoader.load(List.of("X_X")));
        assertThrows(IllegalArgumentException.class,
                () -> LevelFileLoader.load(List.of()));
        assertThrows(IllegalArgumentException.class,
                () -> LevelFileLoader.loadText(null));
    }

    @Test
    void generatorValidatesDimensionsAndObstacleCount() {
        assertThrows(IllegalArgumentException.class,
                () -> LevelGenerator.generate(0, 3, 0, new Random()));
        assertThrows(IllegalArgumentException.class,
                () -> LevelGenerator.generate(3, 3, -1, new Random()));
        assertThrows(IllegalArgumentException.class,
                () -> LevelGenerator.generate(3, 3, 9, new Random()));
        assertThrows(IllegalArgumentException.class,
                () -> LevelGenerator.generate(3, 3, 1, null));
    }

    @Test
    void fileRowsAreMappedFromTopToBottom() {
        GameWorld world = LevelFileLoader.load(List.of(
                "X__",
                "___",
                "__T"
        ));

        assertEquals(new GridPoint2(0, 2), world.getPlayer().getPosition());
        assertEquals(new GridPoint2(2, 0), world.getObstacles().get(0).getPosition());
    }
}
