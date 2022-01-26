package com.mygdx.game.UI.pda;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FadingReality;
import com.mygdx.game.UI.InventoryUI;
import com.mygdx.game.UI.PlayerHUD;
import com.mygdx.game.inventory.Item;
import com.mygdx.game.inventory.ItemButton;
import com.mygdx.game.inventory.ItemFactory;

public class BrowserUI extends Group {

    private Array<Item.ItemID> shopItems;

    private int lengthSlotRow = 5;
    private Table start = new Table();
    private Table site1;

    private InventoryUI inventoryUI;

    public BrowserUI(InventoryUI inventoryUI) {
        this.inventoryUI = inventoryUI;

        shopItems = new Array<>();

        Drawable drawableBrowserUI = new TextureRegionDrawable(new TextureRegion(new Texture("textures/UI/BrowserUI.png")));
        Image imageBackground = new Image(drawableBrowserUI);

        TextField txfAddressBar = new TextField("", FadingReality.getUiSkin());
        txfAddressBar.setWidth(imageBackground.getWidth() / 2);
        txfAddressBar.setPosition(imageBackground.getWidth() / 4, imageBackground.getHeight() - 60);

        Drawable drawableSite1 = new TextureRegionDrawable(new TextureRegion(new Texture("textures/UI/BrowserSite1.png")));
        ImageButton imageButtonSite1 = new ImageButton(drawableSite1);
        imageButtonSite1.setPosition(50,0);

        Drawable drawableSite2 = new TextureRegionDrawable(new TextureRegion(new Texture("textures/UI/BrowserSite1.png")));
        ImageButton imageButtonSite2 = new ImageButton(drawableSite2);
        imageButtonSite2.setPosition(imageButtonSite1.getWidth() + 150,0);

        Drawable drawableSite3 = new TextureRegionDrawable(new TextureRegion(new Texture("textures/UI/BrowserSite1.png")));
        ImageButton imageButtonSite3 = new ImageButton(drawableSite3);

        Drawable drawableSite4 = new TextureRegionDrawable(new TextureRegion(new Texture("textures/UI/BrowserSite1.png")));
        ImageButton imageButtonSite4 = new ImageButton(drawableSite4);

        Drawable drawableBack = new TextureRegionDrawable(new TextureRegion(new Texture("textures/UI/BrowserBack.png")));
        ImageButton imageButtonBack = new ImageButton(drawableBack);
        imageButtonBack.setPosition(150,imageBackground.getHeight() - 60);

        Drawable drawableForward = new TextureRegionDrawable(new TextureRegion(new Texture("textures/UI/BrowserForward.png")));
        ImageButton imageButtonForward = new ImageButton(drawableForward);
        imageButtonForward.setPosition(150+34,imageBackground.getHeight() - 60);


        imageButtonSite1.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("PRESSED");
                openSite1();
            }
        });

        imageButtonBack.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("PRESSED");
                site1.clear();
                start.setVisible(true);
            }
        });

        imageButtonForward.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("PRESSED");
            }
        });

        start.setPosition(imageBackground.getWidth() / 2, imageBackground.getHeight() - 250);
        start.add(imageButtonSite1).pad(30);
        start.add(imageButtonSite2).pad(30);
        start.add(imageButtonSite3).pad(30);
        start.add(imageButtonSite4).pad(30);

        this.addActor(imageBackground);
        this.addActor(txfAddressBar);
        this.addActor(imageButtonBack);
        this.addActor(imageButtonForward);
        this.addActor(start);

    }

    private void openSite1() {
        start.setVisible(false);
        site1 = new Table();
        Table tableItems = new Table();
        for (int i = 1; i <= shopItems.size; i++) {
            Table tableItemInfo = new Table();
            final Item item = ItemFactory.getInstance().getInventoryItem(shopItems.get(i-1));
            Label labelPrice = new Label(item.getItemValue() + " T", FadingReality.getUiSkin());
            Label name = new Label(item.getItemID().toString(), FadingReality.getUiSkin());

            TextButton button = new TextButton("Buy", FadingReality.getUiSkin());

            tableItemInfo.add(item).width(128).height(128).pad(20).row();
            tableItemInfo.add(labelPrice).row();
            tableItemInfo.add(name).row();
            tableItemInfo.add(button).width(128).row();

            button.addListener(new ClickListener() {
                @Override
                public void clicked (InputEvent event, float x, float y) {
                    inventoryUI.addItemToInventory(item.getItemID());
                }
            });

            tableItems.add(tableItemInfo).pad(20);
            tableItemInfo.debug();

            if(i % lengthSlotRow == 0){
                tableItems.row();
            }
        }

        ScrollPane scroller = new ScrollPane(tableItems);
        site1.setPosition(200, 0);
        site1.setSize(1340,855);
        site1.add(scroller);
        site1.debug();

        this.addActor(site1);
    }

    public Array<Item.ItemID> getShopItems() {
        return shopItems;
    }

    public void setShopItems(Array<Item.ItemID> shopItems) {
        this.shopItems = shopItems;
    }









    //    public static void addCloseButtonToWindow (final Group window) {
//        TextButton closeButton = new TextButton("x", FadingReality.getUiSkin());
//        closeButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                window.remove();
//                PlayerHUD.pdaUI.setVisible(true);
//                window.setVisible(false);
//                super.clicked(event, x, y);
//            }
//        });
//        window.getTitleTable().add(closeButton);
//    }


}
