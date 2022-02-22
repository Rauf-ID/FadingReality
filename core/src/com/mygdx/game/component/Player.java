package com.mygdx.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.UI.PlayerHUD;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityConfig;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.Item.ItemID;
import com.mygdx.game.observer.ComponentObserver;
import com.mygdx.game.rudiment.Rudiment;
import com.mygdx.game.rudiment.RudimentFactory;
import com.mygdx.game.rudiment.UniqueRudiment;
import com.mygdx.game.tools.Rumble;
import com.mygdx.game.tools.Toast;
import com.mygdx.game.managers.ControlManager;
import com.mygdx.game.managers.ResourceManager;
import com.mygdx.game.weapon.Ammo;
import com.mygdx.game.weapon.Weapon;
import com.mygdx.game.weapon.WeaponFactory;
import com.mygdx.game.weapon.WeaponSystem;
import com.mygdx.game.world.MapFactory;
import com.mygdx.game.world.MapManager;

import java.util.HashMap;

public class Player extends Component {

    private boolean isLeftButtonPressed = false;
    private boolean isRightButtonPressed = false;
    private boolean usingRudiment = false;
    private boolean rudimentLock = false;
    private float timer, dashTimer, executionTimer, executionDamageResistTimer;
    private boolean startExecutionTimer = false;
    private boolean executionDamageResist = false;
    private int executionScore = 0;

