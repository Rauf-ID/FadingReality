package com.mygdx.game.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.component.Player;
import com.mygdx.game.entity.Entity;

import java.util.ArrayList;

public class Skill {
    private int id, cost, property;
    private SkillType skillType;
    private RootNode rootNode;
    private BranchPosition branchPosition;
    private String description;


    private Array<Integer> nextSkills;

    public enum SkillType{
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

    public enum RootNode {
        BRANCH_RIGHT,
        BRANCH_MIDDLE,
        BRANCH_LEFT,
    }
    public enum BranchPosition{
        POS_LEFT,
        POS_MIDDLE,
        POS_RIGHT
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
        if(player.getAvailableSkills().contains(this.getId(),true) && exp>=this.cost && (!player.getPlayerSkills().contains(this.id,true))){
            switch (this.getType()){
                case DASH:
                    player.setMaxDashCharges(player.getMaxDashCharges()+this.getSkillProperty());
                    break;
                case DMGBOOST:
                    player.setDamageBoost(player.getDamageBoost() + this.getSkillProperty());
                    break;
                case MELEEDMG:
                    player.setMeleeDamageBoost(player.getMeleeDamageBoost() + this.getSkillProperty());
                    break;
                case RANGEDMG:
                    player.setRangedDamageBoost(player.getRangedDamageBoost() + this.getSkillProperty());
                    break;
                case DMGRESIST:
                    player.setDamageResist(player.getDamageResist() + this.getSkillProperty());
                    break;
                case WEAPONSPEED:
                    player.setWeaponSpeed(player.getWeaponSpeed() + this.getSkillProperty());
                    break;
                case HP:
                    player.setMaxHealth(player.getMaxHealth() + this.getSkillProperty());
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
            player.getPlayerSkills().add(this.id);
            player.getAvailableSkills().removeValue(this.getId(),true);
            for (int nextSkill: nextSkills){
                player.getAvailableSkills().add(nextSkill);
            }
        }
    }

    public static Array<Integer> getInaccessibleSkills(Entity player){
        Array<Integer> inaccessibleSkills =  SkillFactory.getInstance().getAllSkills();
        for(int skill: player.getAvailableSkills()){
            inaccessibleSkills.removeValue(skill,true);
        }
        for(int skill: player.getPlayerSkills()){
            inaccessibleSkills.removeValue(skill,true);
        }
        return inaccessibleSkills;
    }

    public static Array<Integer> getAccessibleSkills(Entity player){
        return player.getAvailableSkills();
    }

    public static Array<Integer> getLearnedSkills(Entity player){
        return player.getPlayerSkills();
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

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Array<Integer> getNextSkills() {
        return nextSkills;
    }

    public void setNextSkills(Array<Integer> nextSkills) {
        this.nextSkills = nextSkills;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getProperty() {
        return property;
    }
    public void setProperty(int property) {
        this.property = property;
    }

    public SkillType getSkillType() {
        return skillType;
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
