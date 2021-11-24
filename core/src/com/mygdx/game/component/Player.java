package com.mygdx.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.UI.PlayerHUD;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityConfig;
import com.mygdx.game.inventory.InventoryItem;
import com.mygdx.game.inventory.InventoryItem.ItemID;
import com.mygdx.game.observer.ComponentObserver;
import com.mygdx.game.tools.Rumble;
import com.mygdx.game.tools.Toast;
import com.mygdx.game.tools.managers.ControlManager;
import com.mygdx.game.tools.managers.ResourceManager;
import com.mygdx.game.weapon.Weapon;
import com.mygdx.game.weapon.WeaponFactory;
import com.mygdx.game.weapon.WeaponSystem;
import com.mygdx.game.pathfinder.Node;
import com.mygdx.game.world.MapManager;

import java.util.HashMap;


public class Player extends Component {

    private boolean isLeftButtonPressed = false;
    private boolean isRightButtonPressed = false;

    private Vector2 previousPosition;

    public Player(){
        initEntityRangeBox();
        state = State.NORMAL;
        previousPosition = new Vector2(0,0);
        controlManager = new ControlManager();
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if(string.length == 0) return;

        if(string.length == 2) {
            if(string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())) {
                currentEntityPosition = json.fromJson(Vector2.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
                currentEntityPosition = json.fromJson(Vector2.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.CURRENT_STATE.toString())) {
                currentState = json.fromJson(Entity.State.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.INTERACTION_WITH_ENTITY.toString())) {

            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_CONFIG.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
                initHitBox(entityConfig.getHitBox());
                initImageBox(entityConfig.getImageBox());
                initBoundingBox(entityConfig.getBoundingBox());
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_ALL_AMMO_COUNT.toString())) {
                java.util.Map<String, Integer> allAmmoCount = json.fromJson(HashMap.class, string[1]);
                WeaponSystem.setBagAmmunition(allAmmoCount);
            } else if(string[0].equalsIgnoreCase(MESSAGE.SET_MELEE_WEAPON.toString())) {
                String weaponIDStr = json.fromJson(String.class, string[1]);
                ItemID weaponID = InventoryItem.ItemID.valueOf(weaponIDStr);
                Weapon weapon = WeaponFactory.getInstance().getWeapon(weaponID);
                weaponSystem.setMeleeWeapon(weapon);
            } else if(string[0].equalsIgnoreCase(MESSAGE.SET_RANGED_WEAPON.toString())) {
                String weaponIDStr = json.fromJson(String.class, string[1]);
                String[] splitStr = weaponIDStr.split(MESSAGE_TOKEN_2);
                ItemID weaponID = InventoryItem.ItemID.valueOf(splitStr[0]);
                Weapon weapon = WeaponFactory.getInstance().getWeapon(weaponID);
                weaponSystem.setRangedWeapon(weapon);
                weaponSystem.setStartAmmoCountInMagazine(Integer.parseInt(splitStr[1]));
            } else if(string[0].equalsIgnoreCase(MESSAGE.REMOVE_MELEE_WEAPON.toString())) {
                weaponSystem.setMeleeWeapon(null);
            } else if(string[0].equalsIgnoreCase(MESSAGE.REMOVE_RANGED_WEAPON.toString())) {
                weaponSystem.setRangedWeapon(null);
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
        camera = mapManager.getCamera();

        mapManager.getCamera().unproject(mouseCoordinates.set(Gdx.input.getX(), Gdx.input.getY(), 0));
//        updatePortalLayerActivation(mapManager, delta);

        //Player has moved
        if(previousPosition.x != currentEntityPosition.x || previousPosition.y != currentEntityPosition.y){
            notify("", ComponentObserver.ComponentEvent.PLAYER_HAS_MOVED);
            previousPosition = currentEntityPosition.cpy();
        }

        weaponSystem.update(delta, this);

        activeDash(delta);
        activeSwordAttackMove(delta);
        activeGotHit(delta);
        setSwordRangeBox(new Vector2(10000,10000), 0,0);

        tempEntities.clear();
        tempEntities.addAll(mapManager.getCurrentMapEntities());
        tempEntities.addAll(mapManager.getCurrentMapQuestEntities());

        for(Entity mapEntity : tempEntities) {
            Rectangle entitySwordRangeBox = mapEntity.getCurrentSwordRangeBox();
            if (entitySwordRangeBox.overlaps(entityRangeBox)) {
                stateTime = 0f;
                playerGotHit(delta);
                health-=25;
                state = State.FREEZE;
                currentState = Entity.State.TAKING_DAMAGE;

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        state = State.NORMAL;
                    }}, 0.3f);
                Rumble.rumble(5, .1f, 0, Rumble.State.SWORD);
                PlayerHUD.toastShort("Enemy HIT", Toast.Length.SHORT);
            }
        }

        //INPUT
        input(entity);

        //PHYSICS
        if (Rumble.getRumbleTimeLeft() > 0){
            Rumble.tick(Gdx.graphics.getDeltaTime());
            camera.translate(Rumble.getPos());
        } else if (pdaActive) {
//            if (currentEntityPosition.x < currentEntityPosition.)
//            camera.translate(1,0);
        } else {
            Vector2 screenPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 normalizedScreen = new Vector2(screenPos.x / Gdx.graphics.getWidth(), screenPos.y / Gdx.graphics.getHeight());
            normalizedScreen.sub(.5f, .5f);
            float offset = 150;
            Vector3 cameraMoved = new Vector3(Math.round(currentEntityPosition.x) + normalizedScreen.x * offset+64, Math.round(currentEntityPosition.y) - normalizedScreen.y * offset+64,0);
            if(currentState == Entity.State.USE_RUDIMENT ) {
                cameraMoved.set(currentEntityPosition.x + normalizedScreen.x * offset+128, currentEntityPosition.y - normalizedScreen.y * offset+128,0);
            }
            camera.position.lerp(cameraMoved, 0.1f); //0.05f //delta
//            camera.position.set(cameraMoved);
//            camera.position.set(currentEntityPosition.x+64, currentEntityPosition.y+64, 0f);
        }
        camera.update();

        //PDA SWITCH
        if (pdaActive) {
            if (camera.zoom  > ggov){
                camera.zoom -= delta * speedCamMove * 0.03f;
                camera.translate(1,1);
            }
        }
        if (!pdaActive) {
            if (camera.zoom  <= 0.1f){
                camera.zoom += delta * speedCamMove * 0.03f;
            }
        }


        if (currentState == Entity.State.RUN){
        } else if(currentState == Entity.State.MELEE_ATTACK ) {
        } else if(currentState == Entity.State.USE_RUDIMENT ) {
        } else{
        }

        updateHitBox();
        updateImageBox();
        updateBoundingBox();
        updateEntityRangeBox(64,64);

        //GUN ACTIVE
        if(isGunActive) {
            getMouseDirectionForGun();
        }

