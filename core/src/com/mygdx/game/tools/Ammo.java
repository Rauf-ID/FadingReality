package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Ammo {

    public Texture texture;
    public Sprite sprite;
    public Texture shadow;

    public float range;
    public float damage;
    public float distMoved;
    public Vector2 vector;
    public Vector3 pos;
    public boolean active;
    public boolean remove;
    public float width, height;
    public float speed;
    public float angle;

    protected ArrayList<Vector3> ammoTrace = new ArrayList<>();
    protected boolean dashing = false;
    protected float ammoTime = 0;
    protected int anInt1 = 1;



    public Ammo(){
        super();
        vector = new Vector2();
        pos = new Vector3();
    }

    public void tick(float delta){

    }

    public void draw(SpriteBatch batch, float delta){
//        if(shadow != null) batch.draw(shadow, pos.x, pos.y, width, height);
//        if(texture != null) batch.draw(texture, pos.x, pos.y, width, height);
//        if(texture != null) batch.draw(texture, pos.x, pos.y, 10,10, width, height, 10,10, 1,10,10, (int)width,(int)height,false,false);


        if(dashing) {
            if(Gdx.graphics.getFrameId() % (int) ((Gdx.graphics.getFramesPerSecond()*.02f)+1) == 0) {  //def .05f
                ammoTrace.add(new Vector3(pos.x, pos.y, 1));
                anInt1++;
            }
            ammoTime += delta;
        }
//        if(ammoTime > 0.2f) {  //def .02f
//            ammoTime = 0;
//            dashing = false;
//            anInt1 = 1;
//        }


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

        if(texture != null) batch.draw(sprite, pos.x, pos.y, 0, 0, width, height, 1, 1, angle);



    }

}
