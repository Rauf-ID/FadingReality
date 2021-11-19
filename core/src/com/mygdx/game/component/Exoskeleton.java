package com.mygdx.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.pathfinder.Node;
import com.mygdx.game.world.MapManager;

public class Exoskeleton extends Component{
    //demo
    public Exoskeleton(){
        state = State.FREEZE;
        initEntityRangeBox();
        initChaseRangeBox();
        initAttackRangeBox();
        health = 100;
    }

    @Override
    public void update(Entity entity, MapManager mapManager, Batch batch, float delta) {

    }

    @Override
    public void draw(Batch batch, float delta) {
        /*batch.begin();
        batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
        batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y);
        batch.end();*/
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
