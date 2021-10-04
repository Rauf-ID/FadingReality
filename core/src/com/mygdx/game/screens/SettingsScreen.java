package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.FadingReality;
import com.mygdx.game.tools.managers.ResourceManager;

public class SettingsScreen implements Screen {

    private FadingReality game;
    private ResourceManager rm;
    private Stage uiStage;

    private TextButton languageButtonOne, languageButtonTwo;


    public SettingsScreen(FadingReality game, ResourceManager rm) {
        this.game=game;
        this.rm=rm;
    }

    @Override
    public void show() {
        uiStage = new Stage();

        languageButtonOne = new TextButton("<-", FadingReality.getUiSkin());
        languageButtonTwo = new TextButton("->", FadingReality.getUiSkin());

        languageButtonOne.setPosition(100,100);
        languageButtonOne.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        languageButtonTwo.setPosition(150,100);
        languageButtonTwo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        uiStage.addActor(languageButtonOne);
        uiStage.addActor(languageButtonTwo);
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.6f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(FadingReality.menuScreen);
        }
        uiStage.act(delta);
        uiStage.draw();
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
