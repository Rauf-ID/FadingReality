package com.mygdx.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SimpleBullet extends Ammo {

    private Gun gun;

    public SimpleBullet(Gun gun){
        super();
        this.gun = gun;
        texture = new Texture("SimpleBullet.png");
        sprite = new Sprite(texture);
        range = 360;
        damage = 1;
        width = texture.getWidth();
        height = texture.getHeight();
        speed = 720;
        active = true;
        dashing = true;
        setupBullet();
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

    public void setupBullet(){
        angle=gun.angle;
        float angleRadians = MathUtils.degreesToRadians * gun.angle;
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);

        pos.x = gun.pos.x + 64 + 13 + (vector.x * 10);
        pos.y = gun.pos.y + 64 + 10 + (vector.y * 10);

    }

}
