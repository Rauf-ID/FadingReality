package com.mygdx.game.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.FadingReality;
import com.mygdx.game.entity.Entity;

import java.util.ArrayList;
import java.util.Iterator;

public class Gun {

    public float angle;

    public float xPos, yPos;
    public Vector3 pos;

    public float ammoCount;
    public ArrayList<AmmoOld> activeAmmoOld;

    protected Sprite currentFrame = null;
    protected float stateTime = 0f;
    protected Entity.Direction playerDirection;


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

    public void addAmmo(int count) {
        ammoCount += count;
    }

    public float getAmmoCount() {
        return ammoCount;
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

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setPlayerDirection(Entity.Direction playerDirection) {
        this.playerDirection = playerDirection;
    }

    public void drawRotatedGun(SpriteBatch batch, float delta){
        stateTime+=delta;

        if(angle > 90 && angle < 270){ // 6 to 12 Clockwise or LEFT
            currentFrame = FadingReality.resourceManager.playerRifleAnimLeft.getKeyFrame(stateTime, true);
        } else { // 12 to 6 clockwise or RIGHT
            xPos = -20;
            currentFrame = FadingReality.resourceManager.playerRifleAnimRight.getKeyFrame(stateTime, true);
        }

        if (playerDirection==Entity.Direction.UP) {
            yPos = -10;
        } else {
            yPos = 0;
        }


        batch.begin();
//        batch.draw(gunTexture, pos.x+xPos+64+9, pos.y+yPos+64+7, 1, 6.5f, width, height, 1, 1, angle, 0, 0, (int)width, (int)height, flipX, flipY);
        batch.draw(currentFrame, pos.x+xPos+64+13, pos.y+yPos+64+10, 1, 6.5f, 38, 13, 1, 1, angle);
        batch.end();
}

    public void drawAmmo(SpriteBatch batch, float delta) {
        batch.begin();
        for(AmmoOld a : activeAmmoOld){
            a.draw(batch, delta);
        }
        batch.end();
    }

}
