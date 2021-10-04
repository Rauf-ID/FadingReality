package com.mygdx.game.observer;

import com.badlogic.gdx.utils.Array;

public class ComponentSubject {
    private Array<ComponentObserver> observers;

    public ComponentSubject(){
        observers = new Array<ComponentObserver>();
    }

    public void addObserver(ComponentObserver componentObserver){
        observers.add(componentObserver);
    }

    public void removeObserver(ComponentObserver componentObserver){
        observers.removeValue(componentObserver, true);
    }

    public void removeAllObservers(){
        for(ComponentObserver observer: observers){
            observers.removeValue(observer, true);
        }
    }

    protected void notify(final String value, ComponentObserver.ComponentEvent event){
        for(ComponentObserver observer: observers){
            observer.onNotify(value, event);
        }
    }
}
