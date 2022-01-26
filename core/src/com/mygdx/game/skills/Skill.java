package com.mygdx.game.skills;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;


public class Skill {

    public enum RootNode {
        ROOT_LEFT,
        ROOT_MIDDLE,
        ROOT_RIGHT,
    }

    public enum BranchPosition {
        BRANCH_LEFT,
        BRANCH_MIDDLE,
        BRANCH_RIGHT
    }

    public enum SkillType {
        HP,
        DASH,
        HEAL,
        ACTIVE,
        RANGE_DMG,
        MELEE_DMG,
        EXECUTION,
        DMG_BOOST,
        DMG_RESIST,
        RUDIMENT_CD,
        CRIT_CHANCE,
        WEAPON_SPEED,
    }

    private RootNode rootNode;
    private BranchPosition branchPosition;
    private int id;
    private int cost;
    private int property;
    private String title;
    private String description;
    private SkillType skillType;
    private Array<Integer> nextSkills;


    public Skill() {}

    public void unlockSkill(Entity player){
        if(player.getAvailableSkills().contains(this.getId(),true) && player.getExperience() >= this.cost && (!player.getPlayerSkills().contains(this.id,true))){
            switch (this.getSkillType()){
                case DASH:
                    player.setMaxDashCharges(player.getMaxDashCharges() + this.getProperty());
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
                case ACTIVE:
                    if(this.getId()==12){
                        player.setDashSpeed(200);
                        player.setDashDist(50);
                    }
            }
            player.getPlayerSkills().add(this.id);
            player.getAvailableSkills().removeValue(this.getId(),true);
            player.setExperience(player.getExperience() - cost);
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
