package com.mygdx.game.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.inventory.Item;
import com.mygdx.game.tools.managers.ResourceManager;

import java.util.ArrayList;
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
        ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(ResourceManager.SKILLS_CONFIG));
        skillArray = new Hashtable<>();

        for (JsonValue jsonVal : list) {
            Skill skill = json.readValue(Skill.class, jsonVal);
            skillArray.put(skill.getId(), skill);
        }
    }

    public Skill getSkill(int id){
        return skillArray.get(id);
    }

    public Array<Integer> getAllSkillsID() {
        Array<Integer> skillIDs = new Array<>();
        for (Skill skill: skillArray.values()) {
            skillIDs.add(skill.getId());
        }
        return skillIDs;
    }

    public Array<Skill> getAllSkills(){
        Array<Skill> skills = new Array<>();
        for (Skill skill: skillArray.values()) {
            skills.add(skill);
        }
        return skills;
    }
}
