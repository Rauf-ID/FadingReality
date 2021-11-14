package com.mygdx.game.quest;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class QuestTaskProperty {

    public String targetName;
    public String targetLocation;
    public Array<Vector2> targetsPositions;
    public String numOfConversation;
    public int targetSearchCount;
    public int targetKillCount;
    public int targetTime;

    public QuestTaskProperty() {
        targetsPositions = new Array<>();
    }


}
