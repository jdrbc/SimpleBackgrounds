package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;

import java.util.Iterator;

import ca.jdr23bc.backgrounds.utils.MathUtils;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class Target extends Shape implements Iterator<Target.TargetRing> {
    public static final int MAX_RINGS_FOR_RANDOM_INIT_VALUE = 20;
    public static final int MIN_RINGS_FOR_RANDOM_INIT_VALUE = 4;

    private TargetRing currentRing;
    private final PointF center;
    private final float radius;
    private int ringCount;

    Target(PointF topLeft, PointF bottomRight) {
        super(topLeft, bottomRight);
        center = MathUtils.getPointBetween(topLeft, bottomRight);
        radius = Math.min(
                MathUtils.getHeight(topLeft, bottomRight),
                MathUtils.getWidth(topLeft, bottomRight)
        ) / 2;
        ringCount = RandomUtils.getRandomIntInRange(
                MIN_RINGS_FOR_RANDOM_INIT_VALUE, MAX_RINGS_FOR_RANDOM_INIT_VALUE);
    }

    Target withRingCount(int ringCount) {
        this.ringCount = ringCount;
        return this;
    }

    public void init() {
        currentRing = new TargetRing(center, radius, 1);
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
        final PointF center;
        final float radius;
        final int ringNumber;
        TargetRing(PointF center, float radius, int ringNumber) {
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
