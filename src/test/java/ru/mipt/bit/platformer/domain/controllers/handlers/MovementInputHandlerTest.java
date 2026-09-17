package ru.mipt.bit.platformer.domain.controllers.handlers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mipt.bit.platformer.util.Direction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class MovementInputHandlerTest {
    @Mock
    private Input input;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        Gdx.input = input;
    }



    @Test
    public void detectsUp() {
        when(input.isKeyPressed(Input.Keys.W)).thenReturn(true);

        MovementInputHandler handler =
                new MovementInputHandler();

        assertEquals(
                Direction.UP,
                handler.handle()
        );
    }

    @Test
    public void detectsLeft() {
        when(input.isKeyPressed(Input.Keys.A)).thenReturn(true);

        MovementInputHandler handler =
                new MovementInputHandler();

        assertEquals(
                Direction.LEFT,
                handler.handle()
        );
    }

    @Test
    public void detectsDown() {
        when(input.isKeyPressed(Input.Keys.S)).thenReturn(true);

        MovementInputHandler handler =
                new MovementInputHandler();

        assertEquals(
                Direction.DOWN,
                handler.handle()
        );
    }

    @Test
    public void detectsRight() {
        when(input.isKeyPressed(Input.Keys.D)).thenReturn(true);

        MovementInputHandler handler =
                new MovementInputHandler();

        assertEquals(
                Direction.RIGHT,
                handler.handle()
        );
    }

    @Test
    public void returnsNullWhenNoKeyPressed() {
        MovementInputHandler handler =
                new MovementInputHandler();

        assertNull(handler.handle());
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }
}
