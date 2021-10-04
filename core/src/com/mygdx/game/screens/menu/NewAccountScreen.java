package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.FadingReality;
import com.mygdx.game.FadingReality.ScreenType;
import com.mygdx.game.profile.ProfileManager;

public class NewAccountScreen implements Screen {

    private Stage stage;
    private FadingReality game;
    private TextField profileText;
    private Dialog overwriteDialog;


    public NewAccountScreen(FadingReality game) {
        this.game=game;
    }

    @Override
    public void show() {
        stage = new Stage();

        profileText  = new TextField("", FadingReality.getUiSkin());
        profileText.setMaxLength(20);

        TextButton startButton = new TextButton("Start", FadingReality.getUiSkin());
        TextButton backButton = new TextButton("Back", FadingReality.getUiSkin());

        overwriteDialog = new Dialog("Overwrite?", FadingReality.getUiSkin());
        Label overwriteLabel = new Label("Overwrite existing profile name?", FadingReality.getUiSkin());
        TextButton cancelButton = new TextButton("Cancel", FadingReality.getUiSkin());
        TextButton overwriteButton = new TextButton("Overwrite", FadingReality.getUiSkin());
        overwriteDialog.setKeepWithinStage(true);
        overwriteDialog.setModal(true);
        overwriteDialog.setMovable(false);
        overwriteDialog.text(overwriteLabel);
        overwriteDialog.row();
        overwriteDialog.button(overwriteButton).bottom().left();
        overwriteDialog.button(cancelButton).bottom().right();
        overwriteDialog.hide();

        Table table = new Table();
        table.setFillParent(true);
        table.add(profileText).center();

        Table bottomTable = new Table();
        bottomTable.setHeight(500);
        bottomTable.setWidth(Gdx.graphics.getWidth());
        bottomTable.add(startButton).padRight(50);
        bottomTable.add(backButton);

        stage.addActor(table);
        stage.addActor(bottomTable);


        startButton.addListener(new ClickListener() {
                                    @Override
                                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button ){
                                        return true;
                                    }

                                    @Override
                                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                                        String messageText = profileText.getText();
                                        boolean exists = false;

                                        exists = ProfileManager.getInstance().doesProfileExist(messageText);

                                        if( exists ){
                                            overwriteDialog.show(stage);
                                        }else{
                                            ProfileManager.getInstance().writeProfileToStorage(messageText, "", false);
                                            ProfileManager.getInstance().setCurrentProfile(messageText);
                                            ProfileManager.getInstance().setIsNewProfile(true);
                                            game.setScreen(game.getScreenType(ScreenType.Game));
                                        }
                                    }
                                }
        );

        backButton.addListener(new ClickListener() {
                                   @Override
                                   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                       return true;
                                   }

                                   @Override
                                   public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                                       game.setScreen(game.getScreenType(ScreenType.Menu));
                                   }
                               }
        );

        overwriteButton.addListener(new ClickListener() {
                                        @Override
                                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                            return true;
                                        }

                                        @Override
                                        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                                            String messageText = profileText.getText();
                                            ProfileManager.getInstance().writeProfileToStorage(messageText, "", true);
                                            ProfileManager.getInstance().setCurrentProfile(messageText);
                                            ProfileManager.getInstance().setIsNewProfile(true);
                                            overwriteDialog.hide();
                                            game.setScreen(game.getScreenType(ScreenType.Game));
                                        }

                                    }
        );

        //Listeners
        cancelButton.addListener(new ClickListener() {

                                     @Override
                                     public boolean touchDown(InputEvent event, float x, float y, int pointer, int button ){
                                         return true;
                                     }

                                     @Override
                                     public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                                         overwriteDialog.hide();
                                     }
                                 }
        );

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if( delta == 0){
            return;
        }

        Gdx.gl.glClearColor(0.294f, 0.294f, 0.6f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        overwriteDialog.hide();
        profileText.setText("");
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.clear();
        stage.dispose();
        System.out.println("ASDa");
    }
}
