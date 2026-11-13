package ru.mipt.bit.platformer.domain.render;

import com.badlogic.gdx.graphics.g2d.Batch;

/** Infrastructure output port for drawing a domain entity. */
public interface EntityRenderer<T> {

    void render(Batch batch, T object);

    void dispose();

}