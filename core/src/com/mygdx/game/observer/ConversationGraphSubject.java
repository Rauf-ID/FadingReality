package com.mygdx.game.observer;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.dialogs.ConversationGraph;

public class ConversationGraphSubject {
    private Array<ConversationGraphObserver> _observers;

    public ConversationGraphSubject() {
        _observers = new Array<ConversationGraphObserver>();
    }

    public void addObserver(ConversationGraphObserver graphObserver) {
        _observers.add(graphObserver);
    }

    public void removeObserver(ConversationGraphObserver graphObserver) {
        _observers.removeValue(graphObserver, true);
    }

    public void removeAllObservers() {
        for (ConversationGraphObserver observer : _observers) {
            _observers.removeValue(observer, true);
        }
    }

    public void notify(final ConversationGraph graph, ConversationGraphObserver.ConversationCommandEvent event) {
        for(ConversationGraphObserver observer: _observers){
            observer.onNotify(graph, event);
        }
    }
}
