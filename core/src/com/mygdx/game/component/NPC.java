package com.mygdx.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityConfig;
import com.mygdx.game.tools.managers.ResourceManager;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapManager;

public class NPC extends Component {

    private boolean isSelected = false;
    private boolean wasSelected = false;

    private boolean sentShowConversationMessage = false;
    private boolean sentHideConversationMessage = false;

    public NPC () {
        state = State.NORMAL;
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if( string.length == 0 ) return;

        if( string.length == 1 ) {
            if (string[0].equalsIgnoreCase(MESSAGE.ENTITY_SELECTED.toString())) {
                if (wasSelected) {
                    isSelected = false;
                } else {
                    isSelected = true;
                }
            } else if (string[0].equalsIgnoreCase(MESSAGE.ENTITY_DESELECTED.toString())) {
                wasSelected = isSelected;
                isSelected = false;
            } else if (string[0].equalsIgnoreCase(MESSAGE.COLLISION_WITH_MAP.toString())) {
                currentDirection = Entity.Direction.DOWN;
            } else if (string[0].equalsIgnoreCase(MESSAGE.COLLISION_WITH_ENTITY.toString())) {
                currentState = Entity.State.IDLE;
            }
        }

        if( string.length == 2 ) {
            if (string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())) {
                currentEntityPosition = json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
                currentEntityPosition = json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_STATE.toString())) {
                currentState = json.fromJson(Entity.State.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_CONFIG.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
                initImageBox(entityConfig.getImageBox());
                initBoundingBox(entityConfig.getBoundingBox());
                initActiveZoneBox(entityConfig.getActiveZoneBox());
//                chaseRangeBox.set(currentEntityPosition.x-(entityConfig.getAttackRadiusBoxWidth()/2)+(boundingBox.width/2), currentEntityPosition.y-(entityConfig.getAttackRadiusBoxHeight()/2)+(boundingBox.height/2), entityConfig.getAttackRadiusBoxWidth(), entityConfig.getAttackRadiusBoxHeight());
            } else if(string[0].equalsIgnoreCase(MESSAGE.LOAD_ANIMATIONS.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
                Array<EntityConfig.AnimationConfig> animationConfigs = entityConfig.getAnimationConfig();

                if (animationConfigs.size == 0) return;

                for(EntityConfig.AnimationConfig animationConfig : animationConfigs) {
                    float frameDuration = animationConfig.getFrameDuration();
                    ResourceManager.AtlasType atlasType = animationConfig.getAtlasType();
                    Entity.AnimationType animationType = animationConfig.getAnimationType();
                    Animation.PlayMode playMode = animationConfig.getPlayMode();

                    Animation<Sprite> animation = null;
                    animation = loadAnimation(frameDuration, atlasType, animationType, playMode);
                    animations.put(animationType, animation);
                }
            }
        }
    }

    @Override
    public void update(Entity entity, MapManager mapManager, Batch batch, float delta) {
        this.camera = mapManager.getCamera();

        updateBoundingBox();
        updateActiveZoneBox();
        updateEntityRangeBox(64,64);


        switch (state) {
            case NORMAL:
                break;
            case FREEZE:
                break;
            case DEAD:
                break;
        }

        //GRAPHICS
        updateAnimations(delta);
    }

    @Override
    public void draw(Batch batch, float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) {
            debugActive = !debugActive;
        }
        if (debugActive) {
            debug(true, true);
        }

        batch.begin();
        batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
        batch.end();
    }

    private void drawSelected(Entity entity, MapManager mapMgr){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Camera camera = mapMgr.getCamera();
        Rectangle rect = entity.getCurrentBoundingBox();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.0f, 1.0f, 1.0f, 0.5f);

        float width =  rect.getWidth() * Map.UNIT_SCALE*1.5f;
        float height = rect.getHeight() * Map.UNIT_SCALE/4f;
        float x = rect.x;
        float y = rect.y;

        shapeRenderer.ellipse(x-3,y-14,width,height);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void debug(boolean activeBoundingBox, boolean activeActiveZoneBox) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (activeBoundingBox) {
            Rectangle rect = boundingBox;
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
        if (activeActiveZoneBox) {
            Rectangle rect = activeZoneBox;
            shapeRenderer.setColor(Color.ORANGE);
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
        shapeRenderer.end();
    }

    @Override
    protected boolean isCollisionWithMapEntities(Entity entity, MapManager mapMgr){
        //Test against player
        if( isCollision(entity, mapMgr.getPlayer()) ) {
            return true;
        }

        if( super.isCollisionWithMapEntities(entity, mapMgr) ){
            return true;
        }

        return false;
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

}
