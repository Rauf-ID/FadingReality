package com.mygdx.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.FadingReality;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.observer.ComponentSubject;
import com.mygdx.game.world.MapManager;

import java.util.ArrayList;

public abstract class Component extends ComponentSubject implements Message, InputProcessor {

    protected Json json;
    protected float stateTime = 0f;
    protected float policeLookedAround = 0f;
    public float atkTime = 0f;
    protected Sprite currentFrame = null;
    protected Sprite currentFrame2 = null;
    protected ShapeRenderer shapeRenderer;

    public Entity.State currentState = null;
    public Entity.Direction currentDirection = null;
    protected Entity.MouseDirection mouseDirection = null;

    public Vector3 mouseCoordinates;

    protected float health = 100;

    public Vector2 currentEntityPosition;
    protected Array<Entity> tempEntities;
    protected Vector2 runVelocity, runVelocityD;
    protected Vector2 walkVelocity, walkVelocityD;

    public boolean boolGunActive=false;
    public boolean boolPissPiss=false;

    protected float angle;
    protected Vector2 vector;
    protected boolean activeDash = false;
    protected boolean activeSwordAttackMove= false;
    protected boolean activeSwordAttackMoveForEnemy= false;
    protected boolean activeGotHit = false;
    protected float distMoved;

    protected long currentTime;
    protected int attackId = 0, comboTimer = 600;
    protected long timeSinceLastAttack = 0;
    protected float frameAttack = 0f;

    protected String entityName = "";

    public Rectangle boundingBox;
    public Rectangle entityRangeBox;
    public Rectangle swordRangeBox;
    protected Rectangle chaseRangeBox;
    protected Rectangle attackRangeBox;

    protected boolean attackCoolDown=false;
    protected static final float ATTACK_WAIT_TIMER = 1f;
    protected float attackTimer = 0f;

    protected long lastAttack = 0;
    protected long cooldownTime = 1000; //1sec

    protected OrthographicCamera camera;

    protected float ggov = 0.2f;
    protected float speedCamMove=15f;
    protected boolean pdaActive=false;
    protected boolean pdaDeffault=true;

    protected ArrayList<Vector3> dashShadow = new ArrayList<>();
    protected boolean dashing = false;
    protected float dashTime = 0;
    protected int anInt1 = 1;

    protected boolean detentionActive = false;
    protected boolean moveActive = false;

    protected int sheeshTime = 35;
    public boolean activateAnimMechan = false;


    Component() {
        json = new Json();
        shapeRenderer = new ShapeRenderer();

        currentState = Entity.State.IDLE;
        currentDirection = Entity.Direction.RIGHT;
        mouseDirection = Entity.MouseDirection.RIGHT;

        mouseCoordinates = new Vector3();

//        currentEntityPosition = new Vector2(0,0);
//        currentEntityPosition = new Vector2(850,950);
        currentEntityPosition = new Vector2(850,500);
        tempEntities = new Array<Entity>();
        runVelocity = new Vector2(2f,2f);
        runVelocityD = new Vector2(1.4f,1.4f);
        walkVelocity = new  Vector2(2f/2,2f/2);
        walkVelocityD = new Vector2(1.4f/2,1.4f/2);


        vector = new Vector2();

        boundingBox = new Rectangle();
        entityRangeBox = new Rectangle();
        swordRangeBox = new Rectangle();
        chaseRangeBox = new Rectangle();
        attackRangeBox = new Rectangle();
    }

    protected void updateAnimations(float delta){
//        stateTime = (stateTime + delta)%5; //Want to avoid overflow
        stateTime += delta;
        policeLookedAround += delta;
        atkTime += delta;

        currentFrame2 = FadingReality.resourceManager.nulls.getKeyFrame(stateTime, false);

        switch (currentState) {
            case IDLE:
                if (entityName.equals(EntityFactory.EntityName.TOWN_FOLK2.toString())) {
                    if(currentDirection == Entity.Direction.LEFT) {
                        currentFrame = FadingReality.resourceManager.roninAnimIdleLeft.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.RIGHT) {
                        currentFrame = FadingReality.resourceManager.roninAnimIdleRight.getKeyFrame(stateTime, true);
                    }
                } else if(entityName.equals(EntityFactory.EntityName.TOWN_FOLK3.toString()) || entityName.equals(EntityFactory.EntityName.TOWN_FOLK4.toString())) {
                    if(currentDirection == Entity.Direction.LEFT) {
                        currentFrame = FadingReality.resourceManager.policeAnimIdleLeft.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.RIGHT) {
                        currentFrame = FadingReality.resourceManager.policeAnimIdleRight.getKeyFrame(stateTime, true);
                    }
                } else if(entityName.equals(EntityFactory.EntityName.ELITE_KNIGHT.toString())) {
                    if(currentDirection == Entity.Direction.LEFT) {
                        currentFrame = FadingReality.resourceManager.eliteKnightAnimIdleLeft.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.RIGHT) {
                        currentFrame = FadingReality.resourceManager.eliteKnightAnimIdleRight.getKeyFrame(stateTime, true);
                    }
                } else if (entityName.equals(EntityFactory.EntityName.TOWN_FOL.toString())) {
                    if(currentDirection == Entity.Direction.DOWN) {
                        currentFrame = FadingReality.resourceManager.securityMechanismAnimIdle.getKeyFrame(stateTime, true);
                    }
                } else {
                    if (currentDirection == Entity.Direction.UP) {
                        currentFrame = FadingReality.resourceManager.playerAnimIdleUp.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.DOWN) {
                        currentFrame = FadingReality.resourceManager.playerAnimIdleDown.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.LEFT) {
                        currentFrame = FadingReality.resourceManager.playerAnimIdleLeft.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.RIGHT) {
                        currentFrame = FadingReality.resourceManager.playerAnimIdleRight.getKeyFrame(stateTime, true);
                    }
                }



                if (currentDirection == Entity.Direction.UP) {
                    currentFrame = FadingReality.resourceManager.playerAnimIdleUp.getKeyFrame(stateTime, true);
                } else if(currentDirection == Entity.Direction.DOWN) {
                    currentFrame = FadingReality.resourceManager.playerAnimIdleDown.getKeyFrame(stateTime, true);
                } else if(currentDirection == Entity.Direction.LEFT) {
                    currentFrame = FadingReality.resourceManager.playerAnimIdleLeft.getKeyFrame(stateTime, true);
                } else if(currentDirection == Entity.Direction.RIGHT) {
                    currentFrame = FadingReality.resourceManager.playerAnimIdleRight.getKeyFrame(stateTime, true);
                }


                break;
            case WALK:
                if (entityName.equals(EntityFactory.EntityName.TOWN_FOL.toString())) {
                    if(currentDirection == Entity.Direction.DOWN) {
                        currentFrame = FadingReality.resourceManager.securityMechanismAnimWalkDown.getKeyFrame(stateTime, true);
                    }
                } else {
                    if (currentDirection == Entity.Direction.UP) {
                        currentFrame = FadingReality.resourceManager.playerAnimRunUp.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.DOWN) {
                        currentFrame = FadingReality.resourceManager.playerAnimRunDown.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.LEFT) {
                        currentFrame = FadingReality.resourceManager.playerAnimWalkLeft.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.RIGHT) {
                        currentFrame = FadingReality.resourceManager.playerAnimWalkRight.getKeyFrame(stateTime, true);
                    }
                }
                break;
            case WALK_SHADOW:
                currentFrame = FadingReality.resourceManager.securityMechanismAnimWalkDownShadow.getKeyFrame(stateTime, true);
                break;
            case RUN:
                if (entityName.equals(EntityFactory.EntityName.TOWN_FOLK2.toString())) {
                    if(currentDirection == Entity.Direction.LEFT) {
                        currentFrame = FadingReality.resourceManager.roninAnimRunLeft.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.RIGHT) {
                        currentFrame = FadingReality.resourceManager.roninAnimRunRight.getKeyFrame(stateTime, true);
                    }
                } else if(entityName.equals(EntityFactory.EntityName.ELITE_KNIGHT.toString())) {
                    if(currentDirection == Entity.Direction.LEFT) {
                        currentFrame = FadingReality.resourceManager.eliteKnightAnimRunLeft.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.RIGHT) {
                        currentFrame = FadingReality.resourceManager.eliteKnightAnimRunRight.getKeyFrame(stateTime, true);
                    }
                } else {
                    if (currentDirection == Entity.Direction.UP) {
                        currentFrame = FadingReality.resourceManager.playerAnimRunUp.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.DOWN) {
                        currentFrame = FadingReality.resourceManager.playerAnimRunDown.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.LEFT) {
                        currentFrame = FadingReality.resourceManager.playerAnimRunLeft.getKeyFrame(stateTime, true);
                    } else if(currentDirection == Entity.Direction.RIGHT) {
                        currentFrame = FadingReality.resourceManager.playerAnimRunRight.getKeyFrame(stateTime, true);
                    }
                }
                break;
            case DASH:
                if (mouseDirection == Entity.MouseDirection.UP) {
                    currentFrame = FadingReality.resourceManager.playerAnimRunUp.getKeyFrame(stateTime, true);
                } else if(mouseDirection == Entity.MouseDirection.DOWN) {
                    currentFrame = FadingReality.resourceManager.playerAnimRunDown.getKeyFrame(stateTime, true);
                } else if(mouseDirection == Entity.MouseDirection.LEFT) {
                    currentFrame = FadingReality.resourceManager.playerAnimRunLeft.getKeyFrame(stateTime, true);
                } else if(mouseDirection == Entity.MouseDirection.RIGHT) {
                    currentFrame = FadingReality.resourceManager.playerAnimDashRight.getKeyFrame(stateTime, true);
                }
                break;
            case MELEE_ATTACK:
                if (entityName.equals(EntityFactory.EntityName.TOWN_FOLK2.toString())) {
                    if(currentDirection == Entity.Direction.RIGHT) {
                        currentFrame = FadingReality.resourceManager.roninAnimAttackRight.getKeyFrame(atkTime, true);
                    } else if(currentDirection == Entity.Direction.LEFT) {
                        currentFrame = FadingReality.resourceManager.roninAnimAttackLeft.getKeyFrame(atkTime, true);
                    }
                } else if (entityName.equals(EntityFactory.EntityName.ELITE_KNIGHT.toString())) {
                    if(currentDirection == Entity.Direction.RIGHT) {
                        currentFrame = FadingReality.resourceManager.eliteKnightAnimAttackRight.getKeyFrame(atkTime, true);
                    } else if(currentDirection == Entity.Direction.LEFT) {
                        currentFrame = FadingReality.resourceManager.eliteKnightAnimAttackLeft.getKeyFrame(atkTime, true);
                    }
                }
                else {
                    if (attackId==0) {
                        if (mouseDirection == Entity.MouseDirection.UP) {
                            currentFrame = FadingReality.resourceManager.playerAnimAttackUp.getKeyFrame(atkTime, true);
                        } else if(mouseDirection == Entity.MouseDirection.DOWN) {
                            currentFrame = FadingReality.resourceManager.playerAnimRunDown.getKeyFrame(atkTime, true);
                        } else if(mouseDirection == Entity.MouseDirection.LEFT) {
                            currentFrame = FadingReality.resourceManager.playerAnimAttackLeft.getKeyFrame(atkTime, true);
                        } else if(mouseDirection == Entity.MouseDirection.RIGHT) {
                            currentFrame = FadingReality.resourceManager.playerAnimAttackRight.getKeyFrame(atkTime, true);
                        }
                    } else {
                        if (mouseDirection == Entity.MouseDirection.UP) {
                            currentFrame = FadingReality.resourceManager.playerAnimAttackUp.getKeyFrame(atkTime, true);
                        } else if(mouseDirection == Entity.MouseDirection.DOWN) {
                            currentFrame = FadingReality.resourceManager.playerAnimRunDown.getKeyFrame(atkTime, true);
                        } else if(mouseDirection == Entity.MouseDirection.LEFT) {
                            currentFrame = FadingReality.resourceManager.playerAnimAttackLeft2.getKeyFrame(atkTime, true);
                        } else if(mouseDirection == Entity.MouseDirection.RIGHT) {
                            currentFrame = FadingReality.resourceManager.playerAnimAttackRight2.getKeyFrame(atkTime, true);
                        }
                    }
                }
                break;
            case WEAPON_ATTACK:
                if (mouseDirection == Entity.MouseDirection.UP) {
                    currentFrame = FadingReality.resourceManager.playerAnimGunUp.getKeyFrame(stateTime, true);
                } else if(mouseDirection == Entity.MouseDirection.DOWN) {
                    currentFrame = FadingReality.resourceManager.playerAnimGunDown.getKeyFrame(stateTime, true);
                } else if(mouseDirection == Entity.MouseDirection.LEFT) {
                    currentFrame = FadingReality.resourceManager.playerAnimGunLeft.getKeyFrame(stateTime, true);
                } else if(mouseDirection == Entity.MouseDirection.RIGHT) {
                    currentFrame = FadingReality.resourceManager.playerAnimGunRight.getKeyFrame(stateTime, true);
                }
                break;
            case DEAD:
                if (currentDirection == Entity.Direction.LEFT) {
                    currentFrame = FadingReality.resourceManager.roninAnimDeadLeft.getKeyFrame(stateTime, false);
                } else if(currentDirection == Entity.Direction.RIGHT) {
                    currentFrame = FadingReality.resourceManager.roninAnimDeadRight.getKeyFrame(stateTime, false);
                }
                break;
            case TAKING_DAMAGE:
                if (currentDirection == Entity.Direction.LEFT) {
                    currentFrame = FadingReality.resourceManager.roninDeadLeft;
                    currentFrame2 = FadingReality.resourceManager.bloodLeft.getKeyFrame(stateTime, true);
                } else if(currentDirection == Entity.Direction.RIGHT) {
                    currentFrame = FadingReality.resourceManager.roninDeadRight;
                    currentFrame2 = FadingReality.resourceManager.bloodRight.getKeyFrame(stateTime, true);
                }
                break;
            case POLICE_STOPS:
                currentFrame = FadingReality.resourceManager.policeAnimStops.getKeyFrame(stateTime, false);
                break;
            case POLICE_LOOKED_AROUND:
                currentFrame = FadingReality.resourceManager.policeAnimLookedAround.getKeyFrame(policeLookedAround, false);
                break;
            case TALK:
                currentFrame = FadingReality.resourceManager.kingAnimTalk.getKeyFrame(stateTime, false);
                break;
            case MECHANISM_OPEN_GATE:
                currentFrame = FadingReality.resourceManager.securityMechanismAnimOpenGate.getKeyFrame(stateTime, false);
                break;
            case DEATH:
                currentFrame = FadingReality.resourceManager.kingAnimDeath.getKeyFrame(stateTime, false);
                break;
            case DANCE:
                if (entityName.equals(EntityFactory.EntityName.EARTHLINGS_D1.toString())) {
                    currentFrame = FadingReality.resourceManager.earthlingsAnimDanceD1.getKeyFrame(stateTime, true);
                } else if (entityName.equals(EntityFactory.EntityName.EARTHLINGS_D2.toString())) {
                    currentFrame = FadingReality.resourceManager.earthlingsAnimDanceD2.getKeyFrame(stateTime, true);
                } else if (entityName.equals(EntityFactory.EntityName.EARTHLINGS_E1.toString())) {
                    currentFrame = FadingReality.resourceManager.earthlingsAnimDanceE1.getKeyFrame(stateTime, true);
                } else if (entityName.equals(EntityFactory.EntityName.EARTHLINGS_G1.toString())) {
                    currentFrame = FadingReality.resourceManager.earthlingsAnimDanceG1.getKeyFrame(stateTime, true);
                }  else if (entityName.equals(EntityFactory.EntityName.HOLOGRAM_G2.toString())) {
                    currentFrame = FadingReality.resourceManager.earthlingsAnimDanceHologramG2.getKeyFrame(stateTime, true);
                }
                break;
            case WIPES_GLASS:
                if (entityName.equals(EntityFactory.EntityName.BARTENDER.toString())) {
                    currentFrame = FadingReality.resourceManager.earthlingsAnimDanceBartender.getKeyFrame(stateTime, true);
                }
                break;
            case BAR_DRINK:
                currentFrame = FadingReality.resourceManager.playerAnimBarDrink.getKeyFrame(stateTime, false);
                break;
            case HURT_AMELIA:
                currentFrame = FadingReality.resourceManager.playerAnimHurtAmelia.getKeyFrame(stateTime, false);
                break;
            case DETENTION:
                currentFrame = FadingReality.resourceManager.policeAnimDetention.getKeyFrame(stateTime, true);
                break;
            case USE_RUDIMENT:
                currentFrame = FadingReality.resourceManager.playerAnimUseRudiment1.getKeyFrame(stateTime, true);
                break;
            case AMELIA_BAT_SIT:
                currentFrame = FadingReality.resourceManager.ameliaAnimBarSit.getKeyFrame(stateTime, true);
                break;
        }

    }

