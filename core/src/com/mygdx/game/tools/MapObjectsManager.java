package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.FadingReality;
import com.mygdx.game.world.MapManager;

public class MapObjectsManager {

    private GateMehan gateMehan;
    private LightClub lightClub1, lightClub2, lightClub3, lightClub4;

    public MapObjectsManager() {
        gateMehan = new GateMehan();
        lightClub3 = new LightClub(300,900,215, 215,240, "GREEN");
        lightClub1 = new LightClub(600,900,215, 215,240, "RED");
        lightClub4 = new LightClub(900,900,130, 130,160, "RED");
        lightClub2 = new LightClub(1200,900,130, 130,160, "GREEN");
    }

    public void update(FadingReality game, MapManager mapMgr, float delta) {
        //GATE
        if (mapMgr.getCurrentMap().getNameMap().equals("MEHAN")) {
            if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1)) {
                gateMehan.openGate();
            } else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3)) {
                gateMehan.closeGate();
            }
            gateMehan.update(delta);
            gateMehan.draw(game.getBatch(), delta);
        }

        //LIGHT
        if (mapMgr.getCurrentMap().getNameMap().equals("VI_CLUB")) {
            lightClub1.update();
            lightClub1.draw(game.getBatch(), delta, "RED");
            lightClub2.update();
            lightClub2.draw(game.getBatch(), delta, "GREEN");
            lightClub3.update();
            lightClub3.draw(game.getBatch(), delta, "GREEN");
            lightClub4.update();
            lightClub4.draw(game.getBatch(), delta, "RED");
        }
    }

    public GateMehan getGateMehan() {
        return gateMehan;
    }
}
