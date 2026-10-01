package ru.mipt.bit.platformer.domain.controllers.handlers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mipt.bit.platformer.domain.controllers.handlers.bindings.StandartMovementKeyBindings;
import ru.mipt.bit.platformer.util.Direction;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class MovementInputHandlerTest {
    @Mock
    private Input input;

    private AutoCloseable mocks;
    private KeyInput gdxKeyInput;
    private StandartMovementKeyBindings bindings;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        Gdx.input = input;
        gdxKeyInput = new GdxKeyInput();
        bindings = new StandartMovementKeyBindings(
                Set.of(Input.Keys.UP, Input.Keys.W),
                Set.of(Input.Keys.LEFT, Input.Keys.A),
                Set.of(Input.Keys.DOWN, Input.Keys.S),
                Set.of(Input.Keys.RIGHT, Input.Keys.D)
        );
    }



    @Test
    public void detectsUp() {
        when(input.isKeyPressed(Input.Keys.UP)).thenReturn(true);

        MovementInputHandler handler =
                new MovementInputHandler(gdxKeyInput, bindings);

        assertEquals(
                Direction.UP,
                handler.handle()
        );
    }

    @Test
    public void detectsLeft() {
        when(input.isKeyPressed(Input.Keys.LEFT)).thenReturn(true);

        MovementInputHandler handler =
                new MovementInputHandler(gdxKeyInput, bindings);

        assertEquals(
                Direction.LEFT,
                handler.handle()
        );
    }

    @Test
    public void detectsDown() {
        when(input.isKeyPressed(Input.Keys.DOWN)).thenReturn(true);

        MovementInputHandler handler =
                new MovementInputHandler(gdxKeyInput, bindings);

        assertEquals(
                Direction.DOWN,
                handler.handle()
        );
    }

    @Test
    public void detectsRight() {
        when(input.isKeyPressed(Input.Keys.RIGHT)).thenReturn(true);

        MovementInputHandler handler =
                new MovementInputHandler(gdxKeyInput, bindings);

        assertEquals(
                Direction.RIGHT,
                handler.handle()
        );
    }

    @Test
    public void returnsNullWhenNoKeyPressed() {
        MovementInputHandler handler =
                new MovementInputHandler(gdxKeyInput, bindings);

        assertNull(handler.handle());
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }
}
