package com.mygdx.game.plot;

import com.badlogic.gdx.utils.Array;
public class QuestSystem {

    public enum QuestProgress {
        NOT_AVAILABLE,
        AVAILABLE,
        ACCEPTED,
        DONE
    }

    //info
    public QuestProgress questProgress;
    public String description;
    public String title;
    public String hint;
    public int id;
    public Array<QuestTask> questTasks;
    public String nextQuest; //if chain quest

    //reward
    public int expReward;
    public int moneyReward;
    public String itemReward;


    public QuestProgress getQuestProgress() {
        return questProgress;
    }

    public void setQuestProgress(QuestProgress questProgress) {
        this.questProgress=questProgress;
    }

    public int getExpReward() {
        return expReward;
    }

    public void setExpReward(int expReward) {
        this.expReward = expReward;
    }

    public int getMoneyReward() {
        return moneyReward;
    }

    public void setMoneyReward(int moneyReward) {
        this.moneyReward = moneyReward;
    }

    public String getItemReward() {
        return itemReward;
    }

    public void setItemReward(String itemReward) {
        this.itemReward = itemReward;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Array<QuestTask> getQuestTasks() {
        return questTasks;
    }

    public QuestTask getQuestTaskByID(int id){
        return questTasks.get(id);
    }

    public void setNextQuest(String nextQuest) {
        this.nextQuest = nextQuest;
    }

    public String getNextQuest() {
        return nextQuest;
    }




}
