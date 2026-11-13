package ru.mipt.bit.platformer.domain.controllers.handlers;

/** Application port translating raw input into a domain action value. */
public interface InputHandler<T> {
    T handle();
}
