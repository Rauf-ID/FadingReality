package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.managers.ResourceManager;
import com.mygdx.game.screens.menu.CreditScreen;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.menu.LoadScreen;
import com.mygdx.game.screens.menu.MenuScreen;
import com.mygdx.game.screens.menu.NewAccountScreen;
import com.mygdx.game.screens.PauseScreen;
import com.mygdx.game.screens.SettingsScreen;
import com.mygdx.game.tools.SplashWorker;

public class FadingReality extends Game {

    public enum ScreenType{
        MENU,
        GAME,
        LOAD_GAME,
        NEW_ACCOUNT_GAME,
        SETTINGS,
        CREDITS,
        GAME_OVER,
        PAUSE,
        NONE
    }

    public static final int WIDTH = 1152;  //480  //1152   //1024  //1920  //3840
    public static final int HEIGHT = 896;  //270  //896   //768   //1080  //2160
    private static final String VERSION = "0.0.5";
    public static final String TITLE = "Fading Reality " + VERSION;

    private static MenuScreen menuScreen;
    private static GameScreen gameScreen;
    private static LoadScreen loadScreen;
    private static NewAccountScreen newAccountScreen;
    private static SettingsScreen settingsScreen;
    private static CreditScreen creditScreen;
    private static GameOverScreen gameOverScreen;
    private static PauseScreen pauseScreen;

    public static ResourceManager resourceManager;
    public static BitmapFont font;
    public static Skin skin;
    private SplashWorker splashWorker;
    private SpriteBatch batch;
    private Pixmap pm;

    public FadingReality() { }

    @Override
    public void create() {
        resourceManager = new ResourceManager();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        batch = new SpriteBatch();
        pm = new Pixmap(Gdx.files.internal("textures/mouse3.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        splashWorker.closeSplashScreen();

        menuScreen = new MenuScreen(this, resourceManager);
        gameScreen = new GameScreen(this);
        loadScreen = new LoadScreen(this);
        newAccountScreen = new NewAccountScreen(this);
        settingsScreen = new SettingsScreen(this, resourceManager);
        creditScreen = new CreditScreen(this, resourceManager);
        gameOverScreen = new GameOverScreen(this, resourceManager);
        pauseScreen = new PauseScreen(this, resourceManager);

        this.setScreen(menuScreen);
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
        menuScreen.dispose();
        settingsScreen.dispose();
        resourceManager.dispose();
        batch.dispose();
        font.dispose();
        pm.dispose();
    }


    public void setSplashWorker(SplashWorker splashWorker) {
        this.splashWorker = splashWorker;
    }

    public static Skin getUiSkin() {
        return skin;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Screen getScreenType(ScreenType screenType){
        switch(screenType){
            case GAME:
                return gameScreen;
            case LOAD_GAME:
                return loadScreen;
            case NEW_ACCOUNT_GAME:
                return newAccountScreen;
            case SETTINGS:
                return settingsScreen;
            case CREDITS:
                return creditScreen;
            case GAME_OVER:
                return gameOverScreen;
            case PAUSE:
                return pauseScreen;
            default:
                return menuScreen;
        }
    }

}