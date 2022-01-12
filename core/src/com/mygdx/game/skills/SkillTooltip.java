package com.mygdx.game.skills;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.game.FadingReality;

public class SkillTooltip extends Window {

    private Label description;

    public SkillTooltip(){
        super("", FadingReality.getUiSkin());

        description = new Label("", FadingReality.getUiSkin());

        this.add(description);
        this.padLeft(5).padRight(5);
        this.pack();
        this.setVisible(false);
    }

    public void setVisible(SkillButton skillButton, boolean visible) {
        if( skillButton == null ){
            return;
        }
        super.setVisible(visible);
    }

    public void updateDescription(SkillButton skillButton){
        if( skillButton != null ){
            StringBuilder string = new StringBuilder();
            Skill skill = skillButton.getSkill();
            string.append(skill.getDescription());

//            if(skill.isInventoryItemOffensiveMelee()) {
//                string.append(System.getProperty("line.separator"));
//                string.append(String.format("Attack Points: %s", skill.getItemUseTypeValue()));
//            }

            description.setText(string);
            this.pack();
        } else{
            description.setText("");
            this.pack();
        }
    }

}
