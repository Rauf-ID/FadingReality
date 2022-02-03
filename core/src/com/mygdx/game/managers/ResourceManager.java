package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Hashtable;

public class ResourceManager {

    public enum AtlasType {
        ATLAS,
        ATLAS_ISB,
        ATLAS_KING,
        ATLAS_RONIN,
        ATLAS_AMELIA,
        ATLAS_ISB_S1, ATLAS_ISB_S2, ATLAS_ISB_S3,
        ATLAS_EARTHLING_D1, ATLAS_EARTHLING_D2, ATLAS_EARTHLING_E1, ATLAS_EARTHLING_G1, ATLAS_HOLOGRAM_G2, ATLAS_BARTENDER,
        ATLAS_POLICE_B1, ATLAS_POLICE_R1,
        ATLAS_MERCENARIES_M1,
        ATLAS_EARTHLINGS,
        ATLAS_ELITE_KNIGHT,
        ATLAS_SECURITY_MECHANISM,
        ATLAS_MAP_OBJECTS,
        ATLAS_EXOSKELETON_M1,
        ATLAS_MAP_ITEMS,
        NONE
    }

    public AssetManager assetManager;
    public TextureAtlas ATLAS, ATLAS_SECURITY_MECHANISM, ATLAS_ELITE_KNIGHT, ATLAS_AMELIA, ATLAS_KING, ATLAS_MAP_OBJECTS;
    private static Hashtable<AtlasType, TextureAtlas> atlasTable = new Hashtable<AtlasType, TextureAtlas>();
    public World world;
    public String language;

    public static final String PATH_TO_JSON_WEAPONS = "main/items/weapons.json";
    public static final String PATH_TO_JSON_RUDIMENTS = "main/items/rudiments.json";
    public static final String INVENTORY_ITEM = "main/items/items.json";
    public static String PLAYER_CONFIG = "main/entities/player/player.json";
    public static String EARTHLINGS_CONFIGS = "main/entities/npc/earthlings/earthlings.json";
    public static String ELITE_KNIGHT_CONFIGS = "main/entities/enemies/eliteKnight/eliteKnight.json";
    public static String POLICE_CONFIGS = "main/entities/enemies/police/police.json";
    public static String MERCENARIES_CONFIGS = "main/entities/npc/mercenaries/mercenaries.json";
    public static String TOWN_FOLK_CONFIGS = "main/entities/town_folk.json";
    public static String MERCHANT_CONFIGS = "main/entities/npc/merchants/merchants.json";
    public static String OVERSEER_CONFIGS = "main/entities/npc/overseer/overseer.json";
    public static String MECHANIC_CONFIGS = "main/entities/npc/mechanic/mechanic.json";
    public static String SCIENTIST_CONFIGS = "main/entities/npc/scientist/scientist.json";
    public static String EXOSKELETON_CONFIGS = "main/entities/exoskeletons/exoskeletons.json";
    public static String MAP_ITEMS_CONFIG = "main/entities/mapItems/mapItems.json";
    public static String SKILLS_CONFIG = "main/skills/skills.json";

    //Animation
    public Animation<Sprite> playerRifleAnimRight;
    public Animation<Sprite> playerRifleAnimLeft;
    public Animation<Sprite> playerAnimBarDrink;
    public Animation<Sprite> playerAnimHurtAmelia;
    public Animation<Sprite> playerAnimUseRudiment1;

    public Animation<Sprite> securityMechanismAnimIdle;
    public Animation<Sprite> securityMechanismAnimOpenGate;
    public Animation<Sprite> securityMechanismAnimHand;
    public Animation<Sprite> securityMechanismAnimWalkDown;
    public Animation<Sprite> securityMechanismAnimWalkDownShadow;

    public Animation<Sprite> eliteKnightAnimIdle2Right;
    public Animation<Sprite> S1_AnimComingRight;

    public Animation<Sprite> ameliaAnimBarSit;
    public Animation<Sprite> ameliaAnimRunDown;

    public Animation<Sprite> kingAnimTalk;
    public Animation<Sprite> kingAnimDeath;

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
    public Texture dialogueImg;
    public Texture textureNone;



