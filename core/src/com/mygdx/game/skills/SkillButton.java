package com.mygdx.game.skills;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.skills.Skill;

public class SkillButton extends ImageButton {

    private Skill skill;

    public SkillButton(Drawable drawable) {
        super(drawable);
    }

    public SkillButton(Drawable drawable, Skill skill) {
        super(drawable);

        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
