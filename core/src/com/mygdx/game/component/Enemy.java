package com.mygdx.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.UI.PlayerHUD;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityConfig;
import com.mygdx.game.tools.Rumble;
import com.mygdx.game.tools.Toast;
import com.mygdx.game.tools.managers.ResourceManager;
import com.mygdx.game.pathfinder.Node;
import com.mygdx.game.world.MapManager;

public class Enemy extends Component {

    public Enemy(){
        state = State.FREEZE;
        initEntityRangeBox();
        initChaseRangeBox();
        initAttackRangeBox();
        health = 100;
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
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INTERACTION_WITH_ENTITY.toString())) {
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_CONFIG.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
                initHitBox(entityConfig.getHitBox());
                initImageBox(entityConfig.getImageBox());
                initBoundingBox(entityConfig.getBoundingBox());
//                chaseRangeBox.set(currentEntityPosition.x-(entityConfig.getAttackRadiusBoxWidth()/2)+(boundingBox.width/2), currentEntityPosition.y-(entityConfig.getAttackRadiusBoxHeight()/2)+(boundingBox.height/2), entityConfig.getAttackRadiusBoxWidth(), entityConfig.getAttackRadiusBoxHeight());
            } else if(string[0].equalsIgnoreCase(MESSAGE.LOAD_ANIMATIONS.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);Array<EntityConfig.AnimationConfig> animationConfigs = entityConfig.getAnimationConfig();

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

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            enemyActive = !enemyActive;
        }

        if (enemyActive) {
            state = State.NORMAL;
        } else {
            state = State.FREEZE;
        }

        updateHitBox();
        updateImageBox();
        updateBoundingBox();

        updateEntityRangeBox(64,64);
        updateAttackRangeBox(64,64);
        updateChaseRangeBox(64,64);

        //INPUT
        activeGotHit(delta);
        activeSwordAttackMoveForEnemy(delta);
        setSwordRangeBox(new Vector2(10000,10000),0,0);

        Entity player = mapManager.getPlayer();
        Rectangle playerBoundingBox = player.getBoundingBox();

        switch (state) {
            case NORMAL:
                Array<Array<Node>> grid = mapManager.getCurrentMap().getGrid();

                pathFinder.setGrid(grid);
                for (int y = 0; y < grid.size; y++) {
                    for (int x = 0; x < grid.get(y).size; x++) {
                        if (grid.get(y).get(x).rectangle.contains(boundingBox.x + boundingBox.width / 2, boundingBox.y)) {
                            startNode = grid.get(y).get(x);
                            startNode.setType(Node.GridType.START);
                            pathFinder.setGridNode(startNode, Node.GridType.START);
                        }
                        if (grid.get(y).get(x).rectangle.contains(playerBoundingBox.x + playerBoundingBox.width / 2, playerBoundingBox.y + playerBoundingBox.height / 2)) {
                            endNode = grid.get(y).get(x);
                            endNode.setType(Node.GridType.END);
                            pathFinder.setGridNode(endNode, Node.GridType.END);
                        }
                    }
                }
                pathFinder.findPath();

                followPath();

                break;
            case FREEZE:
                break;
            case DEAD:
                break;
        }

        //GRAPHICS
         updateAnimations(delta);
    }

    public void followPath(){
        Array<Node> finalP = pathFinder.getFinalPath();

        Node node0 = finalP.get(0);
        if (finalP.size == 1) {
            return;
        }
        Node node1 = finalP.get(1);

        if (node1.rectangle.x > node0.rectangle.x) {
            currentState = Entity.State.RUN;
            currentDirection = Entity.Direction.RIGHT;
            currentEntityPosition.x += 1f;
        }
        if (node1.rectangle.x < node0.rectangle.x){
            currentState = Entity.State.RUN;
            currentDirection = Entity.Direction.LEFT;
            currentEntityPosition.x -= 1f;
        }
        if (node1.rectangle.y > node0.rectangle.y) {
            currentEntityPosition.y += 1f;
        }
        if (node1.rectangle.y < node0.rectangle.y) {
            currentEntityPosition.y -= 1f;
        }
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
    public void draw(Batch batch, float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) {
            debugActive = !debugActive;
        }
        if (debugActive) {
            debug(true, true, false, true);
        }

        batch.begin();
        batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
        batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y);
        batch.end();
    }

    public void debug(boolean activePath, boolean activeHitBox, boolean activeImageBox, boolean activeBoundingBox) {
        if (activePath) {
            //Render path
            Array<Node> finalP = pathFinder.getFinalPath();
            shapeRenderer2.setProjectionMatrix(camera.combined);
            shapeRenderer2.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer2.setColor(Color.GOLD);
            for (Node node : finalP) {
                Rectangle rectangle = node.rectangle;
                shapeRenderer2.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            }
            shapeRenderer2.end();
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (activeHitBox) {
            Rectangle rect = hitBox;
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
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
        shapeRenderer.end();
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
