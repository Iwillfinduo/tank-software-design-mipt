package ru.mipt.bit.platformer.domain.controllers.handlers;

import com.badlogic.gdx.Gdx;

/** LibGDX adapter implementing the {@link KeyInput} input port. */
public class GdxKeyInput implements KeyInput{
    @Override
    public boolean isPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }
}
