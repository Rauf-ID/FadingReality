package com.mygdx.game.quest;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityConfig;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.world.MapManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

public class QuestGraph {

    private static final String TAG = QuestGraph.class.getSimpleName();

    private String questID;
    private String questTitle;
    private boolean isQuestComplete;
    private int goldReward;
    private int xpReward;
    private Hashtable<String, QuestTask> questTasks;
    private Hashtable<String, ArrayList<QuestTaskDependency>> questTaskDependencies;

    public QuestGraph() {
    }

    public int getGoldReward() {
        return goldReward;
    }

    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    public void isQuestComplete2() {

    }

    public boolean isQuestComplete() {
        return isQuestComplete;
    }

    public void setQuestComplete(boolean isQuestComplete) {
        this.isQuestComplete = isQuestComplete;
    }

    public String getQuestID() {
        return questID;
    }

    public void setQuestID(String questID) {
        this.questID = questID;
    }

    public String getQuestTitle() {
        return questTitle;
    }

    public void setQuestTitle(String questTitle) {
        this.questTitle = questTitle;
    }

    public boolean areAllTasksComplete(){
        ArrayList<QuestTask> tasks = getAllQuestTasks();
        for( QuestTask task: tasks ){
            if( !task.isTaskComplete() ){
                return false;
            }
        }
        return true;
    }

    public void setTasks(Hashtable<String, QuestTask> questTasks) {
        if( questTasks.size() < 0 ){
            throw new IllegalArgumentException("Can't have a negative amount of conversations");
        }

        this.questTasks = questTasks;
        this.questTaskDependencies = new Hashtable<String, ArrayList<QuestTaskDependency>>(questTasks.size());

        for( QuestTask questTask: questTasks.values() ){
            questTaskDependencies.put(questTask.getId(), new ArrayList<QuestTaskDependency>());
        }
    }

    public ArrayList<QuestTask> getAllQuestTasks(){
        Enumeration<QuestTask> enumeration = questTasks.elements();
        return Collections.list(enumeration);
    }

    public void clear(){
        questTasks.clear();
        questTaskDependencies.clear();
    }

    public Hashtable<String, QuestTask> getQuestTasks() {
        return questTasks;
    }

    public void setQuestTasks(Hashtable<String, QuestTask> questTasks) {
        this.questTasks = questTasks;
    }

    public boolean isValid(String taskID){
        QuestTask questTask = questTasks.get(taskID);
        if( questTask == null ) return false;
        return true;
    }

    public boolean isReachable(String sourceID, String sinkID){
        if( !isValid(sourceID) || !isValid(sinkID) ) return false;
        if( questTasks.get(sourceID) == null ) return false;

        ArrayList<QuestTaskDependency> list = questTaskDependencies.get(sourceID);
        if( list == null ) return false;
        for(QuestTaskDependency dependency: list){
            if(     dependency.getSourceId().equalsIgnoreCase(sourceID) &&
                    dependency.getDestinationId().equalsIgnoreCase(sinkID) ){
                return true;
            }
        }
        return false;
    }

    public QuestTask getQuestTaskByID(String id){
        if( !isValid(id) ){
            //System.out.println("Id " + id + " is not valid!");
            return null;
        }
        return questTasks.get(id);
    }

    public void addDependency(QuestTaskDependency questTaskDependency){
        ArrayList<QuestTaskDependency> list = questTaskDependencies.get(questTaskDependency.getSourceId());
        if( list == null) return;

        //Will not add if creates cycles
        if( doesCycleExist(questTaskDependency) ){
            //System.out.println("Cycle exists! Not adding");
            return;
        }

        list.add(questTaskDependency);
    }

    public boolean doesCycleExist(QuestTaskDependency questTaskDep){
        Set<String> keys = questTasks.keySet();
        for( String id: keys ){
            if( doesQuestTaskHaveDependencies(id) &&
                    questTaskDep.getDestinationId().equalsIgnoreCase(id)){
                //System.out.println("ID: " + id + " destID: " + questTaskDep.getDestinationId());
                return true;
            }
        }
        return false;
    }

