package com.mygdx.game._oldClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class AmmoOld {

    private Gun gun;

    public float range; // -
    public float speed; // -
    public float distMoved; // -

    public Sprite sprite; // -
    public Vector2 vector; // -
    public Vector3 pos; // -
    public boolean active; // -
    public boolean remove; // -
    public float width, height; // -
    public float angle; // -
    protected ArrayList<Vector3> ammoTrace = new ArrayList<>(); // -
    protected boolean dashing = false; // -

    public AmmoOld(Gun gun){
        this.gun = gun;
        sprite = new Sprite(new Texture("bullet.png"));

        vector = new Vector2();
        pos = new Vector3();
        range = 360;
        speed = 720;
        width = sprite.getWidth();
        height = sprite.getHeight();
        active = true;
        dashing = true;
        setupBullet();
    }

    public void setupBullet(){
        angle = gun.angle;
        float angleRadians = MathUtils.degreesToRadians * gun.angle;
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);

        pos.x = gun.pos.x + 64 + 13 + (vector.x * 10);
        pos.y = gun.pos.y + 64 + 10 + (vector.y * 10);
    }

    public void tick(float delta){
        if(active){
            float dx = (delta * vector.x) * speed;
            float dy = (delta * vector.y) * speed;
            float dx2 = pos.x + dx;
            float dy2 = pos.y + dy;

            distMoved += Vector2.dst(pos.x, pos.y, dx2, dy2);
            pos.set(dx2, dy2, 0);

            if(distMoved > range){
                remove = true;
                active = false;
                dashing = false;
            }
        }
    }

    public void draw(SpriteBatch batch, float delta){
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


}