    protected Animation<Sprite> loadAnimantion(float frameDuration, TextureAtlas atlas, Entity.AnimationType animationType) {
        return new Animation<Sprite>(frameDuration, atlas.createSprites(animationType.toString()));
    }

    protected void setCurrentPosition(Entity entity){
        currentEntityPosition.x = 0;
        currentEntityPosition.y = 0;
        //SplashScreen splashScreen = SplashScreen.getSplashScreen();
    }

    protected void initBoundingBox(){
        boundingBox.setWidth(22); //31
        boundingBox.setHeight(11); //66
    }

    protected void updateBoundingBoxPosition(float width, float height) {
        if( currentFrame==null ) return;

        boundingBox.setCenter(currentEntityPosition.x+width, currentEntityPosition.y+height); //x+width/2, y+height/2
    }

    protected void initEntityRangeBox() {
        entityRangeBox.setWidth(24);
        entityRangeBox.setHeight(54);
    }

    protected void initBoundingBoxForObject(float width, float height){
        entityRangeBox.set(currentEntityPosition.x, currentEntityPosition.y, width, height);
    }

    protected void updateEntityRangeBox(float width, float height) {
        entityRangeBox.setCenter(currentEntityPosition.x+width, currentEntityPosition.y+height+10);
    }

    protected void setSwordRangeBox(Vector2 position, float width, float height) {
        swordRangeBox.set(position.x, position.y, width,height);
    }

