package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.FadingReality;
import com.mygdx.game.profile.ProfileManager;
import com.mygdx.game.tools.managers.ResourceManager;
import com.mygdx.game.FadingReality.ScreenType;

public class MenuScreen implements Screen {

    private FadingReality game;
    private ResourceManager rm;
    private Stage stage;


    public MenuScreen(FadingReality game, ResourceManager rm) {
        this.game=game;
        this.rm=rm;

        ProfileManager.getInstance().loadSetting();
    }

    @Override
    public void show() {
        stage = new Stage();

        Drawable drawableMenuBackground = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureMenuBackground));

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(drawableMenuBackground);

        TextButton continueGameButton = new TextButton("CONTINUE GAME", FadingReality.getUiSkin());
        continueGameButton.setPosition(300,500);
        if(ProfileManager.getInstance().getSettingsConfig().getLastActiveAccount().isEmpty()) {
            continueGameButton.setVisible(false);
        }

        TextButton loadGameButton = new TextButton("LOAD GAME", FadingReality.getUiSkin());
        loadGameButton.setPosition(300,450);

        TextButton newGameButton = new TextButton("NEW GAME", FadingReality.getUiSkin());
        newGameButton.setPosition(300,400);

        TextButton settingsGameButton = new TextButton("SETTINGS", FadingReality.getUiSkin());
        settingsGameButton.setPosition(300,350);

        TextButton creditsGameButton = new TextButton("CREDITS", FadingReality.getUiSkin());
        creditsGameButton.setPosition(300,300);

        TextButton exitGameButton = new TextButton("EXIT GAME", FadingReality.getUiSkin());
        exitGameButton.setPosition(300,250);

//        Image imageMenuBackground = new Image(new TextureRegion());
//        imageMenuBackground.setPosition(0,0);


        table.add(continueGameButton).spaceBottom(40).row();
        table.add(loadGameButton).spaceBottom(40).row();
        table.add(newGameButton).spaceBottom(40).row();
        table.add(settingsGameButton).spaceBottom(40).row();
        table.add(creditsGameButton).spaceBottom(40).row();
        table.add(exitGameButton).spaceBottom(40).row();

        stage.addActor(table);
//        stage.addActor(imageMenuBackground);
//        stage.addActor(continueGameButton);
//        stage.addActor(loadGameButton);
//        stage.addActor(newGameButton);
//        stage.addActor(settingsGameButton);
//        stage.addActor(creditsGameButton);
//        stage.addActor(exitGameButton);


        //Listeners
        continueGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                String fileName = ProfileManager.getInstance().getSettingsConfig().getLastActiveAccount();
                if (fileName != null && !fileName.isEmpty()) {
                    FileHandle file = ProfileManager.getInstance().getProfileFile(fileName);
                    if (file != null) {
                        ProfileManager.getInstance().setCurrentProfile(fileName);
                        game.setScreen(game.getScreenType(ScreenType.Game));
                    }
                }
            }
        });


        loadGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.getScreenType(ScreenType.LoadGame));
            }
        });

        newGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.getScreenType(ScreenType.NewAccountGame));
            }
        });

        settingsGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(0.6f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(game.getScreenType(ScreenType.Settings));
                    }
                })));
            }
        });

        creditsGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(0.6f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(game.getScreenType(ScreenType.Credits));
                    }
                })));
            }
        });

        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.6f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            String fileName = ProfileManager.getInstance().getSettingsConfig().getLastActiveAccount();
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
        stage.getViewport().setScreenSize(width, height);
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
        stage.dispose();
    }
}
