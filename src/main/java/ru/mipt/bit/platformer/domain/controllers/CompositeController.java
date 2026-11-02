package ru.mipt.bit.platformer.domain.controllers;

import java.util.List;

public final class CompositeController implements Controller {

    private final List<Controller> controllers;

    public CompositeController(List<? extends Controller> controllers) {
        this.controllers = List.copyOf(controllers);
    }

    public CompositeController(Controller... controllers) {
        this(List.of(controllers));
    }

    @Override
    public void update(float deltaTime) {
        controllers.forEach(controller -> controller.update(deltaTime));
    }
}