    protected void updateSwordRangeBox(float width, float height) {
        switch (currentDirection) {
            case UP:
                swordRangeBox.set(currentEntityPosition.x+width-21, currentEntityPosition.y+height+13, 40, 30);
                break;
            case DOWN:
                swordRangeBox.set(currentEntityPosition.x+width-21, currentEntityPosition.y+height-13, 40, 30);
                break;
            case LEFT:
                swordRangeBox.set(currentEntityPosition.x+width-34, currentEntityPosition.y+height-16, 30, 40);
                break;
            case RIGHT:
                swordRangeBox.set(currentEntityPosition.x+width+4, currentEntityPosition.y+height-16, 30, 40);
                break;
        }
    }

    protected void initChaseRangeBox(){
        chaseRangeBox.setWidth(512); //512
        chaseRangeBox.setHeight(512); //512
    }

    protected void updateChaseRangeBox(float width, float height) {
        chaseRangeBox.setCenter(currentEntityPosition.x+width, currentEntityPosition.y+height);
    }

    protected void initAttackRangeBox(){
        attackRangeBox.setWidth(64);
        attackRangeBox.setHeight(64);
    }

    protected void updateAttackRangeBox(float width, float height) {
        attackRangeBox.setCenter(currentEntityPosition.x+width, currentEntityPosition.y+height);
    }

