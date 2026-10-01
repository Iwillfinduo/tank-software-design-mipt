package ru.mipt.bit.platformer.domain.controllers.handlers;

import com.badlogic.gdx.Gdx;

public class GdxKeyInput implements KeyInput{
    @Override
    public boolean isPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }
}
