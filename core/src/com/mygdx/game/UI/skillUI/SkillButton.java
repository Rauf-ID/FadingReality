package com.mygdx.game.UI.skillUI;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.skills.Skill;

public class SkillButton extends ImageButton {


    public SkillButton(Skin skin, Skill skill) {
        super(skin);
    }

    public void mark(int style) {
        switch (style) {
            case 1:
                System.out.println("1change color");
                break;
            case 2:
                System.out.println("2change color");
                break;
            case 3:
                System.out.println("3change color");
                break;
        }
    }
}
