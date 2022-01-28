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
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.FadingReality;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.observer.ComponentSubject;
import com.mygdx.game.pathfinder.PathFinder;
import com.mygdx.game.skills.Skill;
import com.mygdx.game.tools.managers.ControlManager;
import com.mygdx.game.tools.managers.ResourceManager;
import com.mygdx.game.weapon.Ammo;
import com.mygdx.game.weapon.WeaponSystem;
import com.mygdx.game.world.MapManager;
import com.mygdx.game.pathfinder.Node;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Component extends ComponentSubject implements Message, InputProcessor {

    protected enum State {
        NORMAL,
        FREEZE,
        DEAD,
    }

    protected State state;

    public Entity.State currentState;
    public Entity.Direction currentDirection;
    private Entity.MouseDirection mouseDirection;

    protected OrthographicCamera camera;
    protected MapManager mapManager;

    protected ControlManager controlManager;
    public WeaponSystem weaponSystem;
    public boolean reloaded = false;
    protected PathFinder pathFinder;
    protected Node startNode;
    protected Node endNode;

    protected Array<Entity> tempEntities;

    protected EntityFactory.EntityName exoskeletonName = EntityFactory.EntityName.NONE;

    protected String entityName = "";
    public Vector2 currentEntityPosition;
    protected Vector2 runVelocity, runVelocityD;
    protected Vector2 walkVelocity, walkVelocityD;
    public boolean isGunActive = false;
    public boolean isGunActive2 = false;
    protected Array<Vector3> dashShadow;

    public Rectangle hitBox;
    public Rectangle imageBox;
    public Rectangle boundingBox;
    public Rectangle activeZoneBox;
    public Rectangle attackZoneBox;

    public Rectangle topBoundingBox;
    public Rectangle bottomBoundingBox;
    public Rectangle leftBoundingBox;
    public Rectangle rightBoundingBox;

    public boolean boolTopBoundingBox = false;
    public boolean boolBottomBoundingBox = false;
    public boolean boolLeftBoundingBox = false;
    public boolean boolRightBoundingBox = false;

    public String currentIdCollision = "";

    private int health;
    private int maxHealth;
    public int experience;
    private int damageResist;
    public int dashCharge;
    public int maxDashCharges;
    public float rudimentCharge;
    private int meleeDamageBoost;
    private int rangedDamageBoost;
    private int rudimentCooldown;
    private int weaponSpeed;
    private int critChance;
    private int healAmount;
    private int executionThreshold;
    private int damageBoost;
    private int dashSpeed;
    private int dashDist;
    private boolean executable, lowHP;
    private Array<Integer> playerSkills, availableSkills;

    public ArrayList<Ammo> activeAmmo;

    public Rectangle swordRangeBox;
    protected Rectangle chaseRangeBox;
    protected Rectangle attackRangeBox;

    protected Hashtable<Entity.AnimationType, Animation<Sprite>> animations;

    public Vector3 mouseCoordinates;

    protected Json json;
    public float atkTime = 0f;
    protected float stateTime = 0f;
    protected float policeLookedAround = 0f;
    protected Sprite currentFrame = null;
    protected Sprite currentFrame2 = null;
    protected ShapeRenderer shapeRenderer;
    protected ShapeRenderer shapeRenderer2;
    protected boolean enemyActive = false;
    protected boolean debugActive = true;

    protected float angle;
    protected Vector2 vector;
    protected boolean activeDash = false;
    protected boolean activeSwordAttackMove = false;
    protected boolean activeSwordAttackMoveForEnemy = false;
    protected boolean activeGotHit = false;
    protected float distMoved;

    protected long currentTime;
    protected int attackId = 0;
    protected int comboTimer = 600;
    protected long timeSinceLastAttack = 0;
    protected float frameAttack = 0f;

    protected float ggov = 0.2f;
    protected float speedCamMove = 15f;
    protected boolean pdaActive = false;
    protected boolean pdaDeffault = true;

    protected boolean dashing = false;
    protected float dashTime = 0;
    protected int anInt1 = 1;

    Component() {
        json = new Json();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer2 = new ShapeRenderer();

        currentState = Entity.State.IDLE;
        currentDirection = Entity.Direction.RIGHT;
        mouseDirection = Entity.MouseDirection.RIGHT;

        mouseCoordinates = new Vector3();

        currentEntityPosition = new Vector2();
        tempEntities = new Array<Entity>();
        runVelocity = new Vector2();
        runVelocityD = new Vector2();
        walkVelocity = new  Vector2();
        walkVelocityD = new Vector2();

        dashShadow = new Array<>();

        vector = new Vector2();

        hitBox = new Rectangle();
        imageBox = new Rectangle();
        boundingBox = new Rectangle();
        activeZoneBox = new Rectangle();
        attackZoneBox = new Rectangle();

        topBoundingBox = new Rectangle();
        bottomBoundingBox = new Rectangle();
        leftBoundingBox = new Rectangle();
        rightBoundingBox = new Rectangle();

        activeAmmo = new ArrayList<>();

        swordRangeBox = new Rectangle();
        chaseRangeBox = new Rectangle();
        attackRangeBox = new Rectangle();

        animations = new Hashtable<>();

        controlManager = new ControlManager();
        weaponSystem = new WeaponSystem();
        pathFinder = new PathFinder();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void reduceHealth(int damage) {
        health -= damage*((100-this.getDamageResist())/100);
        System.out.println(health);
        System.out.println(maxHealth);
        if(health<=maxHealth*20/100){
            this.setLowHP(true);
        }
    }

    public int getDamageResist() {
        return damageResist;
    }

    public void setDamageResist(int damageResist) {
        this.damageResist = damageResist;
    }

    public EntityFactory.EntityName getExoskeletonName() {
        return exoskeletonName;
    }

    public void setExoskeletonName(EntityFactory.EntityName exoskeletonName) {
        this.exoskeletonName = exoskeletonName;
    }

    public void setDashCharge(int dashCharge){this.dashCharge=dashCharge;}

    public int getDashCharge(){return this.dashCharge;}

    public void setMaxDashCharges(int maxDashCharges){this.maxDashCharges=maxDashCharges;}

    public int getMaxDashCharges(){return this.maxDashCharges;}

    public void setRudimentCharge(float rudimentCharge){this.rudimentCharge=rudimentCharge;}

    public float getRudimentCharge(){return this.rudimentCharge;}

    public int getMaxHealth() {return maxHealth;}

    public void setMaxHealth(int maxHealth) {this.maxHealth = maxHealth;}

    public int getMeleeDamageBoost() {
        return meleeDamageBoost;
    }

    public void setMeleeDamageBoost(int meleeDamageBoost) {
        this.meleeDamageBoost = meleeDamageBoost;
    }

    public int getRangedDamageBoost() {
        return rangedDamageBoost;
    }

    public void setRangedDamageBoost(int rangedDamageBoost) {
        this.rangedDamageBoost = rangedDamageBoost;
    }

    public int getRudimentCooldown() {
        return rudimentCooldown;
    }

    public void setRudimentCooldown(int rudimentCooldown) {
        this.rudimentCooldown = rudimentCooldown;
    }

    public int getWeaponSpeed() {
        return weaponSpeed;
    }

    public void setWeaponSpeed(int weaponSpeed) {
        this.weaponSpeed = weaponSpeed;
    }


    public int getCritChance() {
        return critChance;
    }

    public void setCritChance(int critChance) {
        this.critChance = critChance;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    public int getExecutionThreshold() {
        return executionThreshold;
    }

    public void setExecutionThreshold(int executionThreshold) {
        this.executionThreshold = executionThreshold;
    }

    public int getDamageBoost() {
        return damageBoost;
    }

    public void setDamageBoost(int damageBoost) {
        this.damageBoost = damageBoost;
    }

    public Array<Integer> getPlayerSkills() {return playerSkills;}

    public void setPlayerSkills(Array<Integer> playerSkills) {this.playerSkills = playerSkills;}

    public Array<Integer> getAvailableSkills() {return availableSkills;}

    public void setAvailableSkills(Array<Integer> availableSkills) {this.availableSkills = availableSkills;}

    public int getDashSpeed() {
        return dashSpeed;
    }

    public void setDashSpeed(int dashSpeed) {
        this.dashSpeed = dashSpeed;
    }

    public int getDashDist() {
        return dashDist;
    }

    public void setDashDist(int dashDist) {
        this.dashDist = dashDist;
    }

    public boolean isExecutable() {
        return executable;
    }

    public void setExecutable(boolean executable) {
        this.executable = executable;
    }

    public boolean isLowHP() {
        return lowHP;
    }

    public void setLowHP(boolean lowHP) {
        this.lowHP = lowHP;
    }

    protected void setCurrentPosition(Entity entity){
        currentEntityPosition.x = 0;
        currentEntityPosition.y = 0;
//        SplashScreen splashScreen = SplashScreen.getSplashScreen();
    }

    protected void initHitBox(Vector2 size) {
        hitBox.setWidth(size.x);
        hitBox.setHeight(size.y);
    }

    protected void initImageBox(Vector2 size) {
        imageBox.setWidth(size.x);
        imageBox.setHeight(size.y);
    }

    protected void initBoundingBox(Vector2 size){
        boundingBox.setWidth(size.x);
        boundingBox.setHeight(size.y);
    }

    protected void initActiveZoneBox(Vector2 size){
        activeZoneBox.setWidth(size.x);
        activeZoneBox.setHeight(size.y);
    }

    protected void initBorderBoundingBox(Vector2 size){
        topBoundingBox.setSize(size.x, 1);
        bottomBoundingBox.setSize(size.x, 1);
        leftBoundingBox.setSize(1, size.y);
        rightBoundingBox.setSize(1, size.y);
    }

    protected void updateHitBox() {
        int middleImageWidth = (int) imageBox.getWidth() / 2;
        int middleImageHeight = (int) imageBox.getHeight() / 2;
        hitBox.setCenter(imageBox.x + middleImageWidth, imageBox.y + middleImageHeight);
    }

    protected void updateImageBox() {
        imageBox.setPosition(currentEntityPosition.x, currentEntityPosition.y);
    }

    protected void updateBoundingBox() {
        float entityX =  currentEntityPosition.x;
        float entityY =  currentEntityPosition.y;
        int middleImageWidth = (int) imageBox.getWidth() / 2;
        int middleImageHeight = (int) imageBox.getHeight() / 2;
        int middleBoundingBoxWidth = (int) boundingBox.getWidth() / 2;
        int boundingBoxHeight = (int) boundingBox.getHeight() * 2;
        boundingBox.setPosition(entityX + middleImageWidth - middleBoundingBoxWidth, entityY + middleImageHeight - boundingBoxHeight);
    }

    protected void updateBorderBoundingBox() {
        topBoundingBox.setPosition(boundingBox.x, boundingBox.y + boundingBox.getHeight());
        bottomBoundingBox.setPosition(boundingBox.x, boundingBox.y - 1);
        leftBoundingBox.setPosition(boundingBox.x - 1, boundingBox.y);
        rightBoundingBox.setPosition(boundingBox.x + boundingBox.getWidth(), boundingBox.y);
    }

    protected void updateActiveZoneBox() {
        int middleBoundingBoxWidth = (int) boundingBox.getWidth() / 2;
        int middleBoundingBoxHeight = (int) boundingBox.getHeight() / 2;
        activeZoneBox.setCenter(boundingBox.x + middleBoundingBoxWidth, boundingBox.y + middleBoundingBoxHeight);
    }

    protected void initBoundingBoxForObject(float width, float height){
        boundingBox.set(currentEntityPosition.x, currentEntityPosition.y, width, height / 2);
    }

    protected void updateBoundingBoxForObject() {
        float entityX =  currentEntityPosition.x;
        float entityY =  currentEntityPosition.y;
        boundingBox.setPosition(entityX, entityY);
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

    protected void updateAnimations(float delta){
//        stateTime = (stateTime + delta) % 5; //Want to avoid overflow
        stateTime += delta;
        policeLookedAround += delta;
        atkTime += delta;

        currentFrame2 = FadingReality.resourceManager.nulls.getKeyFrame(stateTime, false);

        switch (currentState) {
            case IDLE:
                if (currentDirection == Entity.Direction.UP) {
                    currentFrame = animations.get(Entity.AnimationType.IDLE_UP).getKeyFrame(stateTime);
                } else if(currentDirection == Entity.Direction.DOWN) {
                    currentFrame = animations.get(Entity.AnimationType.IDLE_DOWN).getKeyFrame(stateTime);
                } else if(currentDirection == Entity.Direction.LEFT) {
                    currentFrame = animations.get(Entity.AnimationType.IDLE_LEFT).getKeyFrame(stateTime);
                } else if(currentDirection == Entity.Direction.RIGHT) {
                    currentFrame = animations.get(Entity.AnimationType.IDLE_RIGHT).getKeyFrame(stateTime);
                }
                break;
            case WALK:
                if (currentDirection == Entity.Direction.UP) {
                    currentFrame = animations.get(Entity.AnimationType.RUN_UP).getKeyFrame(stateTime); // Correct in the future
                } else if(currentDirection == Entity.Direction.DOWN) {
                    currentFrame = animations.get(Entity.AnimationType.RUN_DOWN).getKeyFrame(stateTime);  // Correct in the future
                } else if(currentDirection == Entity.Direction.LEFT) {
                    currentFrame = animations.get(Entity.AnimationType.WALK_LEFT).getKeyFrame(stateTime);
                } else if(currentDirection == Entity.Direction.RIGHT) {
                    currentFrame = animations.get(Entity.AnimationType.WALK_RIGHT).getKeyFrame(stateTime);
                }
                break;
            case RUN:
                if (currentDirection == Entity.Direction.UP) {
                    currentFrame = animations.get(Entity.AnimationType.RUN_UP).getKeyFrame(stateTime);
                } else if(currentDirection == Entity.Direction.DOWN) {
                    currentFrame = animations.get(Entity.AnimationType.RUN_DOWN).getKeyFrame(stateTime);
                } else if(currentDirection == Entity.Direction.LEFT) {
                    currentFrame = animations.get(Entity.AnimationType.RUN_LEFT).getKeyFrame(stateTime);
                } else if(currentDirection == Entity.Direction.RIGHT) {
                    currentFrame = animations.get(Entity.AnimationType.RUN_RIGHT).getKeyFrame(stateTime);
                }
                break;
            case MELEE_ATTACK:
                if (attackId == 0) {
                    if (mouseDirection == Entity.MouseDirection.UP) {
                        currentFrame = animations.get(Entity.AnimationType.MELEE_ATTACK_UP).getKeyFrame(atkTime);
                    } else if(mouseDirection == Entity.MouseDirection.DOWN) {
                        currentFrame = animations.get(Entity.AnimationType.RUN_DOWN).getKeyFrame(atkTime); // Correct in the future
                    } else if(mouseDirection == Entity.MouseDirection.LEFT) {
                        currentFrame = animations.get(Entity.AnimationType.MELEE_ATTACK_LEFT).getKeyFrame(atkTime);
                    } else if(mouseDirection == Entity.MouseDirection.RIGHT) {
                        currentFrame = animations.get(Entity.AnimationType.MELEE_ATTACK_RIGHT).getKeyFrame(atkTime);
                    }
                } else {
                    if (mouseDirection == Entity.MouseDirection.UP) {
                        currentFrame = animations.get(Entity.AnimationType.MELEE_ATTACK_UP).getKeyFrame(atkTime); // Correct in the future
                    } else if(mouseDirection == Entity.MouseDirection.DOWN) {
                        currentFrame = animations.get(Entity.AnimationType.RUN_DOWN).getKeyFrame(atkTime); // Correct in the future
                    } else if(mouseDirection == Entity.MouseDirection.LEFT) {
                        currentFrame = animations.get(Entity.AnimationType.MELEE_ATTACK_LEFT_2).getKeyFrame(atkTime);
                    } else if(mouseDirection == Entity.MouseDirection.RIGHT) {
                        currentFrame = animations.get(Entity.AnimationType.MELEE_ATTACK_RIGHT_2).getKeyFrame(atkTime);
                    }
                }
                break;
            case RANGED_ATTACK:
                if (mouseDirection == Entity.MouseDirection.UP) {
                    currentFrame = animations.get(Entity.AnimationType.RANGED_ATTACK_RIGHT).getKeyFrame(stateTime);
                } else if(mouseDirection == Entity.MouseDirection.DOWN) {
                    currentFrame = animations.get(Entity.AnimationType.RANGED_ATTACK_RIGHT).getKeyFrame(stateTime);
                } else if(mouseDirection == Entity.MouseDirection.LEFT) {
                    currentFrame = animations.get(Entity.AnimationType.RANGED_ATTACK_LEFT).getKeyFrame(stateTime);
                } else if(mouseDirection == Entity.MouseDirection.RIGHT) {
                    currentFrame = animations.get(Entity.AnimationType.RANGED_ATTACK_RIGHT).getKeyFrame(stateTime);
                }
                break;
            case WALK_SHADOW:
                currentFrame = FadingReality.resourceManager.securityMechanismAnimWalkDownShadow.getKeyFrame(stateTime, true);
                break;
            case DASH:
                if (mouseDirection == Entity.MouseDirection.UP) {
                    currentFrame = animations.get(Entity.AnimationType.RUN_UP).getKeyFrame(stateTime); // Correct in the future
                } else if(mouseDirection == Entity.MouseDirection.DOWN) {
                    currentFrame = animations.get(Entity.AnimationType.RUN_DOWN).getKeyFrame(stateTime); // Correct in the future
                } else if(mouseDirection == Entity.MouseDirection.LEFT) {
                    currentFrame = animations.get(Entity.AnimationType.RUN_LEFT).getKeyFrame(stateTime); // Correct in the future
                } else if(mouseDirection == Entity.MouseDirection.RIGHT) {
                    currentFrame = FadingReality.resourceManager.playerAnimDashRight.getKeyFrame(stateTime, true);
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
                currentFrame = animations.get(Entity.AnimationType.STOPS).getKeyFrame(stateTime);
                break;
            case POLICE_LOOKED_AROUND:
                currentFrame = animations.get(Entity.AnimationType.LOOKED_AROUND).getKeyFrame(stateTime);
                break;
            case DETENTION:
                currentFrame = animations.get(Entity.AnimationType.DETENTION).getKeyFrame(stateTime);
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
            case USE_RUDIMENT:
                currentFrame = FadingReality.resourceManager.playerAnimUseRudiment1.getKeyFrame(stateTime, true);
                break;
            case AMELIA_BAT_SIT:
                currentFrame = FadingReality.resourceManager.ameliaAnimBarSit.getKeyFrame(stateTime, true);
                break;
        }

    }

    protected Animation<Sprite> loadAnimation(float frameDuration, ResourceManager.AtlasType atlasType, Entity.AnimationType animationType, Animation.PlayMode playMode) {
        TextureAtlas atlas = FadingReality.resourceManager.getAtlas(atlasType);
        Animation<Sprite> animation = new Animation<Sprite>(frameDuration, atlas.createSprites(animationType.toString()));
        animation.setPlayMode(playMode);
        return animation;
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
        Rectangle playerRect = mapManager.getPlayer().getBoundingBox();

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

        return angle;
    }

    protected void doDash() {
        float angleRadians = MathUtils.degreesToRadians * getAngleCenterToMouse();
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);
        activeDash = true;
    }

    protected void gotHit() {
        float angleRadians = MathUtils.degreesToRadians * getAngleCenterToMouse();
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);
        activeGotHit = true;
    }

    protected void meleeAttackMove() {
        float angleRadians = MathUtils.degreesToRadians * getAngleCenterToMouse();
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);
        activeSwordAttackMove = true;
    }

    protected void meleeAttackMoveForEnemy(MapManager mapManager) {
        float angleRadians = MathUtils.degreesToRadians;
        vector.x = MathUtils.cos(angleRadians);
        vector.y = MathUtils.sin(angleRadians);
        activeSwordAttackMoveForEnemy = true;
    }

    protected void updateShifts(MapManager mapManager, float delta, float repulsionDistance) {
        if(activeDash){
            float dx = (delta * vector.x) * (500+dashSpeed);
            float dy = (delta * vector.y) * (500+dashSpeed);
            float dx2 = currentEntityPosition.x + dx;
            float dy2 = currentEntityPosition.y + dy;

            distMoved += Vector2.dst(currentEntityPosition.x, currentEntityPosition.y, dx2, dy2);
            currentEntityPosition.x+=dx;
            currentEntityPosition.y+=dy;

            if(distMoved > (100+dashDist)){
                distMoved=0;
                activeDash = false;
                System.out.println("Dash dist:" + dashDist);
            }
        }
        if(activeGotHit){
            float dx = (delta * vector.x) * 200;
            float dy = (delta * vector.y) * 200;
            float dx2 = currentEntityPosition.x + dx;
            float dy2 = currentEntityPosition.y + dy;

            distMoved += Vector2.dst(currentEntityPosition.x, currentEntityPosition.y, dx2, dy2);
            currentEntityPosition.x+=dx;
            currentEntityPosition.y+=dy;

            if(distMoved > repulsionDistance){
                distMoved=0;
                activeGotHit = false;
            }
        }
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

            Rectangle targetRect = mapEntity.getBoundingBox();
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

    protected void updateCollisionWithMapEntities(Entity entity, MapManager mapMgr){
        tempEntities.clear();
        tempEntities.addAll(mapMgr.getCurrentMapEntities());
        tempEntities.addAll(mapMgr.getCurrentMapQuestEntities());

        for(Entity mapEntity: tempEntities){
            if( mapEntity.equals(entity) ){
                continue;
            }

            Rectangle targetRect = mapEntity.getBoundingBox();

            if (topBoundingBox.overlaps(targetRect) ){
                boolTopBoundingBox = true;
            }
            if (bottomBoundingBox.overlaps(targetRect) ){
                boolBottomBoundingBox = true;
            }
            if (leftBoundingBox.overlaps(targetRect) ){
                boolLeftBoundingBox = true;
            }
            if (rightBoundingBox.overlaps(targetRect) ){
                boolRightBoundingBox = true;
            }
        }
        tempEntities.clear();
    }

    protected boolean isCollision(Entity entitySource, Entity entityTarget){
        boolean isCollisionWithMapEntities = false;

        if( entitySource.equals(entityTarget) ){
            return false;
        }

        if (entitySource.getBoundingBox().overlaps(entityTarget.getBoundingBox()) ){
            //Collision
            entitySource.sendMessage(MESSAGE.COLLISION_WITH_ENTITY);
            isCollisionWithMapEntities = true;
        }

        return isCollisionWithMapEntities;
    }

    protected boolean isCollisionWithMapLayer(Entity entity, MapManager mapMgr){
        MapLayer mapCollisionLayer = mapMgr.getCollisionLayer();

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

    protected void updateCurrentCollision(MapManager mapMgr) {
        MapLayer mapCollisionLayer = mapMgr.getCollisionLayer();

        if( mapCollisionLayer == null ){
            return;
        }

        Rectangle rectangle = null;

        for( MapObject object: mapCollisionLayer.getObjects()){
            if(object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject)object).getRectangle();
                String objectID = (String) object.getProperties().get("objectID");
                if( boundingBox.overlaps(rectangle) ){
                    currentIdCollision = objectID;
                }

                if (!boundingBox.overlaps(rectangle) && objectID.equals(currentIdCollision)) {
                    currentIdCollision = "";
                }
            }
        }
    }

    protected boolean updateDoorCollision(Entity entity, MapManager mapMgr){
        MapLayer mapObjectsLayer = mapMgr.getMapObjectsLayer();

        if( mapObjectsLayer == null ){
            return false;
        }

        Rectangle rectangle = null;

        for( MapObject object: mapObjectsLayer.getObjects()){
            TextureMapObject textureMapObject = (TextureMapObject) object;

        }

        return false;
    }

    protected void updateBoundingBoxPosition(Vector2 position){
        float minX = position.x;
        float minY = position.y;

        boundingBox.setCenter(minX, minY);
    }

    public abstract void update(Entity entity, MapManager mapManager, Batch batch, float delta);

    public abstract void draw(Batch batch, float delta);

    @Override
    public void dispose() {

    }
}
