package com.mygdx.game.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.component.Player;

import java.util.ArrayList;

public class Skill {
    private int id;
    private int cost;
    private boolean unlocked;
    private SkillType skillType;

    public static enum SkillType{
        ACTIVE,
        DASH,
        HP,
        RUDIMENTCD,
        RANGEDMG,
        MELEEDMG,
        WEAPONSPEED,
        CRITCHANCE,
        HEAL,
        EXECUTION,
        DMGBOOST,
        DMGRESIST
    }

    public Skill(){

    }

    public Skill loadSkill(){
        Skill skill = new Skill();
        return skill;
    }

    private int dashCharges, hp, rudimentCooldown;

    private int rangedDamage, meleeDamage, weaponSpeed, critChance;

    private int heal, execution, damageBoost, damageResist;

    public void unlockSkill(int exp, Player player){
        if(exp>=this.cost && !this.unlocked){
            this.unlocked=true;
            switch (skillType){
                case DASH:
                    player.setMaxDashCharges(player.getMaxDashCharges()+this.getDashCharges());
                    System.out.println("Skill unlocked!" + " Max dash charges increased");
                default:

            }

        }


    }

    public int getDashCharges(){
        return this.dashCharges;
    }
    public void setDashCharges(int dashCharges){this.dashCharges=dashCharges;}

    public int getId(){return this.id;}
    public void setId(int id){this.id=id;}

    public SkillType getType(){return this.skillType;}
    public void setSkillType(SkillType skillType){this.skillType=skillType;}

    public int getSkillProperty(SkillType skillType){
        switch (skillType){
            case DASH:
                return getDashCharges();
            default:
                return 0;
        }
    }

    static public Array<Skill> getSkillsConfig(String configFilePath){
        Json json = new Json();
        Array<Skill> skills = new Array<>();

        ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(configFilePath));

        for (JsonValue jsonVal: list){
            skills.add(json.readValue(Skill.class, jsonVal));
        }

        return skills;
    }


}
