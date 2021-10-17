package com.mygdx.game.tools.managers;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.inventory.InventoryItem;
import com.mygdx.game.tools.Gun;
import com.mygdx.game.tools.Rumble;
import com.mygdx.game.tools.SimpleBullet;

public class WeaponSystem {

    private Gun gun;
    private float shootTimer = 0f;
    private static final float SHOOT_WAIT_TIMER = 0.3f;
    private float angle;

    private InventoryItem.ItemTypeID itemTypeID;

    public WeaponSystem() {
        gun = new Gun(1, 0,0);
        gun.addAmmo(30000);
    }

    public void update(Entity player, float delta) {
        updateAngleCenterToMouse();
        gun.setAngle(angle);
        gun.setPlayerDirection(player.getCurrentDirection());
        shootTimer+=delta;
        if (player.getBoolPissPiss() && shootTimer >= SHOOT_WAIT_TIMER){ //&& shootTimer >= SHOOT_WAIT_TIMER
            shootTimer = 0;
            player.setBoolPissPiss(false);
            SimpleBullet bullet = new SimpleBullet(gun);
            gun.addActiveAmmo(bullet);
            if(player.getCurrentDirection() == Entity.Direction.LEFT) {
                Rumble.rumble(-1f, .06f, 0, Rumble.State.GUN);
            } else if (player.getCurrentDirection() == Entity.Direction.RIGHT) {
                Rumble.rumble(1f, .06f, 0, Rumble.State.GUN);
            } else if (player.getCurrentDirection() == Entity.Direction.UP) {
                Rumble.rumble(1f, .1f, 1, Rumble.State.GUN);
            } else if (player.getCurrentDirection() == Entity.Direction.DOWN) {
                Rumble.rumble(-1f, .1f, 1, Rumble.State.GUN);
            }
        }
        gun.updatePos(player.getCurrentPosition().x, player.getCurrentPosition().y);
        gun.tick(delta);
        gun.clearTravelledAmmo();
    }

    public void updateAngleCenterToMouse() {
        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        angle = (float) Math.toDegrees(Math.atan2(screenX - (screenWidth/2), screenY - (screenHeight/2)));
        angle = angle < 0 ? angle += 360: angle;
        angle-=90;
    }

    public Gun getGun() {
        return gun;
    }
}
