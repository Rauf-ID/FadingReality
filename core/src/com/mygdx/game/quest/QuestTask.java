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
    private ObjectMap<String, Object> taskPropertiesOld;

    public QuestTask(){
        taskProperties = new QuestTaskProperty();
        taskPropertiesOld = new ObjectMap<String, Object>();
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

    public ObjectMap<String, Object> getTaskPropertiesOld() {
        return taskPropertiesOld;
    }

    public void setTaskPropertiesOld(ObjectMap<String, Object> taskPropertiesOld) {
        this.taskPropertiesOld = taskPropertiesOld;
    }

    public boolean isTaskComplete(){
        if( !taskPropertiesOld.containsKey(QuestTaskPropertyType.IS_TASK_COMPLETE.toString()) ){
            setPropertyValue(QuestTaskPropertyType.IS_TASK_COMPLETE.toString(), "false");
            return false;
        }
        String val = taskPropertiesOld.get(QuestTaskPropertyType.IS_TASK_COMPLETE.toString()).toString();
        return Boolean.parseBoolean(val);
    }

    public void setTaskComplete(){
        setPropertyValue(QuestTaskPropertyType.IS_TASK_COMPLETE.toString(), "true");
    }

    public void resetAllProperties(){
        taskPropertiesOld.put(QuestTaskPropertyType.IS_TASK_COMPLETE.toString(), "false");
    }

    public void setPropertyValue(String key, String value){
        taskPropertiesOld.put(key, value);
    }

    public String getPropertyValue(String key){
        Object propertyVal = taskPropertiesOld.get(key);
        if( propertyVal == null ) return new String();
        return propertyVal.toString();
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
