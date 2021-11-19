package com.mygdx.game.quest;

import com.badlogic.gdx.utils.ObjectMap;

public class QuestTask {

    public enum TaskType {
        TALK,

        FETCH,
        KILL,
        DELIVERY,
        GUARD,
        ESCORT,
        RETURN,
        DISCOVER
    }

    public enum QuestTaskPropertyType{
        IS_TASK_COMPLETE,
        TARGET_TYPE,
        TARGET_NUM,
        TARGET_LOCATION,
        NONE
    }

    private String id;
    private String taskPhrase;
    private TaskType taskType;
    private boolean isTaskComplete;
    private QuestTaskProperty taskProperties;

    public QuestTask(){
        taskProperties = new QuestTaskProperty();
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

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public boolean isTaskComplete() {
        return isTaskComplete;
    }

    public void setTaskComplete(boolean taskComplete) {
        isTaskComplete = taskComplete;
    }

    public QuestTaskProperty getTaskProperties() {
        return taskProperties;
    }

    public void setTaskProperties(QuestTaskProperty taskProperties) {
        this.taskProperties = taskProperties;
    }

    public String toString(){
        return taskPhrase;
    }



}
