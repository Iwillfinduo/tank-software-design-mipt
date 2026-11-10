package ru.mipt.bit.platformer.domain.controllers;

public interface Controller {

    void update(float deltaTime);

    default void collectCommands(float deltaTime, java.util.Collection<Command> commands) {
        commands.add(() -> update(deltaTime));
    }

}
