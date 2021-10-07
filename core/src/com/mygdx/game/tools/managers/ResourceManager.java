package com.mygdx.game.tools.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.entity.Entity;

public class ResourceManager {

    public static enum Atlas {
        ATLAS,
        ATLAS_ELITE_KNIGHT,
    }

    public AssetManager assetManager;

    // public Json json;

    public TextureAtlas ATLAS, ATLAS_SECURITY_MECHANISM, ATLAS_POLICE, ATLAS_ELITE_KNIGHT, ATLAS_ISB, ATLAS_AMELIA, ATLAS_KING, ATLAS_EARTHLINGS, ATLAS_MAP_OBJECTS;

    public static Skin SKIN;
    public World world;
    public String language;


    //Animation
    public Animation<Sprite> playerAnimIdleRight;
    public Animation<Sprite> playerAnimIdleLeft;
    public Animation<Sprite> playerAnimIdleUp;
    public Animation<Sprite> playerAnimIdleDown;
    public Animation<Sprite> playerAnimRunRight;
    public Animation<Sprite> playerAnimRunLeft;
    public Animation<Sprite> playerAnimRunUp;
    public Animation<Sprite> playerAnimRunDown;
    public Animation<Sprite> playerAnimWalkRight;
    public Animation<Sprite> playerAnimWalkLeft;
    public Animation<Sprite> playerAnimWalkUp;
    public Animation<Sprite> playerAnimWalkDown;
    public Animation<Sprite> playerAnimGunRight;
    public Animation<Sprite> playerAnimGunLeft;
    public Animation<Sprite> playerAnimGunUp;
    public Animation<Sprite> playerAnimGunDown;
    public Animation<Sprite> playerRifleAnimRight;
    public Animation<Sprite> playerRifleAnimLeft;
    public Animation<Sprite> playerAnimAttackRight;
    public Animation<Sprite> playerAnimAttackLeft;
    public Animation<Sprite> playerAnimAttackUp;
    public Animation<Sprite> playerAnimAttackDown;
    public Animation<Sprite> playerAnimAttackLeft2;
    public Animation<Sprite> playerAnimAttackRight2;
    public Animation<Sprite> playerAnimDashRight;
    public Animation<Sprite> playerAnimBarDrink;
    public Animation<Sprite> playerAnimHurtAmelia;
    public Animation<Sprite> playerAnimUseRudiment1;

    public Animation<Sprite> securityMechanismAnimIdle;
    public Animation<Sprite> securityMechanismAnimOpenGate;
    public Animation<Sprite> securityMechanismAnimHand;
    public Animation<Sprite> securityMechanismAnimWalkDown;
    public Animation<Sprite> securityMechanismAnimWalkDownShadow;

    public Animation<Sprite> roninAnimIdleRight;
    public Animation<Sprite> roninAnimIdleLeft;
    public Animation<Sprite> roninAnimRunRight;
    public Animation<Sprite> roninAnimRunLeft;
    public Animation<Sprite> roninAnimAttackRight;
    public Animation<Sprite> roninAnimAttackLeft;
    public Animation<Sprite> roninAnimDeadRight;
    public Animation<Sprite> roninAnimDeadLeft;

    public Animation<Sprite> policeAnimIdleRight;
    public Animation<Sprite> policeAnimIdleLeft;
    public Animation<Sprite> policeAnimRunRight;
    public Animation<Sprite> policeAnimRunLeft;
    public Animation<Sprite> policeAnimShootingRight;
    public Animation<Sprite> policeAnimShootingLeft;
    public Animation<Sprite> policeAnimLookedAround;
    public Animation<Sprite> policeAnimStops;
    public Animation<Sprite> policeAnimDetention;

    public Animation<Sprite> eliteKnightAnimIdleRight;
    public Animation<Sprite> eliteKnightAnimIdleLeft;
    public Animation<Sprite> eliteKnightAnimIdle2Right;
    public Animation<Sprite> eliteKnightAnimIdle2Left;
    public Animation<Sprite> eliteKnightAnimRunRight;
    public Animation<Sprite> eliteKnightAnimRunLeft;
    public Animation<Sprite> eliteKnightAnimAttackRight;
    public Animation<Sprite> eliteKnightAnimAttackLeft;

    public Animation<Sprite> S1_AnimIdleRight;
    public Animation<Sprite> S1_AnimIdleLeft;
    public Animation<Sprite> S2_AnimIdleRight;
    public Animation<Sprite> S2_AnimIdleLeft;
    public Animation<Sprite> S3_AnimIdleRight;
    public Animation<Sprite> S3_AnimIdleLeft;
    public Animation<Sprite> S1_AnimComingRight;
    public Animation<Sprite> S1_AnimComingLeft;
    public Animation<Sprite> S2_AnimComingRight;
    public Animation<Sprite> S2_AnimComingLeft;
    public Animation<Sprite> S3_AnimComingRight;
    public Animation<Sprite> S3_AnimComingLeft;

    public Animation<Sprite> ameliaAnimBarSit;
    public Animation<Sprite> ameliaAnimRunDown;

    public Animation<Sprite> kingAnimTalk;
    public Animation<Sprite> kingAnimDeath;

    public Animation<Sprite> earthlingsAnimDanceD2;
    public Animation<Sprite> earthlingsAnimDanceG1;
    public Animation<Sprite> earthlingsAnimDanceE1;
    public Animation<Sprite> earthlingsAnimDanceD1;
    public Animation<Sprite> earthlingsAnimDanceBartender;
    public Animation<Sprite> earthlingsAnimDanceHologramG2;
    public Animation<Sprite> earthlingsAnimDistortionHologramG2;

    public Animation<Sprite> gateMehanLeft;
    public Animation<Sprite> gateMehanRight;

    public Animation<Sprite> bloodRight;
    public Animation<Sprite> bloodLeft;
    public Animation<Sprite> poolBlood1;
    public Animation<Sprite> poolBlood2;
    public Animation<Sprite> poolBlood3;
    public Animation<Sprite> splashBlood1Right;
    public Animation<Sprite> splashBlood1Left;
    public Animation<Sprite> splashBlood2Right;
    public Animation<Sprite> splashBlood2Left;
    public Animation<Sprite> splashBlood3Right;
    public Animation<Sprite> splashBlood3Left;
    public Animation<Sprite> nulls;

    //idle Sprite
    public Sprite idleUp, idleDown, idleRight, idleLeft;

    public Sprite roninDeadLeft, roninDeadLeft2, roninDeadRight, roninDeadRight2;

    public Sprite lightClubRed, lightClubGreen;

    public Texture texture, texture2, texture3, texture4;

    public Texture textureBackgroundPDA, textureButtonNonePDA, textureTCoinLogo;

    public Texture textureMenuBackground;


    public ResourceManager() {
        SKIN = new Skin(Gdx.files.internal("uiskin.json"));
        world = new World(new Vector2(0,0), false);
        language = "main/languages/english";

        assetManager = new AssetManager();
        assetManager.load("main/entities/player/newPlayer.atlas", TextureAtlas.class);
        assetManager.load("main/entities/bosses/securityMechanism/securityMechanism.atlas", TextureAtlas.class);
        assetManager.load("main/entities/enemies/police/police.atlas", TextureAtlas.class);
        assetManager.load("main/entities/enemies/eliteKnight/eliteKnight.atlas", TextureAtlas.class);
        assetManager.load("main/entities/enemies/ISB/ISB.atlas", TextureAtlas.class);
        assetManager.load("main/entities/npc/amelia/amelia.atlas", TextureAtlas.class);
        assetManager.load("main/entities/npc/king/king.atlas", TextureAtlas.class);
        assetManager.load("main/entities/npc/earthlings/earthlings.atlas", TextureAtlas.class);
        assetManager.load("maps/mapObjects.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        ATLAS = assetManager.get("main/entities/player/newPlayer.atlas", TextureAtlas.class);
        ATLAS_SECURITY_MECHANISM = assetManager.get("main/entities/bosses/securityMechanism/securityMechanism.atlas", TextureAtlas.class);
        ATLAS_POLICE = assetManager.get("main/entities/enemies/police/police.atlas", TextureAtlas.class);
        ATLAS_ELITE_KNIGHT = assetManager.get("main/entities/enemies/eliteKnight/eliteKnight.atlas", TextureAtlas.class);
        ATLAS_ISB = assetManager.get("main/entities/enemies/ISB/ISB.atlas", TextureAtlas.class);
        ATLAS_AMELIA = assetManager.get("main/entities/npc/amelia/amelia.atlas", TextureAtlas.class);
        ATLAS_KING = assetManager.get("main/entities/npc/king/king.atlas", TextureAtlas.class);
        ATLAS_EARTHLINGS = assetManager.get("main/entities/npc/earthlings/earthlings.atlas", TextureAtlas.class);
        ATLAS_MAP_OBJECTS = assetManager.get("maps/mapObjects.atlas", TextureAtlas.class);

        playerAnimIdleRight = new Animation<Sprite>(0.2f, ATLAS.createSprites("IDLE_RIGHT"));
        playerAnimIdleLeft = new Animation<Sprite>(0.2f, ATLAS.createSprites("IDLE_LEFT"));
        playerAnimIdleUp = new Animation<Sprite>(0.2f, ATLAS.createSprites("IDLE_UP"));
        playerAnimIdleDown = new Animation<Sprite>(0.06f, ATLAS.createSprites("IDLE_DOWN"));
        playerAnimWalkRight = new Animation<Sprite>(0.14f, ATLAS.createSprites("WALK_RIGHT"));
        playerAnimWalkLeft = new Animation<Sprite>(0.14f, ATLAS.createSprites("WALK_LEFT"));
        playerAnimRunRight = new Animation<Sprite>(0.06f, ATLAS.createSprites("RUN_RIGHT"));
        playerAnimRunLeft = new Animation<Sprite>(0.06f, ATLAS.createSprites("RUN_LEFT"));
        playerAnimRunUp = new Animation<Sprite>(0.06f, ATLAS.createSprites("RUN_UP"));
        playerAnimRunDown = new Animation<Sprite>(0.06f, ATLAS.createSprites("RUN_DOWN"));
        playerAnimGunRight = new Animation<Sprite>(0.09f, ATLAS.createSprites("WEAPON_RIGHT"));
        playerAnimGunLeft = new Animation<Sprite>(0.09f, ATLAS.createSprites("WEAPON_LEFT"));
        playerAnimGunUp = new Animation<Sprite>(0.06f, ATLAS.createSprites("WEAPON_UP"));
        playerAnimGunDown = new Animation<Sprite>(0.06f, ATLAS.createSprites("WEAPON_DOWN"));
        playerRifleAnimRight = new Animation<Sprite>(0.06f, ATLAS.createSprites("RIFLE_RIGHT"));
        playerRifleAnimLeft = new Animation<Sprite>(0.06f, ATLAS.createSprites("RIFLE_LEFT"));
        playerAnimAttackRight = new Animation<Sprite>(0.05f, ATLAS.createSprites("ATTACK_RIGHT")); //11
        playerAnimAttackLeft = new Animation<Sprite>(0.05f, ATLAS.createSprites("ATTACK_LEFT")); //11
        playerAnimAttackUp = new Animation<Sprite>(0.05f, ATLAS.createSprites("ATTACK_UP")); //11
        playerAnimAttackDown = new Animation<Sprite>(0.05f, ATLAS.createSprites("ATTACK_DOWN")); //11
        playerAnimAttackLeft2 = new Animation<Sprite>(0.05f, ATLAS.createSprites("ATTACK_LEFT_2")); //13
        playerAnimAttackRight2 = new Animation<Sprite>(0.05f, ATLAS.createSprites("ATTACK_RIGHT_2")); //13
        playerAnimDashRight = new Animation<Sprite>(0.045f, ATLAS.createSprites("DASH_RIGHT"));
        playerAnimBarDrink = new Animation<Sprite>(0.12f, ATLAS.createSprites("BAR_DRINK"));
        playerAnimHurtAmelia = new Animation<Sprite>(0.14f, ATLAS.createSprites("HURT_AMELIA"));
        playerAnimUseRudiment1 = new Animation<Sprite>(0.05f, ATLAS.createSprites("USE_RUDIMENT_1"));

        securityMechanismAnimIdle = new Animation<Sprite>(0.05f, ATLAS_SECURITY_MECHANISM.createSprites("IDLE"));
        securityMechanismAnimOpenGate = new Animation<Sprite>(0.05f, ATLAS_SECURITY_MECHANISM.createSprites("OPEN_GATE"));
        securityMechanismAnimHand = new Animation<Sprite>(0.05f, ATLAS_SECURITY_MECHANISM.createSprites("OPEN_GATE_HAND"));
        securityMechanismAnimWalkDown = new Animation<Sprite>(0.17f, ATLAS_SECURITY_MECHANISM.createSprites("WALK_DOWN"));
        securityMechanismAnimWalkDownShadow = new Animation<Sprite>(0.17f, ATLAS_SECURITY_MECHANISM.createSprites("WALK_DOWN_SHADOW"));

        roninAnimIdleRight = new Animation<Sprite>(0.06f, ATLAS.createSprites("RONIN_IDLE_RIGHT"));
        roninAnimIdleLeft = new Animation<Sprite>(0.06f, ATLAS.createSprites("RONIN_IDLE_LEFT"));

        roninAnimRunRight = new Animation<Sprite>(0.05f, ATLAS.createSprites("RONIN_RUN_RIGHT"));
        roninAnimRunLeft = new Animation<Sprite>(0.05f, ATLAS.createSprites("RONIN_RUN_LEFT"));

        roninAnimAttackRight = new Animation<Sprite>(0.06f, ATLAS.createSprites("RONIN_ATTACK_RIGHT"));
        roninAnimAttackLeft = new Animation<Sprite>(0.06f, ATLAS.createSprites("RONIN_ATTACK_LEFT"));

        roninAnimDeadRight = new Animation<Sprite>(0.2f, ATLAS.createSprites("RONIN_DEAD_RIGHT"));
        roninAnimDeadLeft = new Animation<Sprite>(0.2f, ATLAS.createSprites("RONIN_DEAD_LEFT"));

        policeAnimIdleRight = new Animation<Sprite>(0.2f, ATLAS_POLICE.createSprites("IDLE_RIGHT"));
        policeAnimIdleLeft = new Animation<Sprite>(0.2f, ATLAS_POLICE.createSprites("IDLE_LEFT"));
        policeAnimRunRight = new Animation<Sprite>(0.2f, ATLAS_POLICE.createSprites("RUN_RIGHT"));
        policeAnimRunLeft = new Animation<Sprite>(0.2f, ATLAS_POLICE.createSprites("RUN_LEFT"));
        policeAnimShootingRight = new Animation<Sprite>(0.06f, ATLAS_POLICE.createSprites("SHOOTING_RIGHT"));
        policeAnimShootingLeft = new Animation<Sprite>(0.06f, ATLAS_POLICE.createSprites("SHOOTING_LEFT"));
        policeAnimLookedAround = new Animation<Sprite>(0.1f, ATLAS_POLICE.createSprites("LOOKED_AROUND"));
        policeAnimStops = new Animation<Sprite>(0.1f, ATLAS_POLICE.createSprites("STOPS"));
        policeAnimDetention = new Animation<Sprite>(0.1f, ATLAS_POLICE.createSprites("DETENTION"));

        eliteKnightAnimIdleRight = new Animation<Sprite>(0.2f, ATLAS_ELITE_KNIGHT.createSprites("IDLE_RIGHT"));
        eliteKnightAnimIdleLeft = new Animation<Sprite>(0.2f, ATLAS_ELITE_KNIGHT.createSprites("IDLE_LEFT"));
        eliteKnightAnimIdle2Right = new Animation<Sprite>(0.2f, ATLAS_ELITE_KNIGHT.createSprites("IDLE2_RIGHT"));
        eliteKnightAnimIdle2Left = new Animation<Sprite>(0.2f, ATLAS_ELITE_KNIGHT.createSprites("IDLE2_LEFT"));
        eliteKnightAnimRunRight = new Animation<Sprite>(0.14f, ATLAS_ELITE_KNIGHT.createSprites("RUN_RIGHT"));
        eliteKnightAnimRunLeft = new Animation<Sprite>(0.14f, ATLAS_ELITE_KNIGHT.createSprites("RUN_LEFT"));
        eliteKnightAnimAttackRight = new Animation<Sprite>(0.03f, ATLAS_ELITE_KNIGHT.createSprites("ATTACK_RIGHT"));
        eliteKnightAnimAttackLeft = new Animation<Sprite>(0.03f, ATLAS_ELITE_KNIGHT.createSprites("ATTACK_LEFT"));

        S1_AnimIdleRight = new Animation<Sprite>(0.2f, ATLAS_ISB.createSprites("S1_IDLE_RIGHT"));
        S1_AnimIdleLeft = new Animation<Sprite>(0.2f, ATLAS_ISB.createSprites("S1_IDLE_LEFT"));
        S2_AnimIdleRight = new Animation<Sprite>(0.2f, ATLAS_ISB.createSprites("S2_IDLE_RIGHT"));
        S2_AnimIdleLeft = new Animation<Sprite>(0.2f, ATLAS_ISB.createSprites("S2_IDLE_LEFT"));
        S3_AnimIdleRight = new Animation<Sprite>(0.2f, ATLAS_ISB.createSprites("S3_IDLE_RIGHT"));
        S3_AnimIdleLeft = new Animation<Sprite>(0.2f, ATLAS_ISB.createSprites("S3_IDLE_LEFT"));
        S1_AnimComingRight = new Animation<Sprite>(0.12f, ATLAS_ISB.createSprites("S1_COMING_RIGHT"));
        S1_AnimComingLeft = new Animation<Sprite>(0.12f, ATLAS_ISB.createSprites("S1_COMING_LEFT"));
        S2_AnimComingRight = new Animation<Sprite>(0.13f, ATLAS_ISB.createSprites("S2_COMING_RIGHT"));
        S2_AnimComingLeft = new Animation<Sprite>(0.13f, ATLAS_ISB.createSprites("S2_COMING_LEFT"));
        S3_AnimComingRight = new Animation<Sprite>(0.12f, ATLAS_ISB.createSprites("S3_COMING_RIGHT"));
        S3_AnimComingLeft = new Animation<Sprite>(0.12f, ATLAS_ISB.createSprites("S3_COMING_LEFT"));

        ameliaAnimBarSit = new Animation<Sprite>(0.2f, ATLAS_AMELIA.createSprites("BAR_SIT"));
        ameliaAnimRunDown  = new Animation<Sprite>(0.06f, ATLAS_AMELIA.createSprites("RUN_DOWN"));

        kingAnimTalk = new Animation<Sprite>(0.1f, ATLAS_KING.createSprites("KING_TALK"));
        kingAnimDeath = new Animation<Sprite>(0.05f, ATLAS_KING.createSprites("KING_DEATH"));

        earthlingsAnimDanceD2 = new Animation<Sprite>(0.06f, ATLAS_EARTHLINGS.createSprites("DANCE_D2"));
        earthlingsAnimDanceG1 = new Animation<Sprite>(0.08f, ATLAS_EARTHLINGS.createSprites("DANCE_G1"));
        earthlingsAnimDanceE1 = new Animation<Sprite>(0.08f, ATLAS_EARTHLINGS.createSprites("DANCE_E1"));
        earthlingsAnimDanceD1 = new Animation<Sprite>(0.1f, ATLAS_EARTHLINGS.createSprites("DANCE_D1"));
        earthlingsAnimDanceBartender = new Animation<Sprite>(0.11f, ATLAS_EARTHLINGS.createSprites("BARTENDER"));
        earthlingsAnimDanceHologramG2 = new Animation<Sprite>(0.1f, ATLAS_EARTHLINGS.createSprites("HOLOGRAM_DANCE_G2"));
        earthlingsAnimDistortionHologramG2 = new Animation<Sprite>(0.1f, ATLAS_EARTHLINGS.createSprites("HOLOGRAM_DISTORTION_G2"));

        gateMehanLeft = new Animation<Sprite>(0.1f, ATLAS_MAP_OBJECTS.createSprite("GATE_MEHAN_LEFT"));
        gateMehanRight = new Animation<Sprite>(0.1f, ATLAS_MAP_OBJECTS.createSprite("GATE_MEHAN_RIGHT"));

        poolBlood1 = new Animation<Sprite>(0.05f, ATLAS.createSprites("POOL_BLOOD1"));
        poolBlood2 = new Animation<Sprite>(0.05f, ATLAS.createSprites("POOL_BLOOD2"));
        poolBlood3 = new Animation<Sprite>(0.05f, ATLAS.createSprites("POOL_BLOOD3"));
        splashBlood1Right = new Animation<Sprite>(0.05f, ATLAS.createSprites("SPLASH_BLOOD1_RIGHT"));
        splashBlood1Left = new Animation<Sprite>(0.05f, ATLAS.createSprites("SPLASH_BLOOD1_LEFT"));
        splashBlood2Right = new Animation<Sprite>(0.05f, ATLAS.createSprites("SPLASH_BLOOD2_RIGHT"));
        splashBlood2Left = new Animation<Sprite>(0.05f, ATLAS.createSprites("SPLASH_BLOOD2_LEFT"));
        splashBlood3Right = new Animation<Sprite>(0.05f, ATLAS.createSprites("SPLASH_BLOOD3_RIGHT"));
        splashBlood3Left = new Animation<Sprite>(0.05f, ATLAS.createSprites("SPLASH_BLOOD3_LEFT"));

        bloodRight = new Animation<Sprite>(0.1f, ATLAS.createSprites("BLOOD_RIGHT"));
        bloodLeft = new Animation<Sprite>(0.1f, ATLAS.createSprites("BLOOD_LEFT"));
        nulls = new Animation<Sprite>(0.1f, ATLAS.createSprites("NULL"));

        idleUp = ATLAS.createSprite("IDLE_UP", 1);
        idleDown = ATLAS.createSprite("IDLE_DOWN", 1);
        idleRight = ATLAS.createSprite("IDLE_RIGHT", 1);
        idleLeft = ATLAS.createSprite("IDLE_LEFT", 1);

        roninDeadLeft = ATLAS.createSprite("RONIN_DEAD_LEFT", 1);
        roninDeadLeft2 = ATLAS.createSprite("RONIN_DEAD_LEFT", 2);
        roninDeadRight = ATLAS.createSprite("RONIN_DEAD_RIGHT", 1);
        roninDeadRight2 = ATLAS.createSprite("RONIN_DEAD_RIGHT", 2);

        lightClubRed = ATLAS_MAP_OBJECTS.createSprite("LIGHT_CLUB_RED",1);
        lightClubGreen = ATLAS_MAP_OBJECTS.createSprite("LIGHT_CLUB_GREEN",1);

        texture = new Texture(Gdx.files.internal("stats.png"));
        texture2 = new Texture(Gdx.files.internal("items.png"));
        texture3 = new Texture(Gdx.files.internal("test3.png"));
        texture4 = new Texture(Gdx.files.internal("sonof.png"));
        textureBackgroundPDA = new Texture(Gdx.files.internal("PDAUI.png"));
        textureButtonNonePDA = new Texture(Gdx.files.internal("ButtonNonePDA.png"));
        textureTCoinLogo = new Texture(Gdx.files.internal("tCoinLogo.png"));
        textureMenuBackground = new Texture(Gdx.files.internal("MenuBackground.png"));

    }

    public void setLanguage(String language) {
        this.language=language;
    }

    public void dispose() {
        SKIN.dispose();
        world.dispose();
        assetManager.dispose();
    }

}
