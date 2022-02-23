package com.mygdx.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityConfig;
import com.mygdx.game.observer.ComponentObserver;
import com.mygdx.game.managers.ResourceManager;
import com.mygdx.game.pathfinder.Node;
import com.mygdx.game.weapon.Ammo;
import com.mygdx.game.weapon.Weapon;
import com.mygdx.game.weapon.WeaponFactory;
import com.mygdx.game.world.MapManager;

public class Enemy extends Component {

    public Enemy(){
        state = State.NORMAL;
        this.setExecutable(true);
        this.setLowHP(false);
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
            } else if (string[0].equalsIgnoreCase(MESSAGE.STUN.toString())) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        state = State.FREEZE;
                    }
                }, 0.5f);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        state = State.NORMAL;
                    }
                }, 2f);
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
                initHitBox(entityConfig.getHitBox());
                initImageBox(entityConfig.getImageBox());
                initBoundingBox(entityConfig.getBoundingBox());
                initActiveZoneBox(entityConfig.getActiveZoneBox());
                initAttackZoneBox(entityConfig.getAttackZoneBox());
                initVisibilityZoneBox(entityConfig.getVisibilityZoneBox());

                setHealth(entityConfig.getHealth());
                setMaxHealth(entityConfig.getMaxHealth());
                if (entityConfig.getWeaponID() != null) {
                    Weapon weapon = WeaponFactory.getInstance().getWeapon(entityConfig.getWeaponID());
                    weaponSystem.setRangedWeapon(weapon);
                }
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_ANIMATIONS.toString())) {
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
        this.mapManager = mapManager;
        this.camera = mapManager.getCamera();

//        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) enemyActive = !enemyActive;
//        if (enemyActive) state = State.NORMAL;
//        else state = State.FREEZE;

        updateHitBox();
        updateImageBox();
        updateBoundingBox();
        updateActiveZoneBox();
        updateAttackZoneBox();
        updateVisibilityZoneBox();
        updateShifts(mapManager, delta, 10);

        switch (state) {
            case NORMAL:
                Entity player = mapManager.getPlayer();
                Rectangle playerBoundingBox = player.getBoundingBox();

                if (activeZoneBox.overlaps(playerBoundingBox)) {
                    if (isLowHP()) {
//                        System.out.println(entity.getEntityConfig().getEntityID() + " LOW HP");
                    }
                } else if (attackZoneBox.overlaps(playerBoundingBox)) {
                    currentState = Entity.State.RANGED_ATTACK;
                    isGunActive2 = true;
                } else if (visibilityZoneBox.overlaps(playerBoundingBox)) {
                    currentState = Entity.State.RUN;
                    updateGrid(playerBoundingBox);
                    followPath();
                } else {
                    currentState = Entity.State.IDLE;
                }

                if (visibilityZoneBox.overlaps(playerBoundingBox)) {
                    getDirectionToPlayer(player);
                }

                updateAmmoHit(player);
                updateSwordHit(player);
                updateHealth(entity, player);
                weaponSystem.updateForEnemy(delta,this, player);
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

    private void updateGrid(Rectangle playerBoundingBox) {
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
    }

    private void followPath(){
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

    private void updateAmmoHit(Entity player) {
        Weapon weapon = player.getRangeWeapon();

        for(Ammo ammo: player.getActiveAmmo()){
            Polygon playerAmmoBoundingBox = ammo.getPolygon();
            Polygon enemyHitBox = new Polygon(new float[] { 0, 0, hitBox.width, 0, hitBox.width, hitBox.height, 0, hitBox.height });
            enemyHitBox.setPosition(hitBox.x, hitBox.y);
            if (Intersector.overlapConvexPolygons(enemyHitBox, playerAmmoBoundingBox)) {
                gotHit();
                ammo.setRemove(true);
                reduceHealth(weapon.getRandomDamage() + player.getRangedDamageBoost() + player.getDamageBoost());
            }
        }
    }

    private void updateSwordHit(Entity player) {
        Weapon weapon = player.getMeleeWeapon();

        Polygon playerSwordBoundingBox = player.getSwordPolygon();
        Polygon enemyHitBox = new Polygon(new float[] { 0, 0, hitBox.width, 0, hitBox.width, hitBox.height, 0, hitBox.height });
        enemyHitBox.setPosition(hitBox.x, hitBox.y);
        if (Intersector.overlapConvexPolygons(enemyHitBox, playerSwordBoundingBox)) {
            gotHit();
            reduceHealth(weapon.getRandomDamage() + player.getDamageBoost());
            System.out.println("HIT");
            System.out.println(getHealth());
        }
    }

    private void updateHealth(Entity entity, Entity player) {
        if (getHealth() <= 0) {
            mapManager.setCurrentMapEntity(entity); // Задать текущего персонажа на карте
            player.sendMessage(MESSAGE.ENEMY_KILLED);
            notify(json.toJson(entity.getEntityConfig()), ComponentObserver.ComponentEvent.ENEMY_DEAD);
            state = State.DEATH;
        }
    }

    @Override
    public void draw(Batch batch, float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) debugActive = !debugActive;
        if (debugActive) debug(false,false,false, false,
                false,false,false, false,
                true,false,true,true,true, true);

        batch.begin();
        if(currentDirection == Entity.Direction.UP) {
            if (weaponSystem.rangedIsActive()) {
                weaponSystem.getRangedWeapon().drawAmmo(batch, camera);
            }
            batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
            batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y);
        } else {
            batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y); // player
            batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y); // blood
            if (weaponSystem.rangedIsActive()) {
                weaponSystem.getRangedWeapon().drawAmmo(batch, camera);
            }
        }
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

}
