package ru.mipt.bit.platformer.domain.controllers.handlers;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.Direction;

import static com.badlogic.gdx.Input.Keys.*;

public class MovementInputHandler implements InputHandler<Direction> {


   @Override
    public Direction handle() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return Direction.UP;
        }

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return Direction.LEFT;
        }

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return Direction.DOWN;
        }

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return Direction.RIGHT;
        }

        return null;
    }

    public Direction getDirection() {
       return this.handle();
    }
}
