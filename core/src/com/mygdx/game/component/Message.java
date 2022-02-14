package com.mygdx.game.component;

public interface Message {

    public static final String MESSAGE_TOKEN = ":::::";
    public static final String MESSAGE_TOKEN_2 = ":::";

    public static enum MESSAGE{
        INIT_START_POSITION,
        ITEM_MAP_SPAWN_ID,
        ITEM_IS_FOR_QUEST,
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
        SET_RUDIMENT_ONE,
        SET_RUDIMENT_TWO,
        SET_UNIQUE_RUDIMENT,
        REMOVE_MELEE_WEAPON,
        REMOVE_RANGED_WEAPON,
        REMOVE_RUDIMENT_ONE,
        REMOVE_RUDIMENT_TWO,
        REMOVE_UNIQUE_RUDIMENT,
        INIT_ALL_AMMO_COUNT,
        ATTAKING_IS_DONE,
        ACTIVATE_ANIM_MECHAN,
        EQUIP_EXOSKELETON,
        EXOSKELETON_ON,
        ENEMY_KILLED,
        UNLOCK_FIRST_SKILLS,
        INIT_ITEM,
        STUN;
    }

    void dispose();
    void receiveMessage(String message);
}
