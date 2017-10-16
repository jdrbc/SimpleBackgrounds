package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.utils.MathUtils;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class Circle extends Shape {

    private static final float MIN_CIRCLE_SIZE = 10;

    private float radius;
    private PointF center;

    public Circle(PointF topLeft, PointF bottomRight) {
        super(topLeft, bottomRight);
        this.center = MathUtils.getPointBetween(topLeft, bottomRight);
        this.radius = RandomUtils.getRandomFloatInRange(MIN_CIRCLE_SIZE, Math.min(getHeight(), getWidth()));
    }

    public PointF getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }
}
