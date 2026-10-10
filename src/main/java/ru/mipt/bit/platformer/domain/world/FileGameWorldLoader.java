package ru.mipt.bit.platformer.domain.world;

import ru.mipt.bit.platformer.util.ResourceReader;

import java.util.Objects;

public final class FileGameWorldLoader implements GameWorldLoader {

    private final ResourceReader resourceReader;
    private final String resourceName;

    public FileGameWorldLoader(ResourceReader resourceReader, String resourceName) {
        this.resourceReader = Objects.requireNonNull(resourceReader, "resourceReader");
        this.resourceName = Objects.requireNonNull(resourceName, "resourceName");
    }

    @Override
    public GameWorld load() {
        return GameWorld.fromText(resourceReader.read(resourceName));
    }
}
