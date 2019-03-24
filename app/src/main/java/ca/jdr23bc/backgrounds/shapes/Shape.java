package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;
import android.support.annotation.NonNull;

import ca.jdr23bc.backgrounds.utils.RandomUtils;

public abstract class Shape {
    private final PointF topLeft;
    private final PointF bottomRight;

    protected Shape(PointF topLeft, PointF bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public float getWidth() {
        return bottomRight.x - topLeft.x;
    }

    public float getHeight() {
        return bottomRight.y - topLeft.y;
    }

    @NonNull
    @Override
    public String toString() {
        return "{ name: " + this.getClass().getSimpleName() + ", " +
                "topLeft: " + topLeft + ", " +
                "bottomRight: " + bottomRight + " }";
    }

    PointF getTopLeft() {
        return topLeft;
    }

    PointF getBottomRight() {
        return bottomRight;
    }

    private PointF getMiddleRight() {
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

    @SuppressWarnings("SameParameterValue")
    float getRandomFloatInRange(float min, float max) {
        if (min <= 0 || max <= min) {
            return max;
        }
        return RandomUtils.getRandomFloatInRange(min, max);
    }
}
