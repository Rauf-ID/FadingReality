package com.mygdx.game._oldClasses;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.FadingReality;
import com.mygdx.game._oldClasses.AmmoOld;
import com.mygdx.game.entity.Entity;

import java.util.ArrayList;
import java.util.Iterator;

public class Gun {

    public float angle;
    public Vector3 pos;
    public float ammoCount;
    public ArrayList<AmmoOld> activeAmmoOld;
    public float stateTime = 0f;
    public Sprite currentFrame = null;

    public Entity.Direction playerDirection;
    public float xPos, yPos;


    public Gun(){
        pos = new Vector3();
        activeAmmoOld = new ArrayList<>();
    }

    public void updatePos(float x, float y){
        this.pos.x = x;
        this.pos.y = y;
    }

    public void tick(float delta){
        for(AmmoOld a : activeAmmoOld){
            a.tick(delta);
        }
    }

    public void addActiveAmmo(AmmoOld a){
        if(ammoCount > 0){
            activeAmmoOld.add(a);
            ammoCount--;
        } else {
            System.out.println("Clink");
        }
    }

    public void clearTravelledAmmo() {
        Iterator<AmmoOld> it = activeAmmoOld.iterator();
        while(it.hasNext()) {
            AmmoOld a = it.next();
            if(a.remove){
                it.remove();
            }
        }
    }

    public void drawRotatedGun(SpriteBatch batch, float delta){
        stateTime += delta;
        currentFrame = FadingReality.resourceManager.playerRifleAnimRight.getKeyFrame(stateTime, true);

        if(angle > 90 && angle < 270){ // 6 to 12 Clockwise or LEFT
            currentFrame.setFlip(false, true);
        } else { // 12 to 6 Clockwise or RIGHT
            currentFrame.setFlip(false, false);
            xPos = -20;
        }

//        if (playerDirection==Entity.Direction.UP) {
//            yPos = -10;
//        } else {
//            yPos = 0;
//        }


        batch.begin();
        batch.draw(currentFrame, pos.x+xPos+64+13, pos.y+yPos+64+10, 1, 6.5f, 38, 13, 1, 1, angle);
//        batch.draw(currentFrame, pos.x, pos.y, 0, currentFrame.getHeight() / 2, currentFrame.getWidth(), currentFrame.getHeight(), 1, 1, angle);
        batch.end();
    }

    public void drawAmmo(SpriteBatch batch, float delta) {
        batch.begin();
        for(AmmoOld a : activeAmmoOld){
            a.draw(batch, delta);
        }
        batch.end();
    }




    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setPlayerDirection(Entity.Direction playerDirection) {
        this.playerDirection = playerDirection;
    }

    public void addAmmo(int count) {
        ammoCount += count;
    }

    public float getAmmoCount() {
        return ammoCount;
    }

}
