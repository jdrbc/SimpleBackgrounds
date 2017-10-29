package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;

import java.util.Random;

import ca.jdr23bc.backgrounds.utils.RandomUtils;

public abstract class Shape {
    private PointF topLeft;
    private PointF bottomRight;
    private Random random;

    public Shape(PointF topLeft, PointF bottomRight) {
        this.random = new Random();
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public float getWidth() {
        return bottomRight.x - topLeft.x;
    }

    public float getHeight() {
        return bottomRight.y - topLeft.y;
    }

    @Override
    public String toString() {
        return "{ name: " + this.getClass().getSimpleName() + ", " +
                "topLeft: " + topLeft + ", " +
                "bottomRight: " + bottomRight + " }";
    }

    protected PointF getTopLeft() {
        return topLeft;
    }

    protected PointF getBottomRight() {
        return bottomRight;
    }

    protected PointF getMiddleRight() {
        return new PointF(bottomRight.x, bottomRight.y / 2);
    }

    protected PointF getCenter() {
        return new PointF(topLeft.x + (bottomRight.x - topLeft.x) / 2, topLeft.y + (bottomRight.y - topLeft.y) / 2);
    }

    protected PointF getUpperHalfCenter() {
        PointF midRight = getMiddleRight();
        return new PointF(topLeft.x + (midRight.x - topLeft.x) / 2, topLeft.y + (midRight.y - topLeft.y) / 2);
    }

    PointF getRandomPoint() {
        return RandomUtils.getRandomPointInRect(topLeft, bottomRight);
    }

    int getRandomIntInRange(int min, int max) {
        if (min <= 0 || max <= min) {
            return max;
        }
        return RandomUtils.getRandomIntInRange(min, max);
    }

    float getRandomFloatInRange(float min, float max) {
        if (min <= 0 || max <= min) {
            return max;
        }
        return RandomUtils.getRandomFloatInRange(min, max);
    }
}
