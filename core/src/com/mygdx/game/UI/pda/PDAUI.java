package com.mygdx.game.UI.pda;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.FadingReality;
import com.mygdx.game.UI.PlayerHUD;

public class PDAUI extends Window {

    private Label label;
    private ImageButton button, button2, button3, button4, button5, button6;
    private Image image;


    public PDAUI(String title, Skin skin) {
        super(title, skin);


        Drawable drawable = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.texture));
        Drawable drawable2 = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.texture4));

        Drawable backgroundPDA = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureBackgroundPDA));
        Drawable buttonNone = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureButtonNonePDA));
        Drawable tCoinLogo = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureTCoinLogo));

        background(backgroundPDA);

        label = new Label("0", skin);
        label.setColor(0,.7f,1,1);

        image = new Image(tCoinLogo);

        button = new ImageButton(buttonNone);
        button2 = new ImageButton(buttonNone);
        button3 = new ImageButton(buttonNone);
        button4 = new ImageButton(buttonNone);
        button5 = new ImageButton(buttonNone);
        button6 = new ImageButton(buttonNone);


        Table table2 = new Table();
        table2.add(label).padRight(10);
        table2.add(image);
        this.add(table2).right().expandY().top().padTop(65);

        this.row();

        Table table = new Table();
        table.columnDefaults(1).width(150).height(150);
        table.add(button);
        table.add(button2);
        table.add(button3);
        table.row();
        table.add(button4);
        table.add(button5);
        table.add(button6);
//        table.add(button).pad(20,20,20,20);
//        table.add(button2).pad(20,20,20,20);
//        table.add(button3).pad(20,20,20,20);
//        table.row();
//        table.add(button4).pad(20,20,20,20);
//        table.add(button5).pad(20,20,20,20);
//        table.add(button6).pad(20,20,20,20);
        this.add(table).padBottom(150);

        //Listeners
        button.addListener(new ClickListener() {
                               @Override
                               public void clicked (InputEvent event, float x, float y) {
                                   System.out.println("PRESSED");
                                   PlayerHUD.browserUI.setVisible(true);
                                   setVisible(false);
                               }
                           }
        );

        button2.addListener(new ClickListener() {
                                @Override
                                public void clicked (InputEvent event, float x, float y) {
                                    System.out.println("PRESSED2");
                                }
                            }
        );
    }


}
