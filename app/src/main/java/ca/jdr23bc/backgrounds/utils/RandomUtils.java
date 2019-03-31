package ca.jdr23bc.backgrounds.utils;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.PointF;

import java.util.Random;

public class RandomUtils {
    public static final Random random = new Random() ;

    /**
     * @return random RGB color
     */
    public static int getRandomColor() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ColorSpace lab = ColorSpace.get(ColorSpace.Named.CIE_LAB);
            Color randomLab = Color.valueOf(
                    getRandomFloatInRange(0, 100),
                    getRandomIntInRange(-128, 128),
                    getRandomIntInRange(-128, 128),
                    1.0f,
                    lab
            );
            float[] randomRgb = ColorSpace.connect(lab).transform(randomLab.getComponents());
            return Color.rgb(
                    randomRgb[0],
                    randomRgb[1],
                    randomRgb[2]
            );
        } else {
            return Color.rgb(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
            );
        }
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

    public static int getRandomIntInRange(int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    public static float getRandomFloatInRange(float min, float max) {
        float randomFloat = random.nextFloat();
        return min + (randomFloat * (max - min));
    }
}
