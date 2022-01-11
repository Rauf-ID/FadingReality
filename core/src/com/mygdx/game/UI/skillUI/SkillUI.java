package com.mygdx.game.UI.skillUI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.FadingReality;

public class SkillUI extends Window {


    public SkillUI() {
        super("Skill UI", FadingReality.getUiSkin());

        Drawable buttonNone = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureButtonNonePDA));

        for (int k = 0; k < 3; k++) {
            Table table1 = new Table();
            table1.add();
            ImageButton peculiarities = new ImageButton(buttonNone);
            table1.add(peculiarities).padBottom(30).row();
            for (int i = 0; i < 3; i++) {
                Table tableVert = new Table();
                for (int j = 0; j < 4; j++) {
                    ImageButton button = new ImageButton(buttonNone);
                    tableVert.add(button).pad(5).row();
                }
                table1.add(tableVert);
            }
            table1.add().pad(30);
            this.add(table1);
        }

    }
}