    public ResourceManager() {
        world = new World(new Vector2(0,0), false);
        language = "main/languages/english";
        
        assetManager = new AssetManager();
        assetManager.load("textures/entities/player/player.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/enemies/ronin/ronin.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/enemies/ISB/ISB_S1.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/enemies/ISB/ISB_S2.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/enemies/ISB/ISB_S3.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/enemies/police/police_B1.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/enemies/police/police_R1.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/npc/earthlings/bartender.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/npc/earthlings/earthling_D1.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/npc/earthlings/earthling_D2.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/npc/earthlings/earthling_E1.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/npc/earthlings/earthling_G1.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/npc/earthlings/earthling_hologram_G2.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/npc/king/king.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/npc/amelia/amelia.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/exoskeletons/exoskeletonM1.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/exoskeletons/exoskeletonM1.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/npc/mercenaries/mercenariesM1.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/enemies/eliteKnight/eliteKnight.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/bosses/securityMechanism/securityMechanism.atlas", TextureAtlas.class);
        assetManager.load("textures/maps/mapObjects.atlas", TextureAtlas.class);
        assetManager.load("textures/entities/mapItems/mapItems.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        ATLAS = assetManager.get("textures/entities/player/player.atlas", TextureAtlas.class);
        ATLAS_SECURITY_MECHANISM = assetManager.get("textures/entities/bosses/securityMechanism/securityMechanism.atlas", TextureAtlas.class);
        ATLAS_ELITE_KNIGHT = assetManager.get("textures/entities/enemies/eliteKnight/eliteKnight.atlas", TextureAtlas.class);
        ATLAS_AMELIA = assetManager.get("textures/entities/npc/amelia/amelia.atlas", TextureAtlas.class);
        ATLAS_KING = assetManager.get("textures/entities/npc/king/king.atlas", TextureAtlas.class);
        ATLAS_MAP_OBJECTS = assetManager.get("textures/maps/mapObjects.atlas", TextureAtlas.class);

        playerRifleAnimRight = new Animation<Sprite>(0.06f, ATLAS.createSprites("RIFLE_RIGHT")); // Used in Weapon class
        playerRifleAnimLeft = new Animation<Sprite>(0.06f, ATLAS.createSprites("RIFLE_LEFT")); // Used in Weapon class

        playerAnimBarDrink = new Animation<Sprite>(0.12f, ATLAS.createSprites("BAR_DRINK"));
        playerAnimHurtAmelia = new Animation<Sprite>(0.14f, ATLAS.createSprites("HURT_AMELIA"));
        playerAnimUseRudiment1 = new Animation<Sprite>(0.05f, ATLAS.createSprites("USE_RUDIMENT_1"));
        securityMechanismAnimIdle = new Animation<Sprite>(0.05f, ATLAS_SECURITY_MECHANISM.createSprites("IDLE"));
        securityMechanismAnimOpenGate = new Animation<Sprite>(0.05f, ATLAS_SECURITY_MECHANISM.createSprites("OPEN_GATE"));
        securityMechanismAnimHand = new Animation<Sprite>(0.05f, ATLAS_SECURITY_MECHANISM.createSprites("OPEN_GATE_HAND"));
        securityMechanismAnimWalkDown = new Animation<Sprite>(0.17f, ATLAS_SECURITY_MECHANISM.createSprites("WALK_DOWN"));
        securityMechanismAnimWalkDownShadow = new Animation<Sprite>(0.17f, ATLAS_SECURITY_MECHANISM.createSprites("WALK_DOWN_SHADOW"));
        ameliaAnimBarSit = new Animation<Sprite>(0.2f, ATLAS_AMELIA.createSprites("BAR_SIT"));
        ameliaAnimRunDown  = new Animation<Sprite>(0.06f, ATLAS_AMELIA.createSprites("RUN_DOWN"));
        kingAnimTalk = new Animation<Sprite>(0.1f, ATLAS_KING.createSprites("KING_TALK"));
        kingAnimDeath = new Animation<Sprite>(0.05f, ATLAS_KING.createSprites("KING_DEATH"));
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

        dialogueImg = new Texture(Gdx.files.internal("textures/UI/dialogue2.png"));

        textureNone = new Texture(Gdx.files.internal("textures/UI/none.png"));

        texture = new Texture(Gdx.files.internal("textures/UI/stats.png"));
        texture2 = new Texture(Gdx.files.internal("textures/UI/items.png"));
        texture3 = new Texture(Gdx.files.internal("textures/UI/test3.png"));
        texture4 = new Texture(Gdx.files.internal("textures/UI/sonof.png"));
        textureBackgroundPDA = new Texture(Gdx.files.internal("textures/UI/PDAUI.png"));
        textureButtonNonePDA = new Texture(Gdx.files.internal("textures/UI/ButtonNonePDA.png"));
        textureTCoinLogo = new Texture(Gdx.files.internal("textures/UI/tCoinLogo.png"));
        textureMenuBackground = new Texture(Gdx.files.internal("textures/UI/MenuBackground.png"));

    }

    public TextureAtlas getAtlas(AtlasType atlasType) {
        TextureAtlas textureAtlas = null;

        switch(atlasType) {
            case ATLAS:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/player/player.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_ELITE_KNIGHT:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/enemies/eliteKnight/eliteKnight.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_RONIN:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/enemies/ronin/ronin.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_EARTHLING_D1:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/npc/earthlings/earthling_D1.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_EARTHLING_D2:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/npc/earthlings/earthling_D2.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_EARTHLING_E1:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/npc/earthlings/earthling_E1.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_EARTHLING_G1:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/npc/earthlings/earthling_G1.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_HOLOGRAM_G2:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/npc/earthlings/earthling_hologram_G2.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_BARTENDER:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/npc/earthlings/bartender.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_ISB_S1:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/enemies/ISB/ISB_S1.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_ISB_S2:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/enemies/ISB/ISB_S2.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_ISB_S3:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/enemies/ISB/ISB_S3.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_POLICE_B1:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/enemies/police/police_B1.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_POLICE_R1:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/enemies/police/police_5R1.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_MERCENARIES_M1:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/npc/mercenaries/mercenariesM1.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_EXOSKELETON_M1:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/exoskeletons/exoskeletonM1.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
            case ATLAS_MAP_ITEMS:
                textureAtlas = atlasTable.get(atlasType);
                if( textureAtlas == null ){
                    textureAtlas = assetManager.get("textures/entities/mapItems/mapItems.atlas", TextureAtlas.class);
                    atlasTable.put(atlasType, textureAtlas);
                }
                break;
        }
        return textureAtlas;
    }

    public void setLanguage(String language) {
        this.language=language;
    }

    public void dispose() {
        world.dispose();
        assetManager.dispose();
    }

}
