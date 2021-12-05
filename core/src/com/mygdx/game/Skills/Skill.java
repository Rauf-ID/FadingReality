package com.mygdx.game.Skills;

public class Skill {
    private int id;
    private int cost;
    private boolean unlocked;
    private Skill previousSkill;


    private int dashCharges, hp, rudimentCooldown;

    private int rangedDamage, meleeDamage, weaponSpeed, critChance;

    private int heal, execution, damageBoost, damageResist;

    public void unlockSkill(){
        //if(player.getExp == this.cost && previous.skill.unlocked)
        this.unlocked=true;
    }




}
