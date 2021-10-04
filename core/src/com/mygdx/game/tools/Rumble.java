package com.mygdx.game.tools;

import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Rumble {

    public static enum State {
        GUN,
        SWORD,
        NONE
    }

    private static float time = 0;
    private static float currentTime = 0;
    private static float power = 0;
    private static float currentPower = 0;
    private static Random random;
    private static Vector3 pos = new Vector3();

    private static int direction = 0;
    private static State state;


    public static void rumble(float rumblePower, float rumbleLength, int rumbleDirection, State rumbleState) {
        random = new Random();
        power = rumblePower;
        time = rumbleLength;
        currentTime = 0;

        direction = rumbleDirection;
        state = rumbleState;
    }

    public static Vector3 tick(float delta) {
        if (currentTime <= time) {
            currentPower = power * ((time - currentTime) / time);

            switch (state) {
                case GUN:
                    if(direction==0){
                        pos.x = 1 * currentPower;
                    } else {
                        pos.y = 1 * currentPower;
                    }
                    break;
                case SWORD:
                    pos.x = (random.nextFloat() - 0.5f) * 2 * currentPower;
                    pos.y = (random.nextFloat() - 0.5f) * 2 * currentPower;
                    break;

            }

            currentTime += delta;
        } else {
            time = 0;
        }
        return pos;
    }

    public static float getRumbleTimeLeft() {
        return time;
    }

    public static Vector3 getPos() {
        return pos;
    }
}