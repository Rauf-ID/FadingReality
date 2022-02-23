//package com.mygdx.game._oldClasses;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.Timer;
//import com.mygdx.game.FadingReality;
//import com.mygdx.game.UI.PlayerHUD;
//import com.mygdx.game.component.Component;
//import com.mygdx.game.component.Message;
//import com.mygdx.game.entity.Entity;
//import com.mygdx.game.entity.EntityConfig;
//import com.mygdx.game.entity.EntityFactory;
//import com.mygdx.game.tools.Rumble;
//import com.mygdx.game.tools.Toast;
//import com.mygdx.game.managers.ResourceManager;
//import com.mygdx.game.world.MapManager;
//
//public class oldEnemy extends oldComponent {
//
//    protected enum State {
//        NORMAL,
//        ATTAK,
//        DEATH,
//    }
//
//    private State state;
//
//    public oldEnemy() {
//        state = State.ATTAK;
//        initBoundingBox();
//        initEntityRangeBox();
//        initChaseRangeBox();
//        initAttackRangeBox();
//
////        Rectangle rectangle2 = new Rectangle();
////        Rectangle rectangle3 = new Rectangle();
////        Rectangle rectangle4 = new Rectangle();
//
//    }
//
//    @Override
//    public void receiveMessage(String message) {
//        String[] string = message.split(MESSAGE_TOKEN);
//
//        if( string.length == 0 ) return;
//
//        if( string.length == 1 ) {
//            if (string[0].equalsIgnoreCase(Message.MESSAGE.COLLISION_WITH_MAP.toString())) {
//                currentDirection = Entity.Direction.DOWN;
//            } else if (string[0].equalsIgnoreCase(Message.MESSAGE.COLLISION_WITH_ENTITY.toString())) {
//                currentState = Entity.State.IDLE;
//            }
//        }
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
//            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_CONFIG.toString())) {
//                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
//                entityName = entityConfig.getEntityID();
////                chaseRangeBox.set(currentEntityPosition.x-(entityConfig.getAttackRadiusBoxWidth()/2)+(boundingBox.width/2), currentEntityPosition.y-(entityConfig.getAttackRadiusBoxHeight()/2)+(boundingBox.height/2), entityConfig.getAttackRadiusBoxWidth(), entityConfig.getAttackRadiusBoxHeight());
//            } else if (string[0].equalsIgnoreCase(MESSAGE.ACTIVATE_ANIM_MECHAN.toString())) {
//                activateAnimMechan = json.fromJson(Boolean.class, string[1]);
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
//    }
//
//    @Override
//    public void updateForPlayer(Entity entity, MapManager mapManager, Batch batch, float delta) {
//
//        if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
//            state = State.NORMAL;
//        }
//
//        updateBoundingBoxPosition(64,64);
//        updateEntityRangeBox(64,64);
//        updateAttackRangeBox(64,64);
//        updateChaseRangeBox(64,64);
//
//        //INPUT
//        activeGotHit(delta);
//        activeSwordAttackMoveForEnemy(delta);
//        initSwordRangeBox(new Vector2(10000,10000),0,0);
//
//
//        Rectangle playerBoundingBox = mapManager.getPlayer().getVectorBoundingBox();
//        Rectangle playerRangeBox = mapManager.getPlayer().getCurrentEntityRangeBox();
//        Rectangle playerSwordRangeBox = mapManager.getPlayer().getCurrentSwordRangeBox();
//
//        Entity player = mapManager.getPlayer();
//
//        if(playerSwordRangeBox.overlaps(entityRangeBox)){
//            stateTime=0f;
//            gotHit();
//            health-=25;
//            state = State.ATTAK;
//            currentState = Entity.State.TAKING_DAMAGE;
//
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    state = State.NORMAL;
//                }
//            }, 0.3f);
//
//            PlayerHUD.toastShort("Player HIT", Toast.Length.SHORT);
//            //
//
//            Rumble.rumble(5, .1f, 0, Rumble.State.SWORD);
////            Rumble.rumble(5, .1f, 0, Rumble.State.GUN);
////            if(player.getCurrentDirection() == Entity.Direction.LEFT) {
////                Rumble.rumble(-5, .1f, 0, Rumble.State.GUN);
////            } else if (player.getCurrentDirection() == Entity.Direction.RIGHT) {
////                Rumble.rumble(5, .1f, 0, Rumble.State.GUN);
////            } else if (player.getCurrentDirection() == Entity.Direction.UP) {
////                Rumble.rumble(5, .1f, 1, Rumble.State.GUN);
////            } else if (player.getCurrentDirection() == Entity.Direction.DOWN) {
////                Rumble.rumble(-5, .1f, 1, Rumble.State.GUN);
////            }
//        }
//
//        //SPACE STATION
//        if(entityName.equals(EntityFactory.EntityName.TOWN_FOLK3.toString()) && Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
//            stateTime = 0f;
//            currentState = Entity.State.POLICE_STOPS;
////            Timer.schedule(new Timer.Task() {
////                @Override
////                public void run() {
////                    currentState = Entity.State.IDLE;
////                }
////            }, 1f);
//        }
//
//        if(entityName.equals(EntityFactory.EntityName.TOWN_FOLK4.toString()) && Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
//            policeLookedAround = 0f;
//            currentState = Entity.State.POLICE_LOOKED_AROUND;
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    currentState = Entity.State.IDLE;
//                }
//            }, 1.4f);
//        }
//
//        if(entityName.equals(EntityFactory.EntityName.TOWN_FOLK7.toString()) && Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3)) {
//            stateTime = 0f;
//            currentState = Entity.State.DETENTION;
//            detentionActive = true;
//        }
//        if (detentionActive) {
//            goToPoint(new Vector2(1200,335));
//        }
//
//        //MEHAN
//        if(entityName.equals(EntityFactory.EntityName.TOWN_FOL.toString()) && activateAnimMechan) {
//            activateAnimMechan = false;
//            stateTime = 0f;
//            currentState = Entity.State.MECHANISM_OPEN_GATE;
//        }
//
//        if(entityName.equals(EntityFactory.EntityName.TOWN_FOL.toString()) && Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
//            stateTime = 0f;
//            currentState = Entity.State.WALK;
//            moveActive = true;
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    currentState = Entity.State.WALK_SHADOW;
//                }
//            }, 1.3f);
//        }
//        if (moveActive) {
//            goToPoint(new Vector2(641,330));
//            if (sheeshTime < 40) {
//                // Do something
//                sheeshTime++;
//            } else {
//                Rumble.rumble(2.5f, 0.1f, -1, Rumble.State.SWORD);
//                sheeshTime = 0;
//            }
//
//        }
//
//        switch (state) {
//            case NORMAL:
//                if (health > 0) {
//                    if (attackRangeBox.overlaps(playerBoundingBox)) {   // ATTACK
//                        long time = System.currentTimeMillis();
//                        if (time > lastAttack + cooldownTime) {
//                            // Do something
//                            state = State.ATTAK;
//                            currentState = Entity.State.MELEE_ATTACK;
//                            lastAttack = time;
//
////                            meleeAttackMoveForEnemy(mapManager);
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
//                    } else if (!boundingBox.overlaps(mapManager.getPlayer().getVectorBoundingBox()) && chaseRangeBox.overlaps(playerBoundingBox) && !isCollisionWithMapEntities(entity, mapManager)) { //CHASE
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
//                    state = State.DEATH;
//                    currentState = Entity.State.DEATH;
//
//                }
//
//                break;
//            case ATTAK:
//                break;
//            case DEATH:
////                Timer.schedule(new Timer.Task() {
////                    @Override
////                    public void run() {
////                        NonameGame.resourceManager.roninAnimDeadLeft.setFrameDuration(0);
////                        NonameGame.resourceManager.roninAnimDeadRight.setFrameDuration(0);
////                    }
////                }, 1f);
//                break;
//        }
//
//
//
//
//
//
//
//
//        //GRAPHICS
//        updateAnimations(delta);
//    }
//
//
//    @Override
//    public void draw(Batch batch, float delta) {
//        batch.begin();
//        batch.draw(currentFrame, currentEntityPosition.x, currentEntityPosition.y);
//        batch.draw(currentFrame2, currentEntityPosition.x, currentEntityPosition.y);
//        batch.end();
//    }
//
//    private void attack(MapManager mapMgr) {
//        currentState = Entity.State.MELEE_ATTACK;
//        //cool down
//    }
//
//    private void chase(MapManager mapMgr) {
//        Vector2 playerCurrentPosition = mapMgr.getPlayer().getCurrentPosition();
//
//        if (playerCurrentPosition.x > currentEntityPosition.x) {
//            currentState = Entity.State.RUN;
//            currentDirection = Entity.Direction.RIGHT;
//            currentEntityPosition.x+=1.5f;
//        }
//        if (playerCurrentPosition.x < currentEntityPosition.x){
//            currentState = Entity.State.RUN;
//            currentDirection = Entity.Direction.LEFT;
//            currentEntityPosition.x-=1.5f;
//        }
//        if (playerCurrentPosition.y > currentEntityPosition.y) {
//            currentEntityPosition.y+=1.5f;
//        }
//        if (playerCurrentPosition.y < currentEntityPosition.y) {
//            currentEntityPosition.y-=1.5f;
//        }
//
////        float dx = playerCurrentPosition.x - currentEntityPosition.x;
////        float dy = playerCurrentPosition.y - currentEntityPosition.y;
////
////        float norm = (float) Math.sqrt(dx*dx + dy*dy);
////
////        //normalization:
////        float wx = dx/norm;
////        float wy = dy/norm;
////
////        currentEntityPosition.x+=wx;
////        currentEntityPosition.y+=wy;
//
////        tempEntities.clear();
////        tempEntities.addAll(mapMgr.getCurrentMapEntities());
////        tempEntities.addAll(mapMgr.getCurrentMapQuestEntities());
////
////        for(Entity mapEntity: tempEntities){
////            Rectangle mapEntityBoundingBox = mapEntity.getVectorBoundingBox();
////
////            dx = currentEntityPosition.x - mapEntityBoundingBox.x;
////            dy = currentEntityPosition.y - mapEntityBoundingBox.y;
////
////            norm = (float) Math.sqrt(dx*dx + dy*dy);
////
////            //normalization:
////            float ox = dx/norm;
////            float oy = dy/norm;
////
////            //add scaling to get the repulsion force we want
////            wx += ox/norm;
////            wy += oy/norm;
////        }
////        tempEntities.clear();
//
////        float norm_of_w = (float) Math.sqrt(wx*wx + wy*wy);
////        float vx = 1 * wx / norm_of_w;
////        float vy = 1 * wy / norm_of_w;
////
////        currentEntityPosition.x+=vx;
////        currentEntityPosition.y+=vy;
//    }
//
//    private void takeDamage() {
//
//    }
//
//    private void dead() {
//
//    }
//
//    private void goToPoint(Vector2 point) {
//        if (point.x > currentEntityPosition.x) {
//            currentEntityPosition.x+=0.4f;//6
//        }
//        if (point.x < currentEntityPosition.x){
//            currentEntityPosition.x-=0.4f;
//        }
//        if (point.y > currentEntityPosition.y) {
//            currentEntityPosition.y+=0.4f;
//        }
//        if (point.y < currentEntityPosition.y) {
//            currentEntityPosition.y-=0.4f;
//        }
//        if (point.x==currentEntityPosition.x && point.y==currentEntityPosition.y) {
//            detentionActive = false;
//            moveActive = false;
//        }
//    }
//
//
//
//    @Override
//    protected boolean isCollisionWithMapEntities(Entity entity, MapManager mapMgr){
//        //Test against player
//        if( isCollision(entity, mapMgr.getPlayer()) ) {
//            return true;
//        }
//
//        if( super.isCollisionWithMapEntities(entity, mapMgr) ){
//            return true;
//        }
//
//        return false;
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
//    public boolean keyTyped(char character) {
//        return false;
//    }
//
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
//        return false;
//    }
//
//}