    protected float getAngleCenterToMouse() {
        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();

        int screenWidth = Gdx.graphics.getWidth()/2;
        int screenHeight = Gdx.graphics.getHeight()/2;

        angle = (float) Math.toDegrees(Math.atan2(screenX - screenWidth, screenY - screenHeight));

        angle = angle < 0 ? angle += 360: angle;

        angle-=90;

        return angle;
    }

    protected float getAngleCenterToPlayer(MapManager mapManager) {
        Rectangle playerRect = mapManager.getPlayer().getCurrentBoundingBox();

        int screenWidth = Gdx.graphics.getWidth()/2;
        int screenHeight = Gdx.graphics.getHeight()/2;

        float targetX = playerRect.x+64;
        float targetY = playerRect.y+64;

        float entityX = currentEntityPosition.x+64;
        float entityY = currentEntityPosition.y+64;

        Vector2 center = new Vector2(screenWidth, screenHeight);
        Vector2 targetPosition = new Vector2(targetX, targetY);
        Vector2 shipPosition = new Vector2(entityX, entityY);

        targetPosition.sub(center).nor();
        shipPosition.sub(center).nor();

        angle = MathUtils.atan2(targetPosition.y, targetPosition.x) - MathUtils.atan2(shipPosition.y, shipPosition.x);


//        Vector2 fromShipToTarget = new Vector2(targetPosition).sub(shipPosition);
//        angle = fromShipToTarget.angle();


//        angle = (float) Math.toDegrees(Math.atan2(targetX - entityX, targetY - entityY));
//
//        angle = angle < 0 ? angle += 360: angle;

//        angle-=90;

        return angle;
    }

