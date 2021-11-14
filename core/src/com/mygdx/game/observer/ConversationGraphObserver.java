package com.mygdx.game.observer;

import com.mygdx.game.dialogs.ConversationGraph;

public interface ConversationGraphObserver {
    enum ConversationCommandEvent {
        ACCEPT_QUEST,
        EXIT_CONVERSATION,
        ADD_ENTITY_TO_INVENTORY,
        LOAD_STORE_INVENTORY,
        RETURN_QUEST,
        NONE
    }

    void onNotify(final ConversationGraph graph, ConversationCommandEvent event);
}