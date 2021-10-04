package com.mygdx.game.component;

public interface Message {

    public static final String MESSAGE_TOKEN = ":::::";

    public static enum MESSAGE{
        CURRENT_POSITION,
        INIT_START_POSITION,
        CURRENT_DIRECTION,
        CURRENT_STATE,
        COLLISION_WITH_MAP,
        COLLISION_WITH_ENTITY,
        LOAD_ANIMATIONS,
        INIT_DIRECTION,
        INIT_STATE,
        INTERACTION_WITH_ENTITY,
        ENTITY_SELECTED,
        ENTITY_DESELECTED,
        INIT_CONFIG,
        ATTAKING_IS_DONE,
        ACTIVATE_ANIM_MECHAN
    }

    void dispose();
    void receiveMessage(String message);
}
