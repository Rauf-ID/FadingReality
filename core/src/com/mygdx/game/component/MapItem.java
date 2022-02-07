package com.mygdx.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.FadingReality;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityConfig;
import com.mygdx.game.item.Item;
import com.mygdx.game.observer.ComponentObserver;
import com.mygdx.game.world.MapManager;

import static com.mygdx.game.managers.ResourceManager.AtlasType.ATLAS_MAP_ITEMS;

public class MapItem extends Component {

    private boolean playerInActiveZone = false;
    private boolean playerInActiveZone2 = true;
    private boolean aBoolean = true;
    private float yY = 0;
    private EntityConfig entityConfig;
    private Item.ItemID itemID;

    public MapItem() {
        currentState = Entity.State.IDLE;
        currentDirection = Entity.Direction.RIGHT;
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if( string.length == 0 ) return;

        if( string.length == 1 ) {
            if (string[0].equalsIgnoreCase(Message.MESSAGE.COLLISION_WITH_MAP.toString())) {
                currentDirection = Entity.Direction.DOWN;
            } else if (string[0].equalsIgnoreCase(Message.MESSAGE.COLLISION_WITH_ENTITY.toString())) {
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
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_ITEM.toString())) {
                itemID = json.fromJson(Item.ItemID.class, string[1]);
            }  else if (string[0].equalsIgnoreCase(MESSAGE.INIT_CONFIG.toString())) {
                entityConfig = json.fromJson(EntityConfig.class, string[1]);
                initImageBox(entityConfig.getImageBox());
                initBoundingBox(entityConfig.getBoundingBox());
                initActiveZoneBox(entityConfig.getActiveZoneBox());
                currentFrame = new Sprite(FadingReality.resourceManager.getAtlas(ATLAS_MAP_ITEMS).findRegion(entityConfig.getEntityID()));
            }
        }
    }

    @Override
    public void update(Entity entity, MapManager mapManager, Batch batch, float delta) {
        this.mapManager = mapManager;
        this.camera = mapManager.getCamera();

        updateImageBox();
        updateBoundingBoxForItem();
        updateActiveZoneBox();

        Entity player = mapManager.getPlayer();
        Rectangle playerBoundingBox = player.getBoundingBox();



        if (playerBoundingBox.overlaps(activeZoneBox)) {
            mapManager.setCurrentMapEntity(entity);
            playerInActiveZone = true;
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
//                notify(itemID.toString(), ComponentObserver.ComponentEvent.ITEM_PICK_UP);
                notify(itemID.toString(), ComponentObserver.ComponentEvent.TEST_EVENT);
                playerInActiveZone2 = false;
            }
        } else {
            playerInActiveZone = false;
            playerInActiveZone2 = true;
        }

//        updateAnimations(delta);
    }

    @Override
    public void draw(Batch batch, float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) {
            debugActive = !debugActive;
        }
        if (debugActive) {
            debug(true, true, true);
        }

        batch.begin();
        drawDialogueImg(batch, delta);
        batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
        batch.end();
    }

    public void debug(boolean activeImageBox, boolean activeBoundingBox, boolean activeActiveZoneBox) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (activeImageBox) {
            Rectangle rect = imageBox;
            shapeRenderer.setColor(0.5f, .92f, .75f, 1f);
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
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

    private void drawDialogueImg(Batch batch, float delta) {
        if (playerInActiveZone && playerInActiveZone2) {
            Texture texture = FadingReality.resourceManager.dialogueImg;
            int x = (int) (imageBox.x + imageBox.getWidth() / 2 - texture.getWidth() / 2);
            int y = (int) (imageBox.y + imageBox.getHeight() + texture.getHeight() / 2);
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
