package ru.mipt.bit.platformer.domain.controllers;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.domain.controllers.handlers.KeyInput;
import ru.mipt.bit.platformer.domain.render.HealthBarVisibility;

import static org.mockito.Mockito.*;

class HealthBarsToggleControllerTest {

    @Test
    void executesToggleOncePerKeyPress() {
        KeyInput input = mock(KeyInput.class);
        HealthBarVisibility visibility = mock(HealthBarVisibility.class);
        when(input.isPressed(42)).thenReturn(true, true, false, true);

        HealthBarsToggleController controller =
                new HealthBarsToggleController(input, 42, visibility);

        controller.update(0f);
        controller.update(0f);
        controller.update(0f);
        controller.update(0f);

        verify(visibility, times(2)).toggleHealthBars();
    }
}
