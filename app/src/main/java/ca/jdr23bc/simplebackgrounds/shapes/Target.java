package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

import java.util.Iterator;

import ca.jdr23bc.simplebackgrounds.utils.MathUtils;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class Target extends Shape implements Iterator<Target.TargetRing> {
    public static final int MAX_RINGS_FOR_RANDOM_INIT_VALUE = 20;
    public static final int MIN_RINGS_FOR_RANDOM_INIT_VALUE = 4;

    TargetRing currentRing;
    PointF center;
    float radius;
    int ringCount;

    public Target(PointF topLeft, PointF bottomRight) {
        super(topLeft, bottomRight);
        center = MathUtils.getPointBetween(topLeft, bottomRight);
        radius = Math.min(
                MathUtils.getHeight(topLeft, bottomRight),
                MathUtils.getWidth(topLeft, bottomRight)
        ) / 2;
        ringCount = RandomUtils.getRandomIntInRange(
                MIN_RINGS_FOR_RANDOM_INIT_VALUE, MAX_RINGS_FOR_RANDOM_INIT_VALUE);
    }

    public Target withCenter(PointF center) {
        this.center = center;
        return this;
    }

    public Target withRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public Target withRingCount(int ringCount) {
        this.ringCount = ringCount;
        return this;
    }

    public void init() {
        currentRing = new TargetRing(center, radius, 1);
    }

    public float getRadius() {
        return this.radius;
    }

    public float getRingWidth() {
        return radius / (ringCount + 1);
    }

    @Override
    public boolean hasNext() {
        return currentRing.getRingNumber() <= ringCount;
    }

    @Override
    public TargetRing next() {
        currentRing = new TargetRing(
                center,
                currentRing.getRadius() - getRingWidth(),
                currentRing.getRingNumber() + 1);
        return currentRing;
    }

    public class TargetRing {
        PointF center;
        float radius;
        int ringNumber;
        public TargetRing(PointF center, float radius, int ringNumber) {
            this.center = center;
            this.radius = radius;
            this.ringNumber = ringNumber;
        }

        public int getRingNumber() {
            return ringNumber;
        }

        public float getRadius() {
            return radius;
        }

        public PointF getCenter() {
            return center;
        }
    }
}
