package com.mygdx.game.UI.pda;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.FadingReality;
import com.mygdx.game.UI.PlayerHUD;

public class BrowserUI extends Group {

    public BrowserUI() {

        Drawable drawableBrowserUI = new TextureRegionDrawable(new TextureRegion(new Texture("BrowserUI.png")));
        Image imageBackground = new Image(drawableBrowserUI);

        TextField txfAddressBar = new TextField("", FadingReality.getUiSkin());
        txfAddressBar.setWidth(imageBackground.getWidth() / 2);
        txfAddressBar.setPosition(imageBackground.getWidth() / 4, imageBackground.getHeight() - 60);

        Drawable drawableSite1 = new TextureRegionDrawable(new TextureRegion(new Texture("BrowserSite1.png")));
        ImageButton imageButtonSite1 = new ImageButton(drawableSite1);
        imageButtonSite1.setPosition(50,0);

        Drawable drawableSite2 = new TextureRegionDrawable(new TextureRegion(new Texture("BrowserSite1.png")));
        ImageButton imageButtonSite2 = new ImageButton(drawableSite2);
        imageButtonSite2.setPosition(imageButtonSite1.getWidth() + 150,0);


        imageButtonSite1.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("PRESSED");
            }
        });

        imageButtonSite2.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("PRESSED");
            }
        });


        this.addActor(imageBackground);
        this.addActor(txfAddressBar);
        this.addActor(imageButtonSite1);
        this.addActor(imageButtonSite2);
    }

//    public static void addCloseButtonToWindow (final Group window) {
//        TextButton closeButton = new TextButton("x", FadingReality.getUiSkin());
//        closeButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                window.remove();
//                PlayerHUD.pdaui.setVisible(true);
//                window.setVisible(false);
//                super.clicked(event, x, y);
//            }
//        });
//        window.getTitleTable().add(closeButton);
//    }


}
