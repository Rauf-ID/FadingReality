package com.mygdx.game.tools;


import com.badlogic.gdx.graphics.Color;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Time {

    private int hour;
    private int min;
    private int sec;

    private Time(Calendar cal) {
        hour = cal.get(Calendar.HOUR_OF_DAY);
        min = cal.get(Calendar.MINUTE);
        sec = cal.get(Calendar.SECOND);
    }

    public int getHour() {
        return  hour;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public static Time getTime(int scale) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis() * scale);

        return new Time(cal);
    }

    public static Time getTime() {
        return getTime(1);
    }

    public Color getTint() {

        /*Credit goes to Ted Larue for this algorithm. Thanks Ted!*/
        double z = Math.cos((hour-14) * Math.PI / 12);
        float b = (float) (0.7f + 0.7f * (z + 1.0) / 2.0);

        return new Color(b-0.4f, b-0.4f, b, 1.0f);
    }
}
