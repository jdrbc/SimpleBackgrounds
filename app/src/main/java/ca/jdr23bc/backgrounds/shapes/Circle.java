package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;

import ca.jdr23bc.backgrounds.utils.MathUtils;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

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

    @Override
    public String toString() {
        return "{ name: " + Circle.class.getSimpleName() + ", " +
                "center: " + center + ", " +
                "radius: " + radius + " }";
    }
}
