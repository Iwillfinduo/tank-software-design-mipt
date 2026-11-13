package ru.mipt.bit.platformer.domain.controllers;

/** Application port for a deferred user or AI action. */
public interface Command {
    void execute();
}
