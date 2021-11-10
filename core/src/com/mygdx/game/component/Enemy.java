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

    protected enum State {
        NORMAL,
        FREEZ,
        DEAD,
    }

    private State state;


    public Enemy(){
        state = State.FREEZ;
        initBoundingBox();
        initEntityRangeBox();
        initChaseRangeBox();
        initAttackRangeBox();

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
                entityName = entityConfig.getEntityID();
                chaseRangeBox.set(currentEntityPosition.x-(entityConfig.getAttackRadiusBoxWidth()/2)+(boundingBox.width/2), currentEntityPosition.y-(entityConfig.getAttackRadiusBoxHeight()/2)+(boundingBox.height/2), entityConfig.getAttackRadiusBoxWidth(), entityConfig.getAttackRadiusBoxHeight());
            } else if (string[0].equalsIgnoreCase(MESSAGE.ACTIVATE_ANIM_MECHAN.toString())) {
                activateAnimMechan = json.fromJson(Boolean.class, string[1]);
            }

            else if(string[0].equalsIgnoreCase(MESSAGE.LOAD_ANIMATIONS.toString())) {
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

        if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            state = State.NORMAL;
        }

        updateBoundingBoxPosition(64,64);
        updateEntityRangeBox(64,64);
        updateAttackRangeBox(64,64);
        updateChaseRangeBox(64,64);

        //INPUT
        activeGotHit(delta);
        activeSwordAttackMoveForEnemy(delta);
        setSwordRangeBox(new Vector2(10000,10000),0,0);


        Rectangle playerBoundingBox = mapManager.getPlayer().getCurrentBoundingBox();
        Rectangle playerRangeBox = mapManager.getPlayer().getCurrentEntityRangeBox();
        Rectangle playerSwordRangeBox = mapManager.getPlayer().getCurrentSwordRangeBox();

        Entity player = mapManager.getPlayer();

        if(playerSwordRangeBox.overlaps(entityRangeBox)){
            stateTime=0f;
            doGotHit();
            health-=25;
            state = State.FREEZ;
            currentState = Entity.State.TAKING_DAMAGE;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    state = State.NORMAL;
                }
            }, 0.3f);

            PlayerHUD.toastShort("Player HIT", Toast.Length.SHORT);

            Rumble.rumble(5, .1f, 0, Rumble.State.SWORD);
        }


        switch (state) {
            case NORMAL:
//                if (health > 0) {
//                    if (attackRangeBox.overlaps(playerBoundingBox)) {   // ATTACK
//                        long time = System.currentTimeMillis();
//                        if (time > lastAttack + cooldownTime) {
//                            // Do something
//                            state = State.FREEZ;
//                            currentState = Entity.State.MELEE_ATTACK;
//                            lastAttack = time;
//
//                            doSwordAttackMoveForEnemy(mapManager);
//
//                            if (entityName.equals(EntityFactory.EntityName.ELITE_KNIGHT.toString())) {
//                                Timer.schedule(new Timer.Task() {
//                                    @Override
//                                    public void run() {
//                                        state = State.NORMAL;
//                                    }
//                                }, 3f);
//                            } else {
//                                Timer.schedule(new Timer.Task() {
//                                    @Override
//                                    public void run() {
//                                        updateSwordRangeBox(64,64);
//                                    }
//                                }, 0.3f);
//
//                                Timer.schedule(new Timer.Task() {
//                                    @Override
//                                    public void run() {
//                                        state = State.NORMAL;
//                                    }
//                                }, 0.5f);
//                            }
//
//
//                        } else {
//                            atkTime = 0f;
//                            currentState = Entity.State.IDLE;
//                        }
//                    } else if (!boundingBox.overlaps(mapManager.getPlayer().getCurrentBoundingBox()) && chaseRangeBox.overlaps(playerBoundingBox) && !isCollisionWithMapEntities(entity, mapManager)) { //CHASE
//                        long time = System.currentTimeMillis();
//                        if (time > lastAttack + cooldownTime) {
//                            // Do something
//                            atkTime = 0f;
//                            chase(mapManager);
//                        } else {
//                            atkTime = 0f;
//                            currentState = Entity.State.IDLE;
//                        }
//                    } else { //IDLE
//                        currentState = Entity.State.IDLE;
//                    }
//                } else {
//                    stateTime=0f;
//                    state = State.DEAD;
//                    currentState = Entity.State.DEAD;
//                }
                break;
            case FREEZ:
                break;
            case DEAD:
                break;
        }


        //GRAPHICS
         updateAnimations(delta);




        Array<Array<Node>> grid = mapManager.getCurrentMap().getGrid();

//        for (int y = 0; y < grid.size; y++) {
//            for (int x = 0; x < grid.get(y).size; x++) {
//                if (grid.get(y).get(x).rectangle.overlaps(boundingBox)) {
//                    startNode = grid.get(y).get(x);
//                    startNode.setType(Node.GridType.START);
//                    pathFinder.setGridNode(startNode, Node.GridType.START);
//                }
//                if (grid.get(y).get(x).rectangle.contains(playerBoundingBox.x, playerBoundingBox.y)) {
//                    endNode = grid.get(y).get(x);
//                    endNode.setType(Node.GridType.END);
//                    pathFinder.setGridNode(endNode, Node.GridType.END);
//                }
//            }
//        }
//
//        pathFinder.setGrid(mapManager.getCurrentMap().getGrid());
//        pathFinder.findPath();
//
//        mapManager.getCurrentMap().updateGridOjc();


        this.mapManager = mapManager;
        this.camera = mapManager.getCamera();
    }

    public void followPath(MapManager mapManager){
        Array<Node> finalP = pathFinder.getFinalPath();

        if(finalP == null && finalP.size == 0) return;

        Node node = finalP.removeIndex(finalP.size - 1);

        if (node.rectangle.x > currentEntityPosition.x) {
            currentState = Entity.State.RUN;
            currentDirection = Entity.Direction.RIGHT;
            currentEntityPosition.x += 1f;
        }
        if (node.rectangle.x < currentEntityPosition.x){
            currentState = Entity.State.RUN;
            currentDirection = Entity.Direction.LEFT;
            currentEntityPosition.x-=1f;
        }
        if (node.rectangle.y > currentEntityPosition.y) {
            currentEntityPosition.y+=1f;
        }
        if (node.rectangle.y < currentEntityPosition.y) {
            currentEntityPosition.y-=1f;
        }

    }

    @Override
    public void draw(Batch batch, float delta) {
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

        //Used to graphically debug boundingBox
        Rectangle rect = boundingBox;
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        shapeRenderer.end();

        batch.begin();
        batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
        batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y);
        batch.end();
    }

    private void chase(MapManager mapMgr) {
        Vector2 playerCurrentPosition = mapMgr.getPlayer().getCurrentPosition();

        if (playerCurrentPosition.x > currentEntityPosition.x) {
            currentState = Entity.State.RUN;
            currentDirection = Entity.Direction.RIGHT;
            currentEntityPosition.x += 1.5f;
        }
        if (playerCurrentPosition.x < currentEntityPosition.x){
            currentState = Entity.State.RUN;
            currentDirection = Entity.Direction.LEFT;
            currentEntityPosition.x-=1.5f;
        }
        if (playerCurrentPosition.y > currentEntityPosition.y) {
            currentEntityPosition.y+=1.5f;
        }
        if (playerCurrentPosition.y < currentEntityPosition.y) {
            currentEntityPosition.y-=1.5f;
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
