//package com.mygdx.game._oldClasses;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.Timer;
//import com.mygdx.game.UI.PlayerHUD;
//import com.mygdx.game.entity.Entity;
//import com.mygdx.game.entity.EntityConfig;
//import com.mygdx.game.observer.ComponentObserver;
//import com.mygdx.game.tools.Rumble;
//import com.mygdx.game.tools.Toast;
//import com.mygdx.game.managers.ControlManager;
//import com.mygdx.game.managers.ResourceManager;
//import com.mygdx.game.world.MapManager;
//
//
//public class oldPlayer extends oldComponent {
//
//    protected enum State {
//        NORMAL,
//        FREEZ,
//        DEATH,
//    }
//
//    private State state;
//    private boolean dogShitLeft = false;
//    private boolean dogShitRight = false;
//
//    private Vector2 previousPosition;
//
//    public oldPlayer(){
//        initBoundingBox();
//        initEntityRangeBox();
//        state = State.NORMAL;
//        previousPosition = new Vector2(0,0);
//        controlManager = new ControlManager();
//    }
//
//    @Override
//    public void receiveMessage(String message) {
//        String[] string = message.split(MESSAGE_TOKEN);
//
//        if( string.length == 0 ) return;
//
//        if( string.length == 2 ) {
//            if (string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())) {
//                currentEntityPosition = json.fromJson(Vector2.class, string[1]);
//            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
//                currentEntityPosition = json.fromJson(Vector2.class, string[1]);
//            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_STATE.toString())) {
//                currentState = json.fromJson(Entity.State.class, string[1]);
//            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_DIRECTION.toString())) {
//                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
//            } else if (string[0].equalsIgnoreCase(MESSAGE.INTERACTION_WITH_ENTITY.toString())) {
//
//            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_CONFIG.toString())) {
//                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
//                entityName = entityConfig.getEntityID();
////                System.out.println(entityName);
//            }  else if (string[0].equalsIgnoreCase(MESSAGE.INIT_ANIMATIONS.toString())) {
//                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
//                Array<EntityConfig.AnimationConfig> animationConfigs = entityConfig.getAnimationConfig();
//
//                if (animationConfigs.size == 0) return;
//
//                for( EntityConfig.AnimationConfig animationConfig : animationConfigs ) {
//                    float frameDuration = animationConfig.getFrameDuration();
//                    ResourceManager.AtlasType atlasType = animationConfig.getAtlasType();
//                    Entity.AnimationType animationType = animationConfig.getAnimationType();
//                    Animation.PlayMode playMode = animationConfig.getPlayMode();
//
//                    Animation<Sprite> animation = null;
//                    animation = loadAnimation(frameDuration, atlasType, animationType, playMode);
//                    animations.put(animationType, animation);
//                }
//            }
//        }
//
//    }
//
//    @Override
//    public void updateForPlayer(Entity entity, MapManager mapManager, Batch batch, float delta) {
//
//        mapManager.getCamera().unproject(mouseCoordinates.set(Gdx.input.getX(), Gdx.input.getY(), 0));
////        updatePortalLayerActivation(mapManager, delta);
//
//        //Player has moved
//        if( previousPosition.x != currentEntityPosition.x || previousPosition.y != currentEntityPosition.y){
//            notify("", ComponentObserver.ComponentEvent.PLAYER_HAS_MOVED);
//            previousPosition = currentEntityPosition.cpy();
//        }
//
//        activeDash(delta);
//        activeSwordAttackMove(delta);
//        activeGotHit(delta);
//        setSwordRangeBox(new Vector2(10000,10000), 0,0);
//
//        tempEntities.clear();
//        tempEntities.addAll(mapManager.getCurrentMapEntities());
//        tempEntities.addAll(mapManager.getCurrentMapQuestEntities());
//
//        for( Entity mapEntity : tempEntities ) {
//            Rectangle entitySwordRangeBox = mapEntity.getCurrentSwordRangeBox();
//            if (entitySwordRangeBox.overlaps(entityRangeBox)) {
//                stateTime=0f;
//                playerGotHit(delta);
//                health-=25;
//                state = State.FREEZ;
//                currentState = Entity.State.TAKING_DAMAGE;
//
//                Timer.schedule(new Timer.Task() {
//                    @Override
//                    public void run() {
//                        state = State.NORMAL;
//                    }}, 0.3f);
//                Rumble.rumble(5, .1f, 0, Rumble.State.SWORD);
//                PlayerHUD.toastShort("Enemy HIT", Toast.Length.SHORT);
//            }
//        }
//
//        //INPUT
//        input(entity);
//
//        //PHYSICS
//        camera = mapManager.getCamera();
//        if (Rumble.getRumbleTimeLeft() > 0){
//            Rumble.tick(Gdx.graphics.getDeltaTime());
//            camera.translate(Rumble.getPos());
//        } else if (pdaActive) {
////            if (currentEntityPosition.x < currentEntityPosition.)
////            camera.translate(1,0);
//        } else {
//            Vector2 screenPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
//            Vector2 normalizedScreen = new Vector2(screenPos.x / Gdx.graphics.getWidth(), screenPos.y / Gdx.graphics.getHeight());
//            normalizedScreen.sub(.5f, .5f);
//            float offset = 150;
//            Vector3 cameraMoved = new Vector3(Math.round(currentEntityPosition.x) + normalizedScreen.x * offset+64, Math.round(currentEntityPosition.y) - normalizedScreen.y * offset+64,0);
//            if(currentState == Entity.State.USE_RUDIMENT ) {
//                cameraMoved.set(currentEntityPosition.x + normalizedScreen.x * offset+128, currentEntityPosition.y - normalizedScreen.y * offset+128,0);
//            }
//            camera.position.lerp(cameraMoved, 0.1f); //0.05f //delta
////            camera.position.set(cameraMoved);
////            camera.position.set(currentEntityPosition.x+64, currentEntityPosition.y+64, 0f);
//        }
//        camera.updateForPlayer();
//
//        //PDA SWITCH
//        if (pdaActive) {
//            if (camera.zoom  > ggov){
//                camera.zoom -= delta * speedCamMove * 0.03f;
//                camera.translate(1,1);
//            }
//        }
//
////        if (!pdaActive) {
////            if (camera.zoom  <= 0.39f){
////                camera.zoom += delta * speedCamMove * 0.03f;
////            }
////        }
//
//
//        if (currentState == Entity.State.RUN){
//        } else if(currentState == Entity.State.MELEE_ATTACK ) {
//        } else if(currentState == Entity.State.USE_RUDIMENT ) {
//        } else{
//        }
//
//        updateBoundingBoxPosition(64,64);
//        updateEntityRangeBox(64,64);
//
//        //GUN ACTIVE
//        if(boolGunActive) {
//            getMouseDirectionForGun();
//        }
//
////        updateSwordRangeBox(64,64);
//
//        //DASH
//        if(dashing) {
//            if(Gdx.graphics.getFrameId() % (int) ((Gdx.graphics.getFramesPerSecond()*.02f)+1) == 0) {  //def .05f
//                updateDashShadow.add(new Vector3(currentEntityPosition.x, currentEntityPosition.y, 1));
//                anInt1++;
//            }
//            dashTime += delta;
//        }
//        if(dashTime > 0.2f) {  //def .02f
//            dashTime = 0;
//            dashing = false;
//            anInt1 = 1;
//        }
//
//        //GRAPHICS
//        updateAnimations(delta);
//    }
//
//    @Override
//    public void draw(Batch batch, float delta) {
//        batch.begin();
//
//        //DASH
//        for(Vector3 shadow : updateDashShadow) {
//            batch.setColor(0.05f,0.7f, 0.8f, shadow.z);
//            batch.draw(currentFrame, shadow.x, shadow.y);
//            shadow.z -= Gdx.graphics.getDeltaTime()*6;  //def *2
//        }
//        batch.setColor(Color.WHITE);
//        for(int i = 0; i < updateDashShadow.size(); i++) {
//            if(updateDashShadow.get(i).z <= 0) {
//                updateDashShadow.remove(i);
//            }
//        }
//
//        batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
//        batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y);
//
//        batch.end();
//    }
//
//    private void input(Entity entity) {
//
//        switch (state) {
//            case NORMAL:
//                if (true) {
//                    if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
//                        Gdx.app.exit();
//                    } else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
//                        PlayerHUD.toastShort("Pressed T", Toast.Length.SHORT);
//                        entity.sendMessage(MESSAGE.INTERACTION_WITH_ENTITY);
//                    }
//
//                    if (!PlayerHUD.browserUI.isVisible()) {
//                        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
//                            PlayerHUD.toastShort("Pressed TAB", Toast.Length.SHORT);
//                            pdaActive = !pdaActive;
//                        }
//                    }
//
//                    if (!PlayerHUD.pdaUI.isVisible() && !PlayerHUD.browserUI.isVisible()) {
//
//                        if (Gdx.input.isKeyPressed(Input.Keys.W) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))) {
//                            currentState = Entity.State.RUN;
//                            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//                                currentDirection = Entity.Direction.RIGHT;
//                                currentEntityPosition.y += runVelocityD.y;
//                                currentEntityPosition.x += runVelocityD.x;
//                            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//                                currentDirection = Entity.Direction.LEFT;
//                                currentEntityPosition.y += runVelocityD.y;
//                                currentEntityPosition.x -= runVelocityD.x;
//                            } else {
//                                currentDirection = Entity.Direction.UP;
//                                currentEntityPosition.y += runVelocity.y;
//                            }
//                        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))) {
//                            currentState = Entity.State.RUN;
//                            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//                                currentDirection = Entity.Direction.RIGHT;
//                                currentEntityPosition.y -= runVelocityD.y;
//                                currentEntityPosition.x += runVelocityD.x;
//                            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//                                currentDirection = Entity.Direction.LEFT;
//                                currentEntityPosition.y -= runVelocityD.y;
//                                currentEntityPosition.x -= runVelocityD.x;
//                            } else {
//                                currentDirection = Entity.Direction.DOWN;
//                                currentEntityPosition.y -= runVelocity.y;
//                            }
//                        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))) {
//                            currentState = Entity.State.RUN;
//                            currentDirection = Entity.Direction.LEFT;
//                            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//                                currentEntityPosition.x -= runVelocityD.x;
//                                currentEntityPosition.y += runVelocityD.y;
//                            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//                                currentEntityPosition.x -= runVelocityD.x;
//                                currentEntityPosition.y -= runVelocityD.y;
//                            } else {
//                                currentEntityPosition.x -= runVelocity.x;
//                            }
//                        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))) {
//                            currentState = Entity.State.RUN;
//                            currentDirection = Entity.Direction.RIGHT;
//                            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//                                currentEntityPosition.x += runVelocityD.x;
//                                currentEntityPosition.y += runVelocityD.y;
//                            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//                                currentEntityPosition.x += runVelocityD.x;
//                                currentEntityPosition.y -= runVelocityD.y;
//                            } else {
//                                currentEntityPosition.x += runVelocity.x;
//                            }
//
//                        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//                            currentState = Entity.State.WALK;
//                            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//                                currentDirection = Entity.Direction.RIGHT;
//                                currentEntityPosition.y += walkVelocityD.y;
//                                currentEntityPosition.x += walkVelocityD.x;
//                            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//                                currentDirection = Entity.Direction.LEFT;
//                                currentEntityPosition.y += walkVelocityD.y;
//                                currentEntityPosition.x -= walkVelocityD.x;
//                            } else {
//                                currentDirection = Entity.Direction.UP;
//                                currentEntityPosition.y += walkVelocity.y;
//                            }
//                        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//                            currentState = Entity.State.WALK;
//                            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//                                currentDirection = Entity.Direction.RIGHT;
//                                currentEntityPosition.y -= walkVelocityD.y;
//                                currentEntityPosition.x += walkVelocityD.x;
//                            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//                                currentDirection = Entity.Direction.LEFT;
//                                currentEntityPosition.y -= walkVelocityD.y;
//                                currentEntityPosition.x -= walkVelocityD.x;
//                            } else {
//                                currentDirection = Entity.Direction.DOWN;
//                                currentEntityPosition.y -= walkVelocity.y;
//                            }
//                        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//                            currentState = Entity.State.WALK;
//                            currentDirection = Entity.Direction.LEFT;
//                            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//                                currentEntityPosition.x -= walkVelocityD.x;
//                                currentEntityPosition.y += walkVelocityD.y;
//                            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//                                currentEntityPosition.x -= walkVelocityD.x;
//                                currentEntityPosition.y -= walkVelocityD.y;
//                            } else {
//                                currentEntityPosition.x -= walkVelocity.x;
//                            }
//                        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//                            currentState = Entity.State.WALK;
//                            currentDirection = Entity.Direction.RIGHT;
//                            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//                                currentEntityPosition.x += walkVelocityD.x;
//                                currentEntityPosition.y += walkVelocityD.y;
//                            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//                                currentEntityPosition.x += walkVelocityD.x;
//                                currentEntityPosition.y -= walkVelocityD.y;
//                            } else {
//                                currentEntityPosition.x += walkVelocity.x;
//                            }
//                        } else if (Gdx.input.isKeyPressed(Input.Keys.C)) {
//                            camera.zoom -= 0.0005;
//                        } else {
//                            currentState = Entity.State.IDLE;
//                            boolPissPiss = false;
//                            boolGunActive = false;
//                        }
//
//                        if (Gdx.input.isKeyJustPressed(Input.Keys.COMMA)) {
//                            stateTime = 0f;
//                            state = State.FREEZ;
//                            currentState = Entity.State.BAR_DRINK;
//
//                            Timer.schedule(new Timer.Task() {
//                                @Override
//                                public void run() {
//                                    state = State.NORMAL;
//                                }}, 2f);
//                        }
//
//                        if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) {
//                            stateTime = 0f;
//                            state = State.FREEZ;
//                            currentState = Entity.State.HURT_AMELIA;
//
//                            Timer.schedule(new Timer.Task() {
//                                @Override
//                                public void run() {
//                                    state = State.NORMAL;
//                                }}, 10f);
//                        }
//
//                        //DASH
//                        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//                            stateTime = 0f;
//                            state = State.FREEZ;
//                            currentState = Entity.State.DASH;
//                            getMouseDirection();
//                            doDash();
//
//                            dashing = true;
//
//                            Timer.schedule(new Timer.Task() {
//                                @Override
//                                public void run() {
//                                    state = State.NORMAL;
//                                }}, 0.38f);
//                        }
//
//                        //RUDIMENT
//                        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
//                            currentEntityPosition.x -= 64;
//                            currentEntityPosition.y -= 64;
//                            stateTime = 0f;
//                            state = State.FREEZ;
//                            currentState = Entity.State.USE_RUDIMENT;
//                            PlayerHUD.toastShort("Use Rudiment", Toast.Length.SHORT);
//
//                            Timer.schedule(new Timer.Task() {
//                                @Override
//                                public void run() {
//                                    Rumble.rumble(15f, 0.1f, -1, Rumble.State.SWORD);
//                                }}, 0.5f);
//
//                            Timer.schedule(new Timer.Task() {
//                                @Override
//                                public void run() {
//                                    currentEntityPosition.x += 64;
//                                    currentEntityPosition.y += 64;
//                                    state = State.NORMAL;
//                                }}, 1.1f);
//                        }
//
//                        //MELEE ATTACK
//                        if (dogShitLeft) {
//                            dogShitLeft = false;
//
//                            currentTime = System.currentTimeMillis();
//                            if ((currentTime - timeSinceLastAttack) < comboTimer) {
//                                if (attackId == 1)
//                                    attackId = 0;
//                                else {
//                                    attackId++;
//                                }
//                            } else {
//                                attackId = 0;
//                            }
//                            System.out.println("LMB: Attack " + attackId);
//                            timeSinceLastAttack = System.currentTimeMillis();
//
//                            atkTime = 0f;
//                            state = State.FREEZ;
//                            currentState = Entity.State.MELEE_ATTACK;
//                            getMouseDirection();
//                            meleeAttackMove();
//
//                            if (attackId == 0) {
//                                frameAttack = 0.55f;
//                            } else {
//                                frameAttack = 0.65f;
//                            }
//
//                            Timer.schedule(new Timer.Task() {
//                                @Override
//                                public void run() {
//                                    state = State.NORMAL;
//                                }
//                            }, frameAttack); //0.44
//
//                            updateSwordRangeBox(64, 64);
//                        }
//
//                        //RANGED ATTACK ACTIVE
//                        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
//                            currentState = Entity.State.RANGED_ATTACK;
//                            boolPissPiss = true;
//                            boolGunActive = true;
//                        }
//
//                        //RANGED ATTACK
//                        if (dogShitRight) {
//                            state = State.FREEZ;
//                            currentState = Entity.State.RANGED_ATTACK;
//                            dogShitRight = false;
//                            boolPissPiss = true;
//                            boolGunActive = true;
//
//                            Timer.schedule(new Timer.Task() {
//                                @Override
//                                public void run() {
//                                    state = State.NORMAL;
//                                    boolGunActive = false;
//                                }}, 0.35f);
//                        }
//                    }
//                } else {
//                    stateTime = 0f;
//                    state = State.DEATH;
//                    currentState = Entity.State.DEATH;
//                }
//                break;
//            case FREEZ:
//                if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
//                    stateTime = 0f;
//                    state = State.NORMAL;
//                }
//                break;
//            case DEATH:
//                break;
//        }
//    }
//
//    private boolean updatePortalLayerActivation(MapManager mapMgr, float delta){
//        return true;
//    }
//
//    @Override
//    public boolean keyDown(int keycode) {
//        return false;
//    }
//
//    @Override
//    public boolean keyUp(int keycode) {
//        return false;
//    }
//
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        if (button == Input.Buttons.LEFT){
//            dogShitLeft=true;
//        }
//        if(button == Input.Buttons.RIGHT) {
////            dogShitRight=true;
////            isGunActive2=true;
////            isGunActive=true;
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean keyTyped(char character) {
//        return false;
//    }
//
//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        return false;
//    }
//
//    @Override
//    public boolean mouseMoved(int screenX, int screenY) {
//        return false;
//    }
//
//    @Override
//    public boolean scrolled(int amount) {
//        if (amount==-1){
//            camera.zoom-=0.03;
//        } else {
//            camera.zoom+=0.03;
//        }
//        return false;
//    }
//
//    @Override
//    public void dispose(){
//        Gdx.input.setInputProcessor(null);
//    }
//}
