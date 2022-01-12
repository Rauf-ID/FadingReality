package com.mygdx.game.skills;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.component.Player;
import com.mygdx.game.entity.Entity;


public class Skill {

    public enum RootNode {
        ROOT_LEFT,
        ROOT_MIDDLE,
        ROOT_RIGHT,
    }

    public enum BranchPosition{
        BRANCH_LEFT,
        BRANCH_MIDDLE,
        BRANCH_RIGHT
    }

    public enum SkillType{
        ACTIVE,
        DASH,
        HP,
        RUDIMENT_CD,
        RANGE_DMG,
        MELEE_DMG,
        WEAPON_SPEED,
        CRIT_CHANCE,
        HEAL,
        EXECUTION,
        DMG_BOOST,
        DMG_RESIST
    }

    private RootNode rootNode;
    private BranchPosition branchPosition;
    private int id, cost, property;
    private SkillType skillType;
    private String title,description;
    private Array<Integer> nextSkills;

    /*private int dashCharges, hp, rudimentCooldown;
    private int rangedDamage, meleeDamage, weaponSpeed, critChance;
    private int heal, execution, damageBoost, damageResist;*/

    public Skill() {}

    public Skill loadSkill() {
        Skill skill = new Skill();
        return skill;
    }

    public void unlockSkill(int exp, Entity player){
        if(player.getAvailableSkills().contains(this.getId(),true) && exp>=this.cost && (!player.getPlayerSkills().contains(this.id,true))){
            switch (this.getSkillType()){
                case DASH:
                    player.setMaxDashCharges(player.getMaxDashCharges()+this.getProperty());
                    break;
                case DMG_BOOST:
                    player.setDamageBoost(player.getDamageBoost() + this.getProperty());
                    break;
                case MELEE_DMG:
                    player.setMeleeDamageBoost(player.getMeleeDamageBoost() + this.getProperty());
                    break;
                case RANGE_DMG:
                    player.setRangedDamageBoost(player.getRangedDamageBoost() + this.getProperty());
                    break;
                case DMG_RESIST:
                    player.setDamageResist(player.getDamageResist() + this.getProperty());
                    break;
                case WEAPON_SPEED:
                    player.setWeaponSpeed(player.getWeaponSpeed() + this.getProperty());
                    break;
                case HP:
                    player.setMaxHealth(player.getMaxHealth() + this.getProperty());
                    break;
                case ACTIVE:
                    break;
                case HEAL:
                    player.setHealAmount(player.getHealAmount() + this.getProperty());
                    break;
                case EXECUTION:
                    player.setExecutionThreshold(player.getExecutionThreshold() + this.getProperty());
                    break;
                case CRIT_CHANCE:
                    player.setCritChanсe(player.getCritChanсe() + this.getProperty());
                    break;
                case RUDIMENT_CD:
                    player.setRudimentCooldown(player.getRudimentCooldown() + this.getProperty());
                    break;
            }
            player.getPlayerSkills().add(this.id);
            player.getAvailableSkills().removeValue(this.getId(),true);
            for (int nextSkill: nextSkills){
                player.getAvailableSkills().add(nextSkill);
            }
        }
    }

    public static Array<Integer> getLearnedSkills(Entity player){
        return player.getPlayerSkills();
    }

    public static Array<Integer> getAccessibleSkills(Entity player){
        return player.getAvailableSkills();
    }

    public static Array<Integer> getInaccessibleSkills(Entity player){
        Array<Integer> inaccessibleSkills = SkillFactory.getInstance().getAllSkillsID();
        for(int skill: player.getAvailableSkills()){
            inaccessibleSkills.removeValue(skill,true);
        }
        for(int skill: player.getPlayerSkills()){
            inaccessibleSkills.removeValue(skill,true);
        }
        return inaccessibleSkills;
    }


    public RootNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    public BranchPosition getBranchPosition() {
        return branchPosition;
    }

    public void setBranchPosition(BranchPosition branchPosition) {
        this.branchPosition = branchPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setSkillType(SkillType skillType) {
        this.skillType = skillType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Array<Integer> getNextSkills() {
        return nextSkills;
    }

    public void setNextSkills(Array<Integer> nextSkills) {
        this.nextSkills = nextSkills;
    }


}
