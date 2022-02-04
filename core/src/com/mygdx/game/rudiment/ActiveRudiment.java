package com.mygdx.game.rudiment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.component.Message;
import com.mygdx.game.component.Player;
import com.mygdx.game.entity.Entity;

public class ActiveRudiment extends PassiveRudiment{
    private Vector2 activeZone;

    public ActiveRudiment(){}

    public ActiveRudiment(ActiveRudiment activeRudiment){
        this.setRudimentID(activeRudiment.getRudimentID());
        this.setName(activeRudiment.getName());
    }

    public void activateRudiment(Player player){
        switch (this.getName()){
            case "Vortex":
                Array<Entity> nearbyEnemies = player.checkForNearbyEnemies();
                for( Entity mapEntity : nearbyEnemies) {
                    mapEntity.sendMessage(Message.MESSAGE.STUN);
                }
                break;
        }

    }

}
