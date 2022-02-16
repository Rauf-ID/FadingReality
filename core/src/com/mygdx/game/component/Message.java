package com.mygdx.game.component;

public interface Message {

    String MESSAGE_TOKEN = ":::::";
    String MESSAGE_TOKEN_2 = ":::";

    enum MESSAGE{
        INIT_ITEM,
        INIT_STATE,
        INIT_CONFIG,
        INIT_DIRECTION,
        INIT_ANIMATIONS,
        INIT_ID_MAP_SPAWN,
        INIT_ALL_AMMO_COUNT,
        INIT_START_POSITION,
        SET_EXOSKELETON,
        SET_MELEE_WEAPON,
        SET_RANGED_WEAPON,
        SET_RUDIMENT_ONE,
        SET_RUDIMENT_TWO,
        SET_UNIQUE_RUDIMENT,
        REMOVE_MELEE_WEAPON,
        REMOVE_RANGED_WEAPON,
        REMOVE_RUDIMENT_ONE,
        REMOVE_RUDIMENT_TWO,
        REMOVE_UNIQUE_RUDIMENT,
        ENTITY_SELECTED,
        ENTITY_DESELECTED,
        COLLISION_WITH_MAP,
        COLLISION_WITH_ENTITY,
        ENEMY_KILLED,
        ITEM_IS_FOR_QUEST,
        ACTIVATE_ANIM_MECHAN,
        STUN,
        INTERACTION_WITH_ENTITY
    }

    void dispose();
    void receiveMessage(String message);
}
