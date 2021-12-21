package com.mygdx.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.world.MapManager;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

public class MapObject extends Component {

    private TextureRegion textureRegion;
    private TextureMapObject textureMapObject;
    private float x,y;

    private String side;
    private boolean active = false;
    private float startX;


    public MapObject(TextureMapObject textureMapObject) {
        this.textureMapObject = textureMapObject;
        textureRegion = textureMapObject.getTextureRegion();
        currentEntityPosition.x = textureMapObject.getX();
        currentEntityPosition.y = textureMapObject.getY();
        startX = textureMapObject.getX();
        initBoundingBoxForObject(textureMapObject.getTextureRegion().getRegionWidth(), textureMapObject.getTextureRegion().getRegionHeight());
    }

    @Override
    public void update(Entity entity, MapManager mapManager, Batch batch, float delta) {
//        String doorID = (String) textureMapObject.getProperties().get("side");
//        System.out.println(doorID);

        Entity player = mapManager.getPlayer();
        String currentCollision = player.getCurrentCollision();
        if (textureMapObject.getProperties().get("objectType").equals(currentCollision)) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                active = true;
                side = (String) textureMapObject.getProperties().get("side");
            }
        }

        if (active) {
            openDoor(side, 35,50, delta);
        }

    }

    public void openDoor(String side, int distance, int speed, float delta) {
        if (side.equals("left")) {
            if ( textureMapObject.getX() >  startX - distance) {
                currentEntityPosition.x -= delta*speed;
                textureMapObject.setX(currentEntityPosition.x);
            } else {
                active = false;
            }
        }
    }

    public void closeDoor() {

    }

    @Override
    public void draw(Batch batch, float delta) {
//        currentEntityPosition.x += 1 * delta;
//        textureMapObject.setX(currentEntityPosition.x);
        batch.begin();
        batch.draw(this.textureRegion, currentEntityPosition.x, currentEntityPosition.y);
        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void receiveMessage(String message) {

    }
}
