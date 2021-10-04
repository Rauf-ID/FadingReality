package com.mygdx.game.tools;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.FadingReality;

public class LightClub {

    public static Sprite lightTexture;

    public Sprite currentFrameLightRed = null;
    public Sprite currentFrameLightGreen = null;

    public float stateTime = 0f;
    public float xPos, yPos;
    public float defaultAngle, startAngle, finishAngle;

    public boolean sheesh1 = true;
    public boolean sheesh2 = false;

    public LightClub(int xPos, int yPos, float defaultAngle, float startAngle, float finishAngle, String color) {
//        lightRedTexture = new Texture("gunTEST.png");

        this.xPos = xPos;
        this.yPos = yPos;
        this.defaultAngle = defaultAngle;
        this.startAngle = startAngle;
        this.finishAngle = finishAngle;

//        switch (color) {
//            case "RED":
//                lightTexture = NonameGame.resourceManager.lightClubRed;
//                break;
//            case "GREEN":
//                lightTexture = NonameGame.resourceManager.lightClubGreen;
//                break;
//        }
    }

    public void update() {
        if (sheesh2 && defaultAngle >= startAngle) {
            defaultAngle -= 0.15;
        } else {
            sheesh1 = true;
            sheesh2 = false;
        }
        if (sheesh1 && defaultAngle <= finishAngle) {
            defaultAngle += 0.15;
        } else {
            sheesh1 = false;
            sheesh2 = true;
        }
    }

    public void draw(Batch batch, float delta, String color) {
        switch (color) {
            case "RED":
                lightTexture = FadingReality.resourceManager.lightClubRed;
                break;
            case "GREEN":
                lightTexture = FadingReality.resourceManager.lightClubGreen;
                lightTexture.flip(true,false);
                break;
        }
        batch.begin();
        batch.draw(lightTexture, xPos, yPos, lightTexture.getRegionWidth()/2f, 1, lightTexture.getWidth(), lightTexture.getHeight(), 1, 1, defaultAngle);
        batch.end();
    }

    }
