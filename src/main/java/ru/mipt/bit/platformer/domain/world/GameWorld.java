package ru.mipt.bit.platformer.domain.world;

import ru.mipt.bit.platformer.domain.entities.Obstacle;
import ru.mipt.bit.platformer.domain.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {
    private final Player player;
    private final ArrayList<Obstacle> obstacles;

    public GameWorld() {
        this.player = new Player(1, 1);
        var tree = new Obstacle(1, 3);
        this.obstacles = new ArrayList<>(List.of(tree));
    }

    public Player getPlayer() {
        return player;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
}
