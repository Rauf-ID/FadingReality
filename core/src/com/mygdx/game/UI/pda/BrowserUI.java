package com.mygdx.game.UI.pda;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.FadingReality;
import com.mygdx.game.UI.PlayerHUD;

public class BrowserUI extends Window {

    public BrowserUI(String title, Skin skin) {
        super(title, skin);

        addCloseButtonToWindow(this);


    }

    public static void addCloseButtonToWindow (final Window window) {
        TextButton closeButton = new TextButton("x", FadingReality.getUiSkin());
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                window.remove();
                PlayerHUD.pdaui.setVisible(true);
                window.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        window.getTitleTable().add(closeButton);
    }


}
