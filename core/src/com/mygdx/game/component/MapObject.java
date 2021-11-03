package com.mygdx.game.component;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.world.MapManager;

public class MapObject extends Component {

    private TextureRegion textureRegion;
    private float x,y;

    public MapObject(TextureRegion textureRegion, float x, float y, float width, float height) {
        this.textureRegion = textureRegion;
        currentEntityPosition.x=x;
        currentEntityPosition.y=y;
        initBoundingBoxForObject(width, height);
    }

    @Override
    public void update(Entity entity, MapManager mapManager, Batch batch, float delta) {

    }

    @Override
    public void draw(Batch batch, float delta) {
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
