package com.mygdx.game._oldClasses.plot;

public class QuestTask {

    public enum QuestType {
        TALK,
        SEARCH,
        KILL,
        MASSACRE,
        DEFEND,
        ESCAPE
    }

    public enum TaskProgress {
        NOT_AVAILABLE,
        AVAILABLE,
        ACCEPTED,
        DONE
    }

    private String id;
    private String taskPhrase;
    private QuestType questType;
    private TaskProgress taskProgress;

    private float targetX, targetY, widthRect, heightRect;


    public void setTaskProgress(TaskProgress taskProgress) {
        this.taskProgress = taskProgress;
    }

    public TaskProgress getTaskProgress() {
        return taskProgress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTaskPhrase() {
        return taskPhrase;
    }

    public void setTaskPhrase(String taskPhrase) {
        this.taskPhrase = taskPhrase;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public void setQuestType(QuestType questType) {
        this.questType = questType;
    }

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetnY() {
        return targetY;
    }

    public void setTargetnY(float targetnY) {
        this.targetY = targetnY;
    }

    public float getWidthRect() {
        return widthRect;
    }

    public void setWidthRect(float widthRect) {
        this.widthRect = widthRect;
    }

    public float getHeightRect() {
        return heightRect;
    }

    public void setHeightRect(float heightRect) {
        this.heightRect = heightRect;
    }


}
