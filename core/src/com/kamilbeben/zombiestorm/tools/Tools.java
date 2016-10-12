package com.kamilbeben.zombiestorm.tools;

import java.util.Random;

/**
 * Created by bezik on 12.10.16.
 */
public class Tools {

    public static final float speedMultiplier_1= 1f;
    public static final float speedMultiplier_2= 1.2f;
    public static final float speedMultiplier_3= 1.4f;
    public static final float speedMultiplier_4= 1.6f;
    public static final float speedMultiplier_5= 1.8f;
    public static final float speedMultiplier_6= 2f;

    public Tools() {

    }

    public static int randomFrom1To10() {
        return new Random().nextInt(9) + 1; //minimum + rn.nextInt(maxValue - minvalue + 1)
    }

    public static int roundTilePosition(float number) {
        if (number >= 0) {
            return (int) number;
        } else {
            return ((int) number) - 1;
        }
    }

    public static float getStaticObjectsSpeedLevel(int speedLevel) {
        float speed = 150f;
        switch (speedLevel) {
            default:
            case 1:
                speed = speed * speedMultiplier_1;
                break;
            case 2:
                speed = speed * speedMultiplier_2;
                break;
            case 3:
                speed = speed * speedMultiplier_3;
                break;
            case 4:
                speed = speed * speedMultiplier_4;
                break;
            case 5:
                speed = speed * speedMultiplier_5;
                break;
            case 6:
                speed = speed * speedMultiplier_6;
                break;
        }
        return speed;
    }
}
