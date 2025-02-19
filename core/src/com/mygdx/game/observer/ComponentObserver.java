package com.mygdx.game.observer;

public interface ComponentObserver {
    enum ComponentEvent {
        LOAD_CONVERSATION,
        SHOW_CONVERSATION,
        HIDE_CONVERSATION,
        QUEST_LOCATION_DISCOVERED,
        ENEMY_SPAWN_LOCATION_CHANGED,
        PLAYER_HAS_MOVED,
        PLAYER_SHOT,
        ENEMY_DEAD,
        ITEM_PICK_UP,
        PLAYER_DASH,
        PLAYER_DASH_UPDATE,
        RANGE_WEAPON_UPDATE,
        OPEN_PDA,
        CLOSE_PDA,
        OPEN_INVENTORY,
        CLOSE_INVENTORY,
        TEST_EVENT,
    }

    void onNotify(final String value, ComponentEvent event);
}