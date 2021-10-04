package com.mygdx.game.tools;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.FadingReality;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.badlogic.gdx.utils.Json;


public class GateMehan {

    private Json json;

    public Sprite currentFrameGateLeft = null;
    public Sprite currentFrameGateRight = null;
    public Sprite currentFrameGateHand = null;

    public float stateTime = 0f;
    public static float xPosLeft, xPosRight, xPosHand;

    public boolean switchDoor = false;

    public int i = 0;

    public Entity entity;


    public GateMehan() {
        json = new Json();

        xPosLeft = 525;
        xPosRight = 704;
        xPosHand = 0;
    }

    public void openGate() {
        switchDoor = true;
    }

    public void closeGate() {
        switchDoor = false;
    }

    public void update(float delta) {
        if (switchDoor) {
            //LEFT DOOR
            if (xPosLeft > 525-100) {
                xPosLeft -= delta*50;
            }
            //RIGHT DOOR
            if (xPosRight < 704+26) {
                xPosRight += delta*15;
                if (xPosRight < 725) {
                    stateTime = 0f;
                } else {
                    xPosHand = 641;
                    if (i == 0) {
                        i++;
                        entity.sendMessage(Message.MESSAGE.ACTIVATE_ANIM_MECHAN, json.toJson(true));
                    }
                }
            } else if (xPosRight < 704+100) {
                xPosHand = 0;
                xPosRight += delta*60;
                i = 0;
            }
        } else {
            if (xPosLeft < 525) {
                xPosLeft += delta*50;
            }
            if (xPosRight > 704) {
                xPosRight -= delta*50;
            }
        }
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void draw(Batch batch, float delta) {
        stateTime += delta;
        currentFrameGateLeft = FadingReality.resourceManager.gateMehanLeft.getKeyFrame(stateTime, true);
        currentFrameGateRight = FadingReality.resourceManager.gateMehanRight.getKeyFrame(stateTime, true);
        currentFrameGateHand = FadingReality.resourceManager.securityMechanismAnimHand.getKeyFrame(stateTime, false);

        batch.begin();
        batch.draw(currentFrameGateLeft, xPosLeft, 517);
        batch.draw(currentFrameGateRight, xPosRight, 517);
        batch.draw(currentFrameGateHand, xPosHand, 530);
        batch.end();


    }

}
