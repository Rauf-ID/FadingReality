package com.mygdx.game.quest;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class QuestTaskProperty {

    //GENERAL
    private String targetLocation;
    private Array<Vector2> targetsPositions;
    private String targetName;

    //TALK
    private String conversationConfigPath;

    private String numOfConversation;
    private int targetSearchCount;
    private int targetKillCount;
    private int targetTime;

    public QuestTaskProperty() {
        targetsPositions = new Array<>();
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }

    public Array<Vector2> getTargetsPositions() {
        return targetsPositions;
    }

    public void setTargetsPositions(Array<Vector2> targetsPositions) {
        this.targetsPositions = targetsPositions;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getConversationConfigPath() {
        return conversationConfigPath;
    }

    public void setConversationConfigPath(String conversationConfigPath) {
        this.conversationConfigPath = conversationConfigPath;
    }

    public String getNumOfConversation() {
        return numOfConversation;
    }

    public void setNumOfConversation(String numOfConversation) {
        this.numOfConversation = numOfConversation;
    }

    public int getTargetSearchCount() {
        return targetSearchCount;
    }

    public void setTargetSearchCount(int targetSearchCount) {
        this.targetSearchCount = targetSearchCount;
    }

    public int getTargetKillCount() {
        return targetKillCount;
    }

    public void setTargetKillCount(int targetKillCount) {
        this.targetKillCount = targetKillCount;
    }

    public int getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(int targetTime) {
        this.targetTime = targetTime;
    }
}