//        updateSwordRangeBox(64,64);

        //DASH
        if(dashing) {
            if(Gdx.graphics.getFrameId() % (int) ((Gdx.graphics.getFramesPerSecond()*.02f)+1) == 0) {  //def .05f
                dashShadow.add(new Vector3(currentEntityPosition.x, currentEntityPosition.y, 1));
                anInt1++;
            }
            dashTime += delta;
        }
        if(dashTime > 0.2f) {  //def .02f
            dashTime = 0;
            dashing = false;
            anInt1 = 1;
        }

        //GRAPHICS
        updateAnimations(delta);

//        mapManager.getCurrentMap().updateGridOjc();

        this.mapManager = mapManager;
    }

    public boolean isCollisionWithMapLayer2(Entity entity, MapManager mapManager){
        MapLayer mapCollisionLayer = mapManager.getCollisionLayer();

        if( mapCollisionLayer == null ){
            return false;
        }

        Rectangle rectangle = null;

        for(MapObject object: mapCollisionLayer.getObjects()){
            if(object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject)object).getRectangle();
                if(boundingBox.overlaps(rectangle)){
                    return true;
                }
            }
        }

        return false;
    }

    private void input(Entity entity) {

        switch (state) {
            case NORMAL:
                if (true) {
                    if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                        Gdx.app.exit();
                    } else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
                        PlayerHUD.toastShort("Pressed T", Toast.Length.SHORT);
                        entity.sendMessage(MESSAGE.INTERACTION_WITH_ENTITY);
                    }

                    if (!PlayerHUD.browserUI.isVisible()) {
                        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
                            PlayerHUD.toastShort("Pressed TAB", Toast.Length.SHORT);
                            pdaActive = !pdaActive;
                        }
                    }

                    if (!PlayerHUD.pdaui.isVisible() && !PlayerHUD.browserUI.isVisible()) {
                        if (Gdx.input.isKeyPressed(Input.Keys.W) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))) {
                            currentState = Entity.State.RUN;
                            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                                currentDirection = Entity.Direction.RIGHT;
                                currentEntityPosition.y += runVelocityD.y;
                                currentEntityPosition.x += runVelocityD.x;
                            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                                currentDirection = Entity.Direction.LEFT;
                                currentEntityPosition.y += runVelocityD.y;
                                currentEntityPosition.x -= runVelocityD.x;
                            } else {
                                currentDirection = Entity.Direction.UP;
                                currentEntityPosition.y += runVelocity.y;
                            }
                        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))) {
                            currentState = Entity.State.RUN;
                            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                                currentDirection = Entity.Direction.RIGHT;
                                currentEntityPosition.y -= runVelocityD.y;
                                currentEntityPosition.x += runVelocityD.x;
                            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                                currentDirection = Entity.Direction.LEFT;
                                currentEntityPosition.y -= runVelocityD.y;
                                currentEntityPosition.x -= runVelocityD.x;
                            } else {
                                currentDirection = Entity.Direction.DOWN;
                                currentEntityPosition.y -= runVelocity.y;
                            }
                        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))) {
                            currentState = Entity.State.RUN;
                            currentDirection = Entity.Direction.LEFT;
                            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                                currentEntityPosition.x -= runVelocityD.x;
                                currentEntityPosition.y += runVelocityD.y;
                            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                                currentEntityPosition.x -= runVelocityD.x;
                                currentEntityPosition.y -= runVelocityD.y;
                            } else {
                                currentEntityPosition.x -= runVelocity.x;
                            }
                        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))) {
                            currentState = Entity.State.RUN;
                            currentDirection = Entity.Direction.RIGHT;
                            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                                currentEntityPosition.x += runVelocityD.x;
                                currentEntityPosition.y += runVelocityD.y;
                            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                                currentEntityPosition.x += runVelocityD.x;
                                currentEntityPosition.y -= runVelocityD.y;
                            } else {
                                currentEntityPosition.x += runVelocity.x;
                            }

                        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                            currentState = Entity.State.WALK;
                            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                                currentDirection = Entity.Direction.RIGHT;
                                currentEntityPosition.y += walkVelocityD.y;
                                currentEntityPosition.x += walkVelocityD.x;
                            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                                currentDirection = Entity.Direction.LEFT;
                                currentEntityPosition.y += walkVelocityD.y;
                                currentEntityPosition.x -= walkVelocityD.x;
                            } else {
                                currentDirection = Entity.Direction.UP;
                                currentEntityPosition.y += walkVelocity.y;
                            }
                        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                            currentState = Entity.State.WALK;
                            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                                currentDirection = Entity.Direction.RIGHT;
                                currentEntityPosition.y -= walkVelocityD.y;
                                currentEntityPosition.x += walkVelocityD.x;
                            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                                currentDirection = Entity.Direction.LEFT;
                                currentEntityPosition.y -= walkVelocityD.y;
                                currentEntityPosition.x -= walkVelocityD.x;
                            } else {
                                currentDirection = Entity.Direction.DOWN;
                                currentEntityPosition.y -= walkVelocity.y;
                            }
                        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                            currentState = Entity.State.WALK;
                            currentDirection = Entity.Direction.LEFT;
                            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                                currentEntityPosition.x -= walkVelocityD.x;
                                currentEntityPosition.y += walkVelocityD.y;
                            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                                currentEntityPosition.x -= walkVelocityD.x;
                                currentEntityPosition.y -= walkVelocityD.y;
                            } else {
                                currentEntityPosition.x -= walkVelocity.x;
                            }
                        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                            currentState = Entity.State.WALK;
                            currentDirection = Entity.Direction.RIGHT;
                            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                                currentEntityPosition.x += walkVelocityD.x;
                                currentEntityPosition.y += walkVelocityD.y;
                            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                                currentEntityPosition.x += walkVelocityD.x;
                                currentEntityPosition.y -= walkVelocityD.y;
                            } else {
                                currentEntityPosition.x += walkVelocity.x;
                            }
                        } else {
                            currentState = Entity.State.IDLE;
                            isGunActive2 = false;
                            isGunActive = false;
                        }

                        //DASH
                        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                            stateTime = 0f;
                            state = State.FREEZE;
                            currentState = Entity.State.DASH;
                            getMouseDirection();
                            doDash();

                            dashing = true;

                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    state = State.NORMAL;
                                }}, 0.38f);
                        }

                        //RUDIMENT
                        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                            currentEntityPosition.x -= 64;
                            currentEntityPosition.y -= 64;
                            stateTime = 0f;
                            state = State.FREEZE;
                            currentState = Entity.State.USE_RUDIMENT;
                            PlayerHUD.toastShort("Use Rudiment", Toast.Length.SHORT);

                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    Rumble.rumble(15f, 0.1f, -1, Rumble.State.SWORD);
                                }}, 0.5f);

                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    currentEntityPosition.x += 64;
                                    currentEntityPosition.y += 64;
                                    state = State.NORMAL;
                                }}, 1.1f);
                        }

                        //MELEE ATTACK
                        if (isLeftButtonPressed && weaponSystem.meleeIsActive()) {
                            isLeftButtonPressed = false;

                            currentTime = System.currentTimeMillis();
                            if ((currentTime - timeSinceLastAttack) < comboTimer) {
                                if (attackId == 1)
                                    attackId = 0;
                                else {
                                    attackId++;
                                }
                            } else {
                                attackId = 0;
                            }
                            System.out.println("LMB: Attack " + attackId);
                            timeSinceLastAttack = System.currentTimeMillis();

                            atkTime = 0f;
                            state = State.FREEZE;
                            currentState = Entity.State.MELEE_ATTACK;
                            getMouseDirection();
                            doSwordAttackMove();

                            if (attackId == 0) {
                                frameAttack = 0.55f;
                            } else {
                                frameAttack = 0.65f;
                            }

                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    state = State.NORMAL;
                                }
                            }, frameAttack); //0.44

                            updateSwordRangeBox(64, 64);
                        } else {
                            isLeftButtonPressed = false;
                        }

                        //RANGED ATTACK
                        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && weaponSystem.rangedIsActive() && weaponSystem.getRangedWeapon().getAmmoCountInMagazine() != 0) {
                            currentState = Entity.State.RANGED_ATTACK;
                            isGunActive2 = true;
                            isGunActive = true;

                            notify(json.toJson(weaponSystem.getRangedWeapon().getAmmoCountInMagazine() - 1), ComponentObserver.ComponentEvent.PLAYER_SHOT);

                            state = State.FREEZE;
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    state = State.NORMAL;
                                }}, weaponSystem.getRangedWeapon().getAttackTime());
                        }

                        //RELOAD WEAPON
                        if(Gdx.input.isKeyJustPressed(Input.Keys.R) && weaponSystem.rangedIsActive()) {

                            state = State.FREEZE;
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    weaponSystem.reloadWeapon();

                                    reloaded = true;

                                    state = State.NORMAL;
                                }}, 0.5f);

                        }

                        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && weaponSystem.rangedIsActive() && weaponSystem.getRangedWeapon().getAmmoCountInMagazine() == 0) {
                            notify(json.toJson(0), ComponentObserver.ComponentEvent.PLAYER_SHOT);
                        }
                        if (reloaded) {
                            reloaded = false;
                            notify(json.toJson(weaponSystem.getRangedWeapon().getAmmoCountInMagazine()), ComponentObserver.ComponentEvent.PLAYER_SHOT);
                        }

                    }
                } else {
                    stateTime = 0f;
                    state = State.DEAD;
                    currentState = Entity.State.DEAD;
                }
                break;
            case FREEZE:
                if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
                    stateTime = 0f;
                    state = State.NORMAL;
                }
                break;
            case DEAD:
                break;
        }
    }

    @Override
    public void draw(Batch batch, float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) {
            debugActive = !debugActive;
        }
        if (debugActive) {
            debug(true, true,true,true);
        }

        batch.begin();
        //DASH
        for(Vector3 shadow : dashShadow) {
            batch.setColor(0.05f,0.7f, 0.8f, shadow.z);
            batch.draw(currentFrame, shadow.x, shadow.y);
            shadow.z -= Gdx.graphics.getDeltaTime()*6;  //def *2
        }
        batch.setColor(Color.WHITE);
        for(int i = 0; i < dashShadow.size; i++) {
            if(dashShadow.get(i).z <= 0) {
                dashShadow.removeIndex(i);
            }
        }

        if(currentDirection == Entity.Direction.UP) {
            if (weaponSystem.rangedIsActive()) {
                if (isGunActive){
                    weaponSystem.getRangedWeapon().drawRotatedGun(batch, delta);
                }
                weaponSystem.getRangedWeapon().drawAmmo(batch);
            }
            batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
            batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y);
        } else {
            batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y); // player
            batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y); // blood
            if (weaponSystem.rangedIsActive()) {
                weaponSystem.getRangedWeapon().drawAmmo(batch);
                if (isGunActive){
                    weaponSystem.getRangedWeapon().drawRotatedGun(batch, delta);
                }
            }
        }
        batch.end();
    }

    public void debug(boolean activeGrid, boolean activeHitBox, boolean activeImageBox, boolean activeBoundingBox) {
        if (activeGrid) {
            Array<Array<Node>> grid = mapManager.getCurrentMap().getGrid();
            if(grid == null && grid.size == 0) return;
            shapeRenderer2.setProjectionMatrix(camera.combined);
            shapeRenderer2.begin(ShapeRenderer.ShapeType.Line);
            for (int y = 0; y < grid.size; y++) {
                for (int x = 0; x < grid.get(y).size; x++) {
                    if (grid.get(y).get(x).getType() == Node.GridType.CLOSE) {
                        shapeRenderer2.setColor(Color.RED);
                        Rectangle rectangle = grid.get(y).get(x).rectangle;
                        shapeRenderer2.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
                    } else {
//                    shapeRenderer2.setColor(Color.GREEN);
//                    Rectangle rectangle = grid.get(y).get(x).rectangle;
//                    shapeRenderer2.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
                    }
                }
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
        if (keycode == Input.Keys.R) {
            if (weaponSystem.rangedIsActive() && weaponSystem.getAmmoCountFromBag(weaponSystem.getRangedWeapon().getAmmoID()) == 0) {
                PlayerHUD.toastShort("No " + weaponSystem.getRangedWeapon().getAmmoID().getValue() + " in bag", Toast.Length.LONG);
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            isLeftButtonPressed = true;

            if(weaponSystem.meleeIsActive()) {

            } else {
                PlayerHUD.toastShort("Melee Weapon is not Active", Toast.Length.LONG);
            }
        } else if (button == Input.Buttons.RIGHT) {
            isRightButtonPressed = true;

            if (weaponSystem.rangedIsActive()) {
                if (weaponSystem.getAmmoCountFromBag(weaponSystem.getRangedWeapon().getAmmoID()) == 0 && weaponSystem.getRangedWeapon().getAmmoCountInMagazine() == 0) {
                    PlayerHUD.toastShort("No " + weaponSystem.getRangedWeapon().getAmmoID().getValue() + " in bag", Toast.Length.LONG);
                } else if (weaponSystem.getRangedWeapon().getAmmoCountInMagazine() == 0) {
                    PlayerHUD.toastShort("Press R to Reload weapon", Toast.Length.LONG);
                }
            } else {
                PlayerHUD.toastShort("Ranged Weapon is not Active", Toast.Length.LONG);
            }

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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
        if (amount==-1){
            camera.zoom -= 0.03;
        } else {
            camera.zoom += 0.03;
        }
        return false;
    }

    @Override
    public void dispose(){
        Gdx.input.setInputProcessor(null);
    }
}