    protected void doDash() {
        float angleRadians = MathUtils.degreesToRadians * getAngleCenterToMouse();
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);
        activeDash=true;
    }

    protected void activeDash(float delta) {
        if(activeDash){
            float dx = (delta * vector.x) * 500;
            float dy = (delta * vector.y) * 500;
            float dx2 = currentEntityPosition.x + dx;
            float dy2 = currentEntityPosition.y + dy;

            distMoved += Vector2.dst(currentEntityPosition.x, currentEntityPosition.y, dx2, dy2);
            currentEntityPosition.x+=dx;
            currentEntityPosition.y+=dy;

            if(distMoved > 100){
                distMoved=0;
                activeDash = false;
            }
        }
    }

    protected void doGotHit() {
        float angleRadians = MathUtils.degreesToRadians * getAngleCenterToMouse();
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);
        activeGotHit=true;
    }

    protected void activeGotHit(float delta) {
        if(activeGotHit){
            float dx = (delta * vector.x) * 200;
            float dy = (delta * vector.y) * 200;
            float dx2 = currentEntityPosition.x + dx;
            float dy2 = currentEntityPosition.y + dy;

            distMoved += Vector2.dst(currentEntityPosition.x, currentEntityPosition.y, dx2, dy2);
            currentEntityPosition.x+=dx;
            currentEntityPosition.y+=dy;


            if(distMoved > 40){
                distMoved=0;
                activeGotHit = false;
            }
        }
    }

    protected void doSwordAttackMove() {
        float angleRadians = MathUtils.degreesToRadians * getAngleCenterToMouse();
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);
        activeSwordAttackMove=true;
    }

    protected void activeSwordAttackMove(float delta) {
        if(activeSwordAttackMove){
            float dx = (delta * vector.x) * 300;
            float dy = (delta * vector.y) * 300;
            float dx2 = currentEntityPosition.x + dx;
            float dy2 = currentEntityPosition.y + dy;

            distMoved += Vector2.dst(currentEntityPosition.x, currentEntityPosition.y, dx2, dy2);
            currentEntityPosition.x+=dx;
            currentEntityPosition.y+=dy;

            if(distMoved > 20){
                distMoved=0;
                activeSwordAttackMove = false;
            }
        }
    }

    protected void doSwordAttackMoveForEnemy(MapManager mapManager) {
        float angleRadians = MathUtils.degreesToRadians;
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);
        activeSwordAttackMoveForEnemy=true;
    }

    protected void activeSwordAttackMoveForEnemy(float delta) {
        if(activeSwordAttackMoveForEnemy){
            float dx = (delta * vector.x) * 300;
            float dy = (delta * vector.y) * 300;
            float dx2 = currentEntityPosition.x + dx;
            float dy2 = currentEntityPosition.y + dy;

            distMoved += Vector2.dst(currentEntityPosition.x, currentEntityPosition.y, dx2, dy2);
            currentEntityPosition.x+=dx;
            currentEntityPosition.y+=dy;

            if(distMoved > 20){
                distMoved=0;
                activeSwordAttackMoveForEnemy = false;
            }
        }
    }

    protected void playerGotHit(float delta) {
        switch (currentDirection) {
            case UP:
                currentEntityPosition.y-=1000*delta;
                break;
            case DOWN:
                currentEntityPosition.y+=1000*delta;
                break;
            case RIGHT:
                currentEntityPosition.x-=1000*delta;
                break;
            case LEFT:
                currentEntityPosition.x+=1000*delta;
                break;
        }
    }


    protected Entity.MouseDirection getMouseDirection() {

        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        float angle = (float) Math.toDegrees(Math.atan2(screenX - (screenWidth/2), screenY - (screenHeight/2)));

        angle = angle < 0 ? angle += 360: angle;

        if(angle > 40 && angle < 130){
            mouseDirection = Entity.MouseDirection.RIGHT;
            currentDirection = Entity.Direction.RIGHT;
        } else if(angle > 130 && angle < 230){
            mouseDirection = Entity.MouseDirection.UP;
            currentDirection = Entity.Direction.UP;
        } else if(angle > 230 && angle < 320){
            mouseDirection = Entity.MouseDirection.LEFT;
            currentDirection = Entity.Direction.LEFT;
        } else if(angle > 320 && angle < 359 || angle > 0 && angle < 40){
            mouseDirection = Entity.MouseDirection.DOWN;
            currentDirection = Entity.Direction.DOWN;
        }

        return mouseDirection;
    }

    protected Entity.MouseDirection getMouseDirectionForGun() {

        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        float angle = (float) Math.toDegrees(Math.atan2(screenX - (screenWidth/2), screenY - (screenHeight/2)));

        angle = angle < 0 ? angle += 360: angle;

        if(angle > 50 && angle < 110){
            mouseDirection = Entity.MouseDirection.RIGHT;
            currentDirection = Entity.Direction.RIGHT;
        } else if(angle > 110 && angle < 245){
            mouseDirection = Entity.MouseDirection.UP;
            currentDirection = Entity.Direction.UP;
        } else if(angle > 245 && angle < 310){
            mouseDirection = Entity.MouseDirection.LEFT;
            currentDirection = Entity.Direction.LEFT;
        } else if(angle > 310 && angle < 359 || angle > 0 && angle < 50){
            mouseDirection = Entity.MouseDirection.DOWN;
            currentDirection = Entity.Direction.DOWN;
        }

        return mouseDirection;
    }





    protected boolean isCollisionWithMapEntities(Entity entity, MapManager mapMgr){
        tempEntities.clear();
        tempEntities.addAll(mapMgr.getCurrentMapEntities());
        tempEntities.addAll(mapMgr.getCurrentMapQuestEntities());
        boolean isCollisionWithMapEntities = false;

        for(Entity mapEntity: tempEntities){
            //Check for testing against self
            if( mapEntity.equals(entity) ){
                continue;
            }

            Rectangle targetRect = mapEntity.getCurrentBoundingBox();
            if (boundingBox.overlaps(targetRect) ){
                //Collision
                entity.sendMessage(MESSAGE.COLLISION_WITH_ENTITY);
                isCollisionWithMapEntities = true;
                break;
            }
        }
        tempEntities.clear();
        return isCollisionWithMapEntities;
    }

    protected boolean isCollision(Entity entitySource, Entity entityTarget){
        boolean isCollisionWithMapEntities = false;

        if( entitySource.equals(entityTarget) ){
            return false;
        }

        if (entitySource.getCurrentBoundingBox().overlaps(entityTarget.getCurrentBoundingBox()) ){
            //Collision
            entitySource.sendMessage(MESSAGE.COLLISION_WITH_ENTITY);
            isCollisionWithMapEntities = true;
        }

        return isCollisionWithMapEntities;
    }

    protected boolean isCollisionWithMapLayer(Entity entity, MapManager mapMgr){
        MapLayer mapCollisionLayer =  mapMgr.getCollisionLayer();

        if( mapCollisionLayer == null ){
            return false;
        }

        Rectangle rectangle = null;

        for( MapObject object: mapCollisionLayer.getObjects()){
            if(object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject)object).getRectangle();
                if( boundingBox.overlaps(rectangle) ){
                    //Collision
                    entity.sendMessage(MESSAGE.COLLISION_WITH_MAP);
                    return true;
                }
            }
        }

        return false;
    }

    public abstract void update(Entity entity, MapManager mapManager, Batch batch, float delta);

    public abstract void draw(Batch batch, float delta);

    @Override
    public void dispose() {

    }
}
