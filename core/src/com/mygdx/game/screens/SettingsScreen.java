package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.FadingReality;
import com.mygdx.game.profile.ProfileManager;
import com.mygdx.game.managers.ResourceManager;

public class SettingsScreen implements Screen {

    private Stage stage;
    private FadingReality game;
    private ResourceManager rm;


    public SettingsScreen(FadingReality game, ResourceManager rm) {
        this.game = game;
        this.rm = rm;
    }

    @Override
    public void show() {
        stage = new Stage();

        TextButton saveButton = new TextButton("Save", FadingReality.getUiSkin());
        TextButton backButton = new TextButton("Back", FadingReality.getUiSkin());

        Table table = new Table();
        table.setFillParent(true);

        Table bottomTable = new Table();
        bottomTable.setHeight(500);
        bottomTable.setWidth(Gdx.graphics.getWidth());
        bottomTable.add(saveButton).padRight(50);
        bottomTable.add(backButton);

        stage.addActor(table);
        stage.addActor(bottomTable);

        //Listeners
        saveButton.addListener(new ClickListener() {
               @Override
               public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                   return true;
               }

               @Override
               public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                   ProfileManager.getInstance().saveSetting();
               }
        });

        backButton.addListener(new ClickListener() {
               @Override
               public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                   return true;
               }

               @Override
               public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                   game.setScreen(game.getScreenType(FadingReality.ScreenType.Menu));
               }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.6f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(FadingReality.menuScreen);
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

    }

    @Override
    public void dispose() {

    }
}
