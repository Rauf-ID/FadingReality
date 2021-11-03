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
        SET_MELEE_WEAPON,
        SET_RANGED_WEAPON,
        REMOVE_MELEE_WEAPON,
        REMOVE_RANGED_WEAPON,
        INIT_ALL_AMMO_COUNT,
        ATTAKING_IS_DONE,
        ACTIVATE_ANIM_MECHAN
    }

    void dispose();
    void receiveMessage(String message);
}
