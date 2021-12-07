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
    private int property;

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

    /*private int dashCharges, hp, rudimentCooldown;

    private int rangedDamage, meleeDamage, weaponSpeed, critChance;

    private int heal, execution, damageBoost, damageResist;*/

    public void unlockSkill(int exp, Player player){
        if(exp>=this.cost && !this.unlocked){
            this.unlocked=true;
            switch (this.getType()){
                case DASH:
                    player.setMaxDashCharges(player.getMaxDashCharges()+this.getSkillProperty());
                    System.out.println("Skill unlocked!" + " Max dash charges increased");
                    break;
                case DMGBOOST:
                    player.setDamageBoost(player.getDamageBoost() + this.getSkillProperty());
                    System.out.println("Skill unlocked!" + this.getType());
                    break;
                case MELEEDMG:
                    player.setMeleeDamageBoost(player.getMeleeDamageBoost() + this.getSkillProperty());
                    System.out.println("Skill unlocked!" + this.getType());
                    break;
                case RANGEDMG:
                    player.setRangedDamageBoost(player.getRangedDamageBoost() + this.getSkillProperty());
                    System.out.println("Skill unlocked!" + this.getType());
                    break;
                case DMGRESIST:
                    player.setDamageResist(player.getDamageResist() + this.getSkillProperty());
                    System.out.println("Skill unlocked!" + this.getType());
                    break;
                case WEAPONSPEED:
                    player.setWeaponSpeed(player.getWeaponSpeed() + this.getSkillProperty());
                    System.out.println("Skill unlocked!" + this.getType());
                    break;
                case HP:
                    player.setMaxHealth(player.getMaxHealth() + this.getSkillProperty());
                    System.out.println("Skill unlocked!" + this.getType());
                    break;
                case ACTIVE:
                    break;
                case HEAL:
                    player.setHealAmount(player.getHealAmount() + this.getSkillProperty());
                    break;
                case EXECUTION:
                    player.setExecutionThreshold(player.getExecutionThreshold() + this.getSkillProperty());
                    break;
                case CRITCHANCE:
                    player.setCritChanсe(player.getCritChanсe() + this.getSkillProperty());
                    break;
                case RUDIMENTCD:
                    player.setRudimentCooldown(player.getRudimentCooldown() + this.getSkillProperty());
                    break;
            }

        }


    }



    public int getId(){return this.id;}
    public void setId(int id){this.id=id;}

    public SkillType getType(){return this.skillType;}
    public void setSkillType(SkillType skillType){this.skillType=skillType;}

    public int getSkillProperty(){
        return property;
    }
    public void setSkillProperty(int property){
        this.property=property;
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
