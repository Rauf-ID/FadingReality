package com.mygdx.game.skills;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.tools.managers.ResourceManager;

import java.util.Hashtable;

public class SkillFactory {

    private static Json json = new Json();
    private static SkillFactory instance = null;
    private Hashtable<Integer, Skill> skillArray;

    public static SkillFactory getInstance() {
        if (instance == null) {
            instance = new SkillFactory();
        }

        return instance;
    }

    private SkillFactory(){
        skillArray = new Hashtable<Integer, Skill>();

        Array<Skill> skills = Skill.getSkillsConfig(ResourceManager.SKILLS_CONFIG);
        for(Skill skill: skills){
            skillArray.put(skill.getId(), skill);
        }
    }

    public Skill getSkill(int id){
        return skillArray.get(id);
    }
}
