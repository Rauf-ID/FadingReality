package com.mygdx.game.UI.skillUI;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.skills.Skill;

public class SkillButton extends ImageButton {

    private Label skillLabel;
    private int id;

    public SkillButton(Skin skin, Skill skill) {
        super(skin);
        skillLabel.setText(skill.getDescription());
        this.id=skill.getId();
    }

    public SkillButton(Skin skin) {
        super(skin);
    }

    public SkillButton(Skin skin, String styleName) {
        super(skin, styleName);
    }

    public SkillButton(ImageButtonStyle style) {
        super(style);
    }

    public SkillButton(Drawable imageUp) {
        super(imageUp);
    }

    public SkillButton(Drawable imageUp, Drawable imageDown) {
        super(imageUp, imageDown);
    }

    public SkillButton(Drawable imageUp, Drawable imageDown, Drawable imageChecked) {
        super(imageUp, imageDown, imageChecked);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