    public boolean doesQuestTaskHaveDependencies(String id){
        QuestTask task = getQuestTaskByID(id);
        if( task == null) return false;
        ArrayList<QuestTaskDependency> list = questTaskDependencies.get(id);

        if( list.isEmpty() || list.size() == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateQuestForReturn(){
        ArrayList<QuestTask> tasks = getAllQuestTasks();
        QuestTask readyTask = null;

        //First, see if all tasks are available, meaning no blocking dependencies
        for( QuestTask task : tasks){
            if( !isQuestTaskAvailable(task.getId())){
                return false;
            }
            if( !task.isTaskComplete() ){
                if( task.getTaskType().equals(QuestTask.TaskType.RETURN) ){
                    readyTask = task;
                }else{
                    return false;
                }
            }
        }
        if( readyTask == null ) return false;
//        readyTask.setTaskComplete();
        return true;
    }

    public boolean isQuestTaskAvailable(String id){
        QuestTask task = getQuestTaskByID(id);
        if( task == null) return false;
        ArrayList<QuestTaskDependency> list = questTaskDependencies.get(id);

        for(QuestTaskDependency dep: list){
            QuestTask depTask = getQuestTaskByID(dep.getDestinationId());
            if( depTask == null || depTask.isTaskComplete() ) continue;
            if( dep.getSourceId().equalsIgnoreCase(id) ){
                return false;
            }
        }
        return true;
    }

    public void setQuestTaskComplete(String id){
        QuestTask task = getQuestTaskByID(id);
        if( task == null) return;
//        task.setTaskComplete();
    }

    public void update(MapManager mapMgr){
        ArrayList<QuestTask> allQuestTasks = getAllQuestTasks();
        for( QuestTask questTask: allQuestTasks ) {

            if( questTask.isTaskComplete() ) continue;

            //We first want to make sure the task is available and is relevant to current location
//            if (!isQuestTaskAvailable(questTask.getId())) continue;

            String targetLocation = questTask.getTaskProperties().getTargetLocation();
            if (targetLocation == null || targetLocation.isEmpty() || !targetLocation.equalsIgnoreCase(mapMgr.getCurrentMapType().toString())) continue;

            switch (questTask.getTaskType()) {
                case TALK:
                    QuestTaskProperty questTaskProperty = questTask.getTaskProperties();
                    for (int i = 0; i < questTaskProperty.getNumberEntities(); i++) {
                        if (questTaskProperty.getTargetName().get(i).equals(mapMgr.getCurrentMapEntity().getEntityConfig().getEntityID())) {
                            questTask.setTaskComplete(true);
                        }
                    }
                    break;
                case FETCH:
                    break;
                case KILL:
                    break;
                case DELIVERY:
                    break;
                case GUARD:
                    break;
                case ESCORT:
                    break;
                case RETURN:
                    break;
                case DISCOVER:
                    break;
            }
        }
    }

    public void init(MapManager mapMgr){
        ArrayList<QuestTask> allQuestTasks = getAllQuestTasks();
        for(QuestTask questTask: allQuestTasks) {

            if(questTask.isTaskComplete()) continue;

            //We first want to make sure the task is available and is relevant to current location
//            if (!isQuestTaskAvailable(questTask.getId())) continue;

            String targetLocation = questTask.getTaskProperties().getTargetLocation();
            if (targetLocation == null || targetLocation.isEmpty() || !targetLocation.equalsIgnoreCase(mapMgr.getCurrentMapType().toString())) continue;

            switch (questTask.getTaskType()) {

                case TALK:
                    System.out.println(questTask.getTaskPhrase() + " INIT");
                    Array<Entity> questEntities = new Array<>();
                    QuestTaskProperty taskProperties = questTask.getTaskProperties();

                    System.out.println(taskProperties.getTargetName());

                    for (int i = 0; i < taskProperties.getNumberEntities(); i++) {
                        EntityFactory.EntityName entityName = EntityFactory.EntityName.valueOf(taskProperties.getTargetName().get(i));
                        Vector2 position = taskProperties.getTargetsPositions().get(i);
                        Entity.Direction direction = taskProperties.getDirection();
                        String conversationConfigPath = taskProperties.getConversationConfigPath();

                        Entity entity = EntityFactory.getInstance().getNPCByNameForQuest(entityName, position, direction, conversationConfigPath);

                        questEntities.add(entity);
                    }

                    mapMgr.addMapQuestEntities(questEntities);
                    break;
                case FETCH:
                    break;
                case KILL:
                    break;
                case DELIVERY:
                    break;
                case GUARD:
                    break;
                case ESCORT:
                    break;
                case RETURN:
                    break;
                case DISCOVER:
                    break;
            }
        }
    }

    public String toString(){
        return questTitle;
    }

    public String toJson(){
        Json json = new Json();
        return json.prettyPrint(this);
    }

}