    public Player() {
        state = State.NORMAL;
        this.rudimentCharge = 4;
        controlManager = new ControlManager();
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if(string.length == 0) return;

        if(string.length==1) {
            if (string[0].equalsIgnoreCase(MESSAGE.ENEMY_KILLED.toString())) {
                setHealth(getHealth() + getHealAmount());
                if(getHealth() > getMaxHealth()){
                    setHealth(getMaxHealth());
                }
            }
        }

        if(string.length == 2) {
            if(string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())) {
                currentEntityPosition = json.fromJson(Vector2.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_STATE.toString())) {
                currentState = json.fromJson(Entity.State.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.SET_EXOSKELETON.toString())){
                setExoskeletonName(json.fromJson(EntityFactory.EntityName.class, string[1]));
                Entity exoskeleton = EntityFactory.getInstance().getExoskeletonByName(getExoskeletonName());
                EntityConfig exoskeletonEntityConfig = exoskeleton.getEntityConfig();
                equipExoskeleton(exoskeletonEntityConfig);

                Array<EntityConfig.AnimationConfig> animationConfigs = exoskeletonEntityConfig.getAnimationConfig();

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
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_CONFIG.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
                initHitBox(entityConfig.getHitBox());
                initImageBox(entityConfig.getImageBox());
                initBoundingBox(entityConfig.getBoundingBox());
                initBorderBoundingBox(entityConfig.getBoundingBox());
//                setDamageResist(entityConfig.getDamageResist());
                unEquipExoskeleton(entityConfig);
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_ALL_AMMO_COUNT.toString())) {
                java.util.Map<String, Integer> allAmmoCount = json.fromJson(HashMap.class, string[1]);
                WeaponSystem.setBagAmmunition(allAmmoCount);
            } else if(string[0].equalsIgnoreCase(MESSAGE.SET_MELEE_WEAPON.toString())) {
                String weaponIDStr = json.fromJson(String.class, string[1]);
                ItemID weaponID = Item.ItemID.valueOf(weaponIDStr);
                Weapon weapon = WeaponFactory.getInstance().getWeapon(weaponID);
                weaponSystem.setMeleeWeapon(weapon);
            } else if(string[0].equalsIgnoreCase(MESSAGE.SET_RANGED_WEAPON.toString())) {
                String weaponIDStr = json.fromJson(String.class, string[1]);
                String[] splitStr = weaponIDStr.split(MESSAGE_TOKEN_2);
                ItemID weaponID = Item.ItemID.valueOf(splitStr[0]);
                Weapon weapon = WeaponFactory.getInstance().getWeapon(weaponID);
                weaponSystem.setRangedWeapon(weapon);
                weaponSystem.setStartAmmoCountInMagazine(Integer.parseInt(splitStr[1]));
            } else if(string[0].equalsIgnoreCase(MESSAGE.SET_RUDIMENT_ONE.toString())) {
                String rudimentOneIDStr = json.fromJson(String.class, string[1]);
                ItemID rudimentOneID = Item.ItemID.valueOf(rudimentOneIDStr);
                Rudiment rudimentOne = RudimentFactory.getInstance().getRudiment(rudimentOneID);
                rudimentSystem.setRudimentOne(rudimentOne);
            }  else if(string[0].equalsIgnoreCase(MESSAGE.SET_RUDIMENT_TWO.toString())) {
                String rudimentTwoIDStr = json.fromJson(String.class, string[1]);
                ItemID rudimentTwoID = Item.ItemID.valueOf(rudimentTwoIDStr);
                Rudiment rudimentTwo = RudimentFactory.getInstance().getRudiment(rudimentTwoID);
                rudimentSystem.setRudimentTwo(rudimentTwo);
            }  else if(string[0].equalsIgnoreCase(MESSAGE.SET_UNIQUE_RUDIMENT.toString())) {
                String uniqueRudimentIDStr = json.fromJson(String.class, string[1]);
                ItemID uniqueRudimentID = Item.ItemID.valueOf(uniqueRudimentIDStr);
                UniqueRudiment uniqueRudiment = RudimentFactory.getInstance().getUniqueRudiment(uniqueRudimentID);
                rudimentSystem.setUniqueRudiment(uniqueRudiment);
            }  else if(string[0].equalsIgnoreCase(MESSAGE.REMOVE_MELEE_WEAPON.toString())) {
                weaponSystem.setMeleeWeapon(null);
            } else if(string[0].equalsIgnoreCase(MESSAGE.REMOVE_RANGED_WEAPON.toString())) {
                weaponSystem.setRangedWeapon(null);
            } else if(string[0].equalsIgnoreCase(MESSAGE.REMOVE_RUDIMENT_ONE.toString())) {
                rudimentSystem.setRudimentOne(null);
            } else if(string[0].equalsIgnoreCase(MESSAGE.REMOVE_RUDIMENT_TWO.toString())) {
                rudimentSystem.setRudimentTwo(null);
            } else if(string[0].equalsIgnoreCase(MESSAGE.REMOVE_UNIQUE_RUDIMENT.toString())) {
                rudimentSystem.setUniqueRudiment(null);
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_ANIMATIONS.toString())) {
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
        this.mapManager = mapManager;
        camera = mapManager.getCamera();
        mapManager.getCamera().unproject(mouseCoordinates.set(Gdx.input.getX(), Gdx.input.getY(), 0));

        updateCamera();
        updateHitBox();
        updateImageBox();
        updateBoundingBox();
        updateBorderBoundingBox();
        updateShifts(mapManager, delta, 40);
        setSwordRangeBox(new Vector2(10000,10000), 0,0);
        weaponSystem.updateForPlayer(delta, this);
//        updateSwordRangeBox(64,64);

        if(isGunActive) {
            getMouseDirectionForGun();
        }

        if(startExecutionTimer){
            executionTimer += delta;
            if(executionScore == 2){
                startExecutionTimer = false;
                executionTimer = 0;
                executionScore = 0;
                executionDamageResist = true;
                executionDamageResistTimer = 10;
                this.setDamageResist(30);
            }
            if(executionTimer >= 5){
                startExecutionTimer = false;
                executionTimer = 0;
                executionScore = 0;
            }
        }

        if(executionDamageResist){
            executionDamageResistTimer -= delta;
            if(executionDamageResistTimer <= 0){
                executionDamageResist = false;
                this.setDamageResist(0);
            }
        }

        updateCharges(delta);

        switch (state) {
            case NORMAL:
                updateExecutableEnemies();
                updateAmmoHit(entity);
                updateMovement();
                updateDash();
                updateRudiment();
                updateMeleeAttack();
                updateRangedAttack();
                updateOtherInputs(entity, delta);
                updateDashShadow(delta);
                updateCurrentCollision(mapManager);
                updateCollisionWithPortalLayer(mapManager);
//                updateCollisionWithMapEntities(entity, mapManager);

//                if (pdaActive || inventoryActive) {
//                    state = State.FREEZE;
//                }
                break;
            case FREEZE:
                updateOtherInputs(entity, delta);
                if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
                    stateTime = 0f;
                    state = State.NORMAL;
                }
                break;
            case DEATH:
                currentState = Entity.State.IDLE;
                break;
        }

        updateAnimations(delta);
        mapManager.getCurrentMap().updateGridOjc();
    }

    private void updateCharges(float delta) {
        //RUDIMENT CHARGE
        if(this.rudimentCharge < 4 && !usingRudiment){
            rudimentCharge += delta;
        }

        //DASH CHARGE
        if(this.dashCharge < this.maxDashCharges && !dashing){
            dashTimer += delta;
            if(dashTimer >= 2){
                dashCharge += 1;
                dashTimer = 0;
                notify("", ComponentObserver.ComponentEvent.PLAYER_DASH_UPDATE);
            }
        }
    }

    private void updateOtherInputs(Entity entity, float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (pdaActive) {
                pdaActive = false;
                state = State.NORMAL;
                currentState = Entity.State.IDLE;
                notify("", ComponentObserver.ComponentEvent.CLOSE_PDA);
            }
            if (inventoryActive) {
                inventoryActive = false;
                state = State.NORMAL;
                currentState = Entity.State.IDLE;
                notify("", ComponentObserver.ComponentEvent.CLOSE_INVENTORY);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB) && !inventoryActive) {
            PlayerHUD.toastShort("Pressed TAB", Toast.Length.SHORT);
            pdaActive = !pdaActive;
            if (pdaActive) {
                notify("", ComponentObserver.ComponentEvent.OPEN_PDA);
                state = State.FREEZE;
            } else {
                notify("", ComponentObserver.ComponentEvent.CLOSE_PDA);
                state = State.NORMAL;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && !pdaActive) {
            PlayerHUD.toastShort("Pressed Z", Toast.Length.SHORT);
            inventoryActive = !inventoryActive;
            if (inventoryActive) {
                notify("", ComponentObserver.ComponentEvent.OPEN_INVENTORY);
                state = State.FREEZE;
            } else {
                notify("", ComponentObserver.ComponentEvent.CLOSE_INVENTORY);
                state = State.NORMAL;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            PlayerHUD.toastShort("Pressed T", Toast.Length.SHORT);
        }

        //EXO OFF
        if(Gdx.input.isKeyPressed(Input.Keys.E) && exoskeletonName != EntityFactory.EntityName.NONE){
            timer += delta;
            if(timer >= 2){
                entity.sendMessage(MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));
                entity.sendMessage(MESSAGE.INIT_ANIMATIONS, json.toJson(entity.getEntityConfig()));
                exoskeletonName = EntityFactory.EntityName.NONE;
                timer = 0;
            }
        }
    }

    private void updateCamera() {
        Vector2 screenPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector2 normalizedScreen = new Vector2(screenPos.x / Gdx.graphics.getWidth(), screenPos.y / Gdx.graphics.getHeight());
        float offset = 150;
        if (Rumble.getRumbleTimeLeft() > 0){
            Rumble.tick(Gdx.graphics.getDeltaTime());
            camera.translate(Rumble.getPos());
        } else if (pdaActive) {
            if (camera.zoom > 0.25f){
                camera.zoom -= 0.01f;
            }
            normalizedScreen.set(0.8f, 0.5f);
            normalizedScreen.sub(.5f, .5f);
            Vector3 cameraMoved = new Vector3(Math.round(currentEntityPosition.x) + normalizedScreen.x * offset + 64, Math.round(currentEntityPosition.y) - normalizedScreen.y * offset + 64,0);
            camera.position.lerp(cameraMoved, 0.1f);
        } else if (inventoryActive) {
        } else {
            normalizedScreen.sub(.5f, .5f);
            Vector3 cameraMoved = new Vector3(Math.round(currentEntityPosition.x) + normalizedScreen.x * offset + 64, Math.round(currentEntityPosition.y) - normalizedScreen.y * offset + 64,0);
            if(currentState == Entity.State.USE_RUDIMENT ) {
                cameraMoved.set(currentEntityPosition.x + normalizedScreen.x * offset + 128, currentEntityPosition.y - normalizedScreen.y * offset + 128,0);
            }
            camera.position.lerp(cameraMoved, 0.1f); //0.05f //delta
//            camera.position.set(cameraMoved);
//            camera.position.set(currentEntityPosition.x+64, currentEntityPosition.y+64, 0f);
        }

        //PDA SWITCH
        if (!pdaActive) {
            if (camera.zoom < 0.5f){
                camera.zoom += 0.01f;
            }
        }


        camera.update();
    }

    private void updateDashShadow(float delta) {
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
    }

    private void updateAmmoHit(Entity player) {
        tempEntities.clear();
        tempEntities.addAll(mapManager.getCurrentMapEntities());
        tempEntities.addAll(mapManager.getCurrentMapQuestEntities());

        for(Entity mapEntity: tempEntities) {
            if (!mapEntity.getWeaponSystem().rangedIsActive()) {
                continue;
            }
            if(mapEntity.equals(player)){
                continue;
            }

            Weapon weapon = mapEntity.getRangeWeapon();

            for(Ammo ammo: mapEntity.getActiveAmmo()){
                Polygon entityAmmoBoundingBox = ammo.getPolygon();
                Polygon playerHitBox = new Polygon(new float[] { 0, 0, hitBox.width, 0, hitBox.width, hitBox.height, 0, hitBox.height });
                playerHitBox.setPosition(hitBox.x, hitBox.y);
                if (Intersector.overlapConvexPolygons(playerHitBox, entityAmmoBoundingBox)) {
//                    gotHit();
//                    reduceHealth(weapon.getRandomDamage() + player.getRangedDamageBoost() + player.getDamageBoost());
                    ammo.setRemove(true);
                }
            }
        }

    }

    private void updateMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) && !boolTopBoundingBox) {
            currentState = Entity.State.RUN;
            if (Gdx.input.isKeyPressed(Input.Keys.D) && !boolTopBoundingBox) {
                currentDirection = Entity.Direction.RIGHT;
                currentEntityPosition.y += runVelocityD.y;
                currentEntityPosition.x += runVelocityD.x;
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) && !boolTopBoundingBox) {
                currentDirection = Entity.Direction.LEFT;
                currentEntityPosition.y += runVelocityD.y;
                currentEntityPosition.x -= runVelocityD.x;
            } else {
                currentDirection = Entity.Direction.UP;
                currentEntityPosition.y += runVelocity.y;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) && !boolBottomBoundingBox) {
            currentState = Entity.State.RUN;
            if (Gdx.input.isKeyPressed(Input.Keys.D) && !boolBottomBoundingBox) {
                currentDirection = Entity.Direction.RIGHT;
                currentEntityPosition.y -= runVelocityD.y;
                currentEntityPosition.x += runVelocityD.x;
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) && !boolBottomBoundingBox) {
                currentDirection = Entity.Direction.LEFT;
                currentEntityPosition.y -= runVelocityD.y;
                currentEntityPosition.x -= runVelocityD.x;
            } else {
                currentDirection = Entity.Direction.DOWN;
                currentEntityPosition.y -= runVelocity.y;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) && !boolLeftBoundingBox) {
            currentState = Entity.State.RUN;
            currentDirection = Entity.Direction.LEFT;
            currentEntityPosition.x -= runVelocity.x;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) && !boolRightBoundingBox) {
            currentState = Entity.State.RUN;
            currentDirection = Entity.Direction.RIGHT;
            currentEntityPosition.x += runVelocity.x;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && !boolTopBoundingBox) {
            currentState = Entity.State.WALK;
            if (Gdx.input.isKeyPressed(Input.Keys.D) && !boolTopBoundingBox) {
                currentDirection = Entity.Direction.RIGHT;
                currentEntityPosition.y += walkVelocityD.y;
                currentEntityPosition.x += walkVelocityD.x;
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) && !boolTopBoundingBox) {
                currentDirection = Entity.Direction.LEFT;
                currentEntityPosition.y += walkVelocityD.y;
                currentEntityPosition.x -= walkVelocityD.x;
            } else {
                currentDirection = Entity.Direction.UP;
                currentEntityPosition.y += walkVelocity.y;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && !boolBottomBoundingBox) {
            currentState = Entity.State.WALK;
            if (Gdx.input.isKeyPressed(Input.Keys.D) && !boolBottomBoundingBox) {
                currentDirection = Entity.Direction.RIGHT;
                currentEntityPosition.y -= walkVelocityD.y;
                currentEntityPosition.x += walkVelocityD.x;
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) && !boolBottomBoundingBox) {
                currentDirection = Entity.Direction.LEFT;
                currentEntityPosition.y -= walkVelocityD.y;
                currentEntityPosition.x -= walkVelocityD.x;
            } else {
                currentDirection = Entity.Direction.DOWN;
                currentEntityPosition.y -= walkVelocity.y;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && !boolLeftBoundingBox) {
            currentState = Entity.State.WALK;
            currentDirection = Entity.Direction.LEFT;
            currentEntityPosition.x -= walkVelocity.x;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && !boolRightBoundingBox) {
            currentState = Entity.State.WALK;
            currentDirection = Entity.Direction.RIGHT;
            currentEntityPosition.x += walkVelocity.x;
        } else {
            currentState = Entity.State.IDLE;
            boolTopBoundingBox = boolBottomBoundingBox = boolLeftBoundingBox = boolRightBoundingBox = isGunActive2 = isGunActive = false;
        }
    }

    private void updateDash() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && this.dashCharge >= 1) {
            dashCharge-=1;
            dashing = true;
            stateTime = 0f;
            currentState = Entity.State.DASH;
            getMouseDirection();
            doDash();
            animationExecution(0.38f);
            dashing = false; // if active dash shadow does not work
            notify("", ComponentObserver.ComponentEvent.PLAYER_DASH);
        }
    }

    private void updateRudiment() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F) && this.rudimentCharge>=1 && !usingRudiment && rudimentSystem.getUniqueRudiment()!=null) {
            usingRudiment=true;
            currentEntityPosition.x -= 64;
            currentEntityPosition.y -= 64;
            stateTime = 0f;
            state = State.FREEZE;
            currentState = Entity.State.USE_RUDIMENT;
            rudimentSystem.getUniqueRudiment().activateRudiment(this);
            rudimentCharge-=1;

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
    }

    private void updateMeleeAttack() {
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
            timeSinceLastAttack = System.currentTimeMillis();
            System.out.println("LMB: Attack " + attackId);

            atkTime = 0f;
            currentState = Entity.State.MELEE_ATTACK;
            getMouseDirection();
            meleeAttackMove();

            if (attackId == 0) {
                frameAttack = 0.55f;
            } else {
                frameAttack = 0.65f;
            }

            animationExecution(frameAttack); //0.44
            updateSwordRangeBox(64, 64);
        }
    }

    private void updateRangedAttack() {
        //RANGED ATTACK
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && weaponSystem.rangedIsActive() && weaponSystem.getRangedWeapon().getAmmoCountInMagazine() != 0) {
            currentState = Entity.State.RANGED_ATTACK;
            isGunActive = isGunActive2 = true;
            notify(json.toJson(weaponSystem.getRangedWeapon().getAmmoCountInMagazine() - 1 + ":::" + weaponSystem.getAmmoCountFromBag()), ComponentObserver.ComponentEvent.PLAYER_SHOT);
            animationExecution(weaponSystem.getRangedWeapon().getAttackTime());
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
            notify(json.toJson(weaponSystem.getRangedWeapon().getAmmoCountInMagazine() + ":::" + weaponSystem.getAmmoCountFromBag()), ComponentObserver.ComponentEvent.PLAYER_SHOT);
        }
        if (reloaded) {
            reloaded = false;
            notify(json.toJson(weaponSystem.getRangedWeapon().getAmmoCountInMagazine() + ":::" + weaponSystem.getAmmoCountFromBag()), ComponentObserver.ComponentEvent.PLAYER_SHOT);
        }
    }

    private void updateCollisionWithPortalLayer(MapManager mapMgr){
        MapLayer portalLayer = mapMgr.getPortalLayer();

        if( portalLayer == null ){
            return;
        }

        Rectangle rectangle;
        for( MapObject object: portalLayer.getObjects()){
            if(object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject)object).getRectangle();
                if(boundingBox.overlaps(rectangle) ){
                    if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                        String mapName = object.getName();
                        int xPosStart = (int) object.getProperties().get("xPosStart");
                        int yPosStart = (int) object.getProperties().get("yPosStart");
                        mapMgr.loadMap(MapFactory.MapType.valueOf(mapName));
                        setCurrentPosition(xPosStart, yPosStart);
                    }
                }
            }
        }
    }

    private void updateExecutableEnemies(){
        Array<Entity> nearbyEnemies = checkForNearbyEnemies();

        for( Entity mapEntity : nearbyEnemies ) {
            if(mapEntity.isLowHP() && mapEntity.isExecutable()){
                if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
                    animationExecution(0.8f);
                    mapEntity.executeEnemy();
                    if(this.getPlayerSkills().contains(27,true)) {
                        startExecutionTimer = true;
                        executionScore += 1;
                    }
                }
            }

        }
    }

    private void endlessCourageSkill(){
        if(this.getPlayerSkills().contains(21,true)){
            int percentOfHealthLost = 100 - (this.getHealth() / (this.getMaxHealth() / 100));
            this.setDamageBoost(percentOfHealthLost / 3);
        }
    }

    public Array<Entity> checkForNearbyEnemies(){
        tempEntities.clear();
        tempEntities.addAll(mapManager.getCurrentMapEntities());
        tempEntities.addAll(mapManager.getCurrentMapQuestEntities());
        Array<Entity> nearbyEnemies = new Array<>();
        for( Entity mapEntity : tempEntities ) {
            Rectangle entitySomeBox = mapEntity.getActiveZoneBox();
            if (boundingBox.overlaps(entitySomeBox)) {
                nearbyEnemies.add(mapEntity);
            }
        }
        return nearbyEnemies;
    }

    private void equipExoskeleton(EntityConfig exoskeletonConfig){
        walkVelocity.set(exoskeletonConfig.getWalkVelocity());
        walkVelocityD.set(exoskeletonConfig.getWalkVelocityD());
        runVelocity.set(exoskeletonConfig.getRunVelocity());
        runVelocityD.set(exoskeletonConfig.getRunVelocityD());
    }

    private void unEquipExoskeleton(EntityConfig playerConfig) {
        walkVelocity.set(playerConfig.getWalkVelocity());
        walkVelocityD.set(playerConfig.getWalkVelocityD());
        runVelocity.set(playerConfig.getRunVelocity());
        runVelocityD.set(playerConfig.getRunVelocityD());
    }

    @Override
    public void draw(Batch batch, float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) debugActive = !debugActive;
        if (debugActive) debug(false,false,true,
                true,true,true, true,
                true,false,true,false,false, false);

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
                weaponSystem.getRangedWeapon().drawAmmo(batch, camera);
            }
            batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
            batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y);
        } else {
            batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y); // player
            batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y); // blood
            if (weaponSystem.rangedIsActive()) {
                weaponSystem.getRangedWeapon().drawAmmo(batch, camera);
                if (isGunActive){
                    weaponSystem.getRangedWeapon().drawRotatedGun(batch, delta);
                }
            }
        }
        batch.end();
    }

    @Override
    public void reduceHealth(int damage) {
        this.setHealth(this.getHealth() - damage*((100-this.getDamageResist())/100));
        endlessCourageSkill();
    }

    @Override
    public void restoreHealth(int heal){
        super.restoreHealth(heal);
        endlessCourageSkill();
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
        if(keycode==Input.Keys.E){
            timer=0;
        }else if(keycode==Input.Keys.F && !rudimentLock) {
            rudimentLock=true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    usingRudiment=false;
                    rudimentLock=false;
                }}, 4);
        };
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isLeftButtonPressed = true;

        if (button == Input.Buttons.LEFT) {
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
