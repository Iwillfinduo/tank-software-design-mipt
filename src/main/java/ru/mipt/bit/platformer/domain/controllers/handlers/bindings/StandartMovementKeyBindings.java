package ru.mipt.bit.platformer.domain.controllers.handlers.bindings;

import java.util.Set;

/*
 Возможно(!) это оверкилл, да простят меня C предки, писавшие все в̶ ̶о̶д̶и̶н̶ ̶м̶е̶т̶о̶д̶ в одну функцию
 за этот кошмар
 */
public class StandartMovementKeyBindings {

    private final Set<Integer> upKeys;
    private final Set<Integer> leftKeys;
    private final Set<Integer> downKeys;
    private final Set<Integer> rightKeys;

    public StandartMovementKeyBindings(
            Set<Integer> upKeys,
            Set<Integer> leftKeys,
            Set<Integer> downKeys,
            Set<Integer> rightKeys
    ) {
        this.upKeys = upKeys;
        this.leftKeys = leftKeys;
        this.downKeys = downKeys;
        this.rightKeys = rightKeys;
    }

    public Set<Integer> getUpKeys() {
        return upKeys;
    }

    public Set<Integer> getLeftKeys() {
        return leftKeys;
    }

    public Set<Integer> getDownKeys() {
        return downKeys;
    }

    public Set<Integer> getRightKeys() {
        return rightKeys;
    }
}