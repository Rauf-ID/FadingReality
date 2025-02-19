package com.mygdx.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FadingReality;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityConfig;
import com.mygdx.game.observer.ComponentObserver;
import com.mygdx.game.managers.ResourceManager;
import com.mygdx.game.world.MapManager;

public class NPC extends Component {

    private boolean isSelected = false;
    private boolean wasSelected = false;

    private boolean playerInActiveZone = false;
    private boolean playerInActiveZone2 = true;

    private float yY = 0;
    private boolean aBoolean = true;

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
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_STATE.toString())) {
                currentState = json.fromJson(Entity.State.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_CONFIG.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
                initImageBox(entityConfig.getImageBox());
                initBoundingBox(entityConfig.getBoundingBox());
                initActiveZoneBox(entityConfig.getActiveZoneBox());
                entityName = entityConfig.getEntityID();
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_ANIMATIONS.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
                Array<EntityConfig.AnimationConfig> animationConfigs = entityConfig.getAnimationConfig();

                if (animationConfigs.size == 0) return;

                for(EntityConfig.AnimationConfig animationConfig : animationConfigs) {
                    float frameDuration = animationConfig.getFrameDuration();
                    ResourceManager.AtlasType atlasType = animationConfig.getAtlasType();
                    Entity.AnimationType animationType = animationConfig.getAnimationType();
                    Animation.PlayMode playMode = animationConfig.getPlayMode();

                    Animation<Sprite> animation;
                    animation = loadAnimation(frameDuration, atlasType, animationType, playMode);
                    animations.put(animationType, animation);
                }
            }
        }
    }

    @Override
    public void update(Entity entity, MapManager mapManager, Batch batch, float delta) {
        this.mapManager = mapManager;
        this.camera = mapManager.getCamera();

        updateImageBox();
        updateBoundingBox();
        updateActiveZoneBox();

        switch (state) {
            case NORMAL:
                Entity player = mapManager.getPlayer();
                Rectangle playerBoundingBox = player.getBoundingBox();
//                currentEntityPosition.x += Math.random();

                if (activeZoneBox.overlaps(playerBoundingBox)) {
                    mapManager.setCurrentMapEntity(entity); // Задать текущего персонажа на карте
                    playerInActiveZone = true;
                    if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                        notify(json.toJson(entity.getEntityConfig()), ComponentObserver.ComponentEvent.LOAD_CONVERSATION);
//                        playerInActiveZone2 = false;
                    }
                } else {
//                    notify(json.toJson(entity.getEntityConfig()), ComponentObserver.ComponentEvent.HIDE_CONVERSATION);
                    playerInActiveZone = false;
                    playerInActiveZone2 = true;
                }
                break;
            case FREEZE:
                break;
            case DEATH:
                currentState = Entity.State.IDLE;
                break;
        }

        //GRAPHICS
        updateAnimations(delta);
    }

    @Override
    public void draw(Batch batch, float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) debugActive = !debugActive;
        if (debugActive) debug(false,false,false, false,
                false,false,false, false,
                false,false,true,true,false, false);

        batch.begin();
        drawDialogueImg(batch, delta);
        batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
        batch.end();
    }

    private void drawDialogueImg(Batch batch, float delta) {
        if (playerInActiveZone && playerInActiveZone2) {
            Texture texture = FadingReality.resourceManager.dialogueImg;
            int x = (int) (imageBox.x + imageBox.getWidth() / 2 - texture.getWidth() / 2);
            int y = (int) (imageBox.y + imageBox.getHeight() - texture.getHeight());
            if (!aBoolean) {
                if (yY > 0) {
                    aBoolean = true;
                }
                yY += 4 * delta;
            } else {
                if (yY < -5) {
                    aBoolean = false;
                }
                yY -= 4 * delta;
            }
            batch.draw(texture, x, y + yY);
        }
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
