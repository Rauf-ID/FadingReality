package com.mygdx.game.UI.skillUI;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.component.Player;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.skills.Skill;
import com.mygdx.game.skills.SkillFactory;

public class SkillTree {

    Array<SkillButton> skillButtons;
    SkillFactory skillFactory;
    Entity player;

    public SkillTree(Entity player){
        this.skillFactory = SkillFactory.getInstance();
        this.player=player;
    }

    public void createSkillButtons(Skin skin){
        Array<Integer> allSkills =  skillFactory.getAllSkills();
        for(Integer i:allSkills){
            skillButtons.add(new SkillButton(skin, skillFactory.getSkill(i)));
        }
    }

    public void markSkillButtons(){
//        Array<Integer> inaccessibleSkills = Skill.getInaccessibleSkills(player);
//        Array<Integer> accessibleSkills = Skill.getAccessibleSkills(player);
//        Array<Integer> learnedSkills = Skill.getLearnedSkills(player);
//
//        for (int i:inaccessibleSkills){
//            skillButtons.get(i).mark(1);
//        }
//        for (int i:accessibleSkills){
//            skillButtons.get(i).mark(2);
//        }
//        for (int i:learnedSkills){
//            skillButtons.get(i).mark(3);
//        }
    }

}
