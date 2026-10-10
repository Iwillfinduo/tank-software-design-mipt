package ru.mipt.bit.platformer.domain.controllers.handlers.bindings;

import java.util.Set;

public record StandardMovementKeyBindings(Set<Integer> upKeys, Set<Integer> leftKeys, Set<Integer> downKeys,
                                          Set<Integer> rightKeys) {

}