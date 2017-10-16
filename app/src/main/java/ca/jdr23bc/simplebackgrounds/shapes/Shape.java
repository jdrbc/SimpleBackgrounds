package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

import java.util.Random;

import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

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

    protected PointF getTopLeft() {
        return topLeft;
    }

    protected PointF getBottomRight() {
        return bottomRight;
    }

    protected PointF getCenter() {
        return new PointF(topLeft.x + (bottomRight.x - topLeft.x) / 2, topLeft.y + (bottomRight.y - topLeft.y) / 2);
    }

    protected PointF getRandomPoint() {
        return new PointF(random.nextFloat() * getWidth(), random.nextFloat() * getHeight());
    }

    protected int getRandomIntInRange(int min, int max) {
        if (min <= 0 || max <= min) {
            return max;
        }
        return RandomUtils.getRandomIntInRange(min, max);
    }

    protected float getRandomFloatInRange(float min, float max) {
        if (min <= 0 || max <= min) {
            return max;
        }
        return RandomUtils.getRandomFloatInRange(min, max);
    }
}
