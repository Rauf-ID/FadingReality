package com.mygdx.game.UI.skillUI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FadingReality;
import com.mygdx.game.component.Player;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.skills.Skill;
import com.mygdx.game.skills.SkillFactory;

public class SkillUI extends Window {


    public SkillUI(Entity player) {
        super("Skill UI", FadingReality.getUiSkin());
    }

    public void createSkillTree(Entity player) {
        this.clear();
        Drawable buttonNone = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureButtonNonePDA));

        Array<Integer> allSkills =  SkillFactory.getInstance().getAllSkills();
        System.out.println(allSkills);

        Array<Integer> learnedSkills = Skill.getLearnedSkills(player);
        System.out.println(learnedSkills);
        Array<Integer> accessibleSkills = Skill.getAccessibleSkills(player);
        System.out.println(accessibleSkills);
        Array<Integer> inaccessibleSkills = Skill.getInaccessibleSkills(player);
        System.out.println(inaccessibleSkills);

        for (Skill.RootNode rootNode : Skill.RootNode.values()) {
            Table unified = new Table();
            unified.add();
            ImageButton peculiarities = new ImageButton(buttonNone);
            unified.add(peculiarities).padBottom(30).row();
            for (int i = 0; i < 3; i++) {
                Table tableVertical = new Table();

                for (int j = 0; j < 4; j++) {
                    ImageButton button = new ImageButton(buttonNone);
                    tableVertical.add(button).pad(5).row();

                    Skill skill = SkillFactory.getInstance().getSkill(i);
                    final int finalI = i;
                    button.addListener(new ClickListener() {
                        @Override
                        public void clicked (InputEvent event, float x, float y) {
                            System.out.println("PRESSED " + finalI);
                        }
                    });
                }
                unified.add(tableVertical);
            }
            unified.add().pad(30);
            this.add(unified);
        }
    }

}
