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
        executeCommands(collectCommands(deltaTime));
    }

    public java.util.List<Command> collectCommands(float deltaTime) {
        java.util.List<Command> commands = new java.util.ArrayList<>();
        controllers.forEach(controller -> controller.collectCommands(deltaTime, commands));
        return commands;
    }

    public void executeCommands(java.util.Collection<? extends Command> commands) {
        commands.forEach(Command::execute);
    }
}
