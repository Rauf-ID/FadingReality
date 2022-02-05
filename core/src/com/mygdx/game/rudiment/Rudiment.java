package com.mygdx.game.rudiment;

import com.mygdx.game.component.Player;
import com.mygdx.game.entity.Entity;

public interface Rudiment {

    void equipRudiment(Entity player);
    void unequipRudiment(Entity player);
    void activateRudiment(Player player);

    String getRudimentType();

}
