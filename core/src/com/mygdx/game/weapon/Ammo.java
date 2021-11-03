package com.mygdx.game.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Ammo {

    public enum AmmoID {
        CALIBER_12,
        CALIBER_9MM,
        CALIBER_6MM,
        CALIBER_5_55MM,
        BATTERIES,
        UM,
    }

    private Weapon weapon;
    private int hitRange, ammoSpeed;

    private Sprite sprite;
    private Vector2 vector;
    private Vector3 pos;
    private float width, height;
    private float distMoved;
    private float angle;
    private boolean active, remove;
    private boolean dashing = false;
    private ArrayList<Vector3> ammoTrace = new ArrayList<>();

    public Ammo(Weapon weapon) {
        this.weapon = weapon;
        sprite = new Sprite(new Texture(this.weapon.getPathTextureAmmo()));
        hitRange = this.weapon.getHitRange();
        ammoSpeed = this.weapon.getAmmoSpeed();
        width = sprite.getWidth();
        height = sprite.getHeight();

        active = true;
        dashing = true;
        vector = new Vector2();
        pos = new Vector3();

        setupBullet();
    }

    private void setupBullet(){
        angle = weapon.getAngle();
        float angleRadians = MathUtils.degreesToRadians * weapon.getAngle();
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);

        pos.x = weapon.getPos().x + 64 + 13 + (vector.x * 10);
        pos.y = weapon.getPos().y + 64 + 10 + (vector.y * 10);
    }

    public void tick(float delta){
        if(active){
            float dx = (delta * vector.x) * ammoSpeed;
            float dy = (delta * vector.y) * ammoSpeed;
            float dx2 = pos.x + dx;
            float dy2 = pos.y + dy;

            distMoved += Vector2.dst(pos.x, pos.y, dx2, dy2);
            pos.set(dx2, dy2, 0);

            if(distMoved > hitRange){
                remove = true;
                active = false;
                dashing = false;
            }
        }
    }

    public void draw(Batch batch){
        if(dashing) {
            if(Gdx.graphics.getFrameId() % (int) ((Gdx.graphics.getFramesPerSecond()*.02f)+1) == 0) {  //def .05f
                ammoTrace.add(new Vector3(pos.x, pos.y, 1));
            }
        }

        for(Vector3 trace : ammoTrace) {
            batch.setColor(0.05f,0.7f, 0.8f, trace.z);
            batch.draw(sprite, trace.x, trace.y,0, 0, width, height, 1, 1, angle);
            trace.z -= Gdx.graphics.getDeltaTime()*6;  //def *2 *6 *10
        }
        batch.setColor(Color.WHITE);
        for(int i = 0; i < ammoTrace.size(); i++) {
            if(ammoTrace.get(i).z <= 0) {
                ammoTrace.remove(i);
            }
        }

        if(sprite != null) batch.draw(sprite, pos.x, pos.y, 0, 0, width, height, 1, 1, angle);
    }



    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }
}
