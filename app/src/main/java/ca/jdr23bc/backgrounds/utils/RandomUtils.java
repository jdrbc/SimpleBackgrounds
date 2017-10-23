package ca.jdr23bc.backgrounds.utils;

import android.graphics.PointF;

import java.util.Random;

public class RandomUtils {
    public static final Random random = new Random();

    public static PointF getRandomPointInRect(PointF topLeft, PointF bottomRight) {
        return new PointF(
                getRandomFloatInRange(topLeft.x, bottomRight.x),
                getRandomFloatInRange(topLeft.y, bottomRight.y)
        );
    }

    public static int getRandomIntInRange(int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static float getRandomFloatInRange(float min, float max) {
        float randomFloat = random.nextFloat();
        return min + (randomFloat * (max - min));
    }
}
