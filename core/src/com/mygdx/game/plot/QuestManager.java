package com.mygdx.game.plot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;


public class QuestManager {

    private QuestSystem questSystem;
    private Json json;

    public QuestManager() {
        json = new Json();

    }

    public void loadQuest(String questPath) {
        questSystem = json.fromJson(QuestSystem.class, Gdx.files.internal("main/plot/quests/"+ questPath +".json"));
        if(questSystem.questProgress==QuestSystem.QuestProgress.DONE) {
            loadQuest(questSystem.nextQuest);
        }
    }

    public void saveQuest(String questPath) {
        FileHandle fileQuest = Gdx.files.local(questPath);
        fileQuest.writeString(json.prettyPrint(questSystem), false);
    }

    public void update() {

        for (QuestTask questTask : questSystem.getQuestTasks()) {
            if(questTask.getTaskProgress() != QuestTask.TaskProgress.DONE){
                switch (questTask.getQuestType()) {
                    case TALK:
                        Rectangle target = new Rectangle(questTask.getTargetX(), questTask.getTargetnY(), questTask.getWidthRect(), questTask.getHeightRect());
//                        if(player.getRectangle().overlaps(target)) {
//                            System.out.println("Rect");
//                        }
                        break;
                    case KILL:
                        break;
                    case SEARCH:
                        break;
                    case DEFEND:
                        break;
                    case ESCAPE:
                        break;
                    case MASSACRE:
                        break;
                }
            }
        }
    }

    public void render(float delta) {

    }

}
