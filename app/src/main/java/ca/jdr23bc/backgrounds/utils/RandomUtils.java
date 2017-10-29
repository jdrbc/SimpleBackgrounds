package ca.jdr23bc.backgrounds.utils;

import android.graphics.Color;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Random;

import ca.jdr23bc.backgrounds.shapes.tree.Branch;

public class RandomUtils {
    public static final Random random = new Random();

    public static int getRandomColor() {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static PointF getRandomPointInRect(PointF topLeft, PointF bottomRight) {
        return new PointF(
                getRandomFloatInRange(topLeft.x, bottomRight.x),
                getRandomFloatInRange(topLeft.y, bottomRight.y)
        );
    }

    // http://www.anderswallin.net/2009/05/uniform-random-points-in-a-circle-using-polar-coordinates/
    public static PointF getRandomPointInCircle(PointF center, float radius) {
        double randomAngle = random.nextDouble() * 2 * Math.PI;
        float randomMangitude = radius * (float) Math.sqrt(random.nextFloat());
        float y = (float) Math.sin(randomAngle) * randomMangitude;
        float x = (float) Math.cos(randomAngle) * randomMangitude;
        return new PointF(center.x + x, center.y + y);
    }

    public static Branch removeRandomElement(ArrayList<Branch> branches) {
        return branches.remove(getRandomIntInRange(0, branches.size()));
    }

    public static int getRandomIntInRange(int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static float getRandomFloatInRange(float min, float max) {
        float randomFloat = random.nextFloat();
        return min + (randomFloat * (max - min));
    }
}
