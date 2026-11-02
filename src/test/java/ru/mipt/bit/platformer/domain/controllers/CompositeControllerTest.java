package ru.mipt.bit.platformer.domain.controllers;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompositeControllerTest {

    @Test
    void updatesAllControllersInOrder() {
        List<String> updates = new ArrayList<>();
        Controller first = deltaTime -> updates.add("first:" + deltaTime);
        Controller second = deltaTime -> updates.add("second:" + deltaTime);

        new CompositeController(first, second).update(0.5f);

        assertEquals(
                List.of("first:0.5", "second:0.5"),
                updates
        );
    }
}
