package ru.mipt.bit.platformer;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.domain.world.RandomGameWorldLoader;

public class RandomGameDesktopLauncher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config =
                new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);

        new Lwjgl3Application(
                new PlatformerGame(new RandomGameWorldLoader(10, 8, 15, 5, new java.util.Random())),
                config
        );
    }
}
