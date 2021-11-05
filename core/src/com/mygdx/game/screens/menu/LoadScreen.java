package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FadingReality;
import com.mygdx.game.FadingReality.ScreenType;
import com.mygdx.game.profile.ProfileManager;


public class LoadScreen implements Screen {

    private Stage stage;
    private FadingReality game;
    private List listItems;


    public LoadScreen(FadingReality game) {
        this.game=game;
    }

    @Override
    public void show() {
        stage = new Stage();

        TextButton loadButton = new TextButton("Load", FadingReality.getUiSkin());
        loadButton.setPosition(300,100);

        TextButton backButton = new TextButton("Back", FadingReality.getUiSkin());
        backButton.setPosition(500,100);

        ProfileManager.getInstance().storeAllProfiles();
        listItems = new List( FadingReality.getUiSkin());

        Array<String> list = ProfileManager.getInstance().getProfileList();
        listItems.setItems(list);
        ScrollPane scrollPane = new ScrollPane(listItems);
        scrollPane.setPosition(350,200);

        scrollPane.setOverscroll(false, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        Table table = new Table();
        table.setFillParent(true);
        table.add(scrollPane).center();

        Table bottomTable = new Table();
        bottomTable.setHeight(500);
        bottomTable.setWidth(Gdx.graphics.getWidth());
        bottomTable.add(loadButton).padRight(50);
        bottomTable.add(backButton);

        stage.addActor(table);
        stage.addActor(bottomTable);

        //Listeners
        backButton.addListener(new ClickListener() {
               @Override
               public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                   return true;
               }

               @Override
               public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                   game.setScreen(game.getScreenType(ScreenType.Menu));
               }
        });

        loadButton.addListener(new ClickListener() {
               @Override
               public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                   return true;
               }

               @Override
               public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                   if( listItems.getSelected() == null ) {
                       return;
                   }

                   String fileName = listItems.getSelected().toString();

                   if (fileName != null && !fileName.isEmpty()) {
                       FileHandle file = ProfileManager.getInstance().getProfileFile(fileName);
                       if (file != null) {
                           ProfileManager.getInstance().setCurrentProfile(fileName);
                           game.setScreen(game.getScreenType(ScreenType.Game));
                       }
                   }
               }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if( delta == 0){
            return;
        }
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.6f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            if( listItems.getSelected() == null ) {
                return;
            }

            String fileName = listItems.getSelected().toString();

            if (fileName != null && !fileName.isEmpty()) {
                FileHandle file = ProfileManager.getInstance().getProfileFile(fileName);
                if (file != null) {
                    ProfileManager.getInstance().setCurrentProfile(fileName);
                    game.setScreen(game.getScreenType(ScreenType.Game));
                }
            }
        }


        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.clear();
        stage.dispose();
    }
}
