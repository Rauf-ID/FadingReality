package com.mygdx.game.managers;

import com.mygdx.game.entity.Entity;

public class ControlManager {

    protected enum State {
        NORMAL,
        ATTACK,
        DEAD,
    }

    private State state;

    public void ControlManager() {
        state = State.NORMAL;
    }

    public void update(Entity entity) {

    }

}
