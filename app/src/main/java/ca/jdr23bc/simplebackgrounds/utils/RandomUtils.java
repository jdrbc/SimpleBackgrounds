package ca.jdr23bc.simplebackgrounds.utils;

import java.util.Random;

public class RandomUtils {
    public static final Random random = new Random();

    public static int getRandomIntInRange(int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static float getRandomFloatInRange(float min, float max) {
        float randomFloat = random.nextFloat();
        return min + (randomFloat * (max - min));
    }
}
