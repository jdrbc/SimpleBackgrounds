package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import java.util.Iterator;

public class Star extends Shape implements Iterator<Pair<PointF, PointF>> {
    private static final String TAG = Star.class.getCanonicalName();
    private static final float TWO_PI = (float) (2 * Math.PI);
    private static final float MIN_DISTANCE_BETWEEN_POINTS = 10;
    private static final float MIN_NUMBER_OF_POINTS = 3f;
    private static final float MAX_ANGLE_BETWEEN_POINTS = TWO_PI / MIN_NUMBER_OF_POINTS;
    private static final Integer MIN_SKIP_POINTS_RATIO_TO_TOTAL_POINTS = 4;
    private static final Integer MAX_SKIP_POINTS_RATIO_TO_TOTAL_POINTS = 2;

    private final float radius;
    private PointF center;
    private PointF startingPoint;
    private int numberOfPoints;
    private float rotationStep;
    private final int skip;

    private int count;
    private int currentRotationStepCount;
    private float currentRotationStepStartingAngle;

    Star(PointF topLeft, PointF bottomRight) {
        super(topLeft, bottomRight);
        this.radius = Math.min(getWidth(),getHeight()) / 2;

        float minAngleBetweenPoints = 2 * ((float) Math.asin((MIN_DISTANCE_BETWEEN_POINTS / 2)/ radius));
        float randomAngleInRange = getRandomFloatInRange(minAngleBetweenPoints, MAX_ANGLE_BETWEEN_POINTS);
        this.setRotationStepAndNumberOfPoints(randomAngleInRange);

        int minSkip = numberOfPoints / MIN_SKIP_POINTS_RATIO_TO_TOTAL_POINTS;
        int maxSkip = numberOfPoints / MAX_SKIP_POINTS_RATIO_TO_TOTAL_POINTS;
        this.skip = getRandomIntInRange(minSkip, maxSkip);

        this.center = getRandomPoint();
    }

    public PointF getStartingPoint() {
        return startingPoint;
    }

    Star withIsCentered(Boolean isCentered) {
        if (isCentered) {
            this.center = getCenter();
        } else {
            this.center = getRandomPoint();
        }
        return this;
    }

    public void init() {
        this.count = 0;
        this.currentRotationStepCount = 1;
        this.currentRotationStepStartingAngle = (float) (Math.PI / 4);
        this.startingPoint = getPoint(currentRotationStepStartingAngle);
    }

    @Override
    public boolean hasNext() {
        return count < numberOfPoints;
    }

    @Override
    public Pair<PointF, PointF> next() {
        float rotation = rotationStep * skip;
        float prevAngle = (rotation * (currentRotationStepCount - 1) + currentRotationStepStartingAngle) % TWO_PI;
        Log.d(TAG, "prevAngle: " + prevAngle);
        @SuppressWarnings("RedundantCast") float nextAngle = (float) ((rotation * currentRotationStepCount + currentRotationStepStartingAngle) % TWO_PI);
        Log.d(TAG, "nextAngle: " + nextAngle);
        float diff = Math.abs(nextAngle - currentRotationStepStartingAngle);
        // If next angle has arrived at the starting angle for this rotation step, then move to next step
        if (diff < 0.0001) {
            currentRotationStepStartingAngle += rotationStep;
            currentRotationStepCount = 0;
        }
        currentRotationStepCount++;
        count++;
        return new Pair<>(getPoint(prevAngle), getPoint(nextAngle));
    }

    @NonNull
    @Override
    public String toString() {
        return "star radius: " + radius + "; center: " + center.toString() + "; number of points: " +
                numberOfPoints + "; rotation step " + rotationStep + "; and skip: " + skip;
    }

    private PointF getPoint(float angle) {
        return new PointF(getX(angle), getY(angle));
    }

    private float getX(float angle) {
        return (float) (Math.cos(angle) * radius) + center.x;
    }

    private float getY(float angle) {
        return (float) (Math.sin(angle) * radius) + center.y;
    }

    private void setRotationStepAndNumberOfPoints(float angle) {
        if (angle >= MAX_ANGLE_BETWEEN_POINTS) {
            rotationStep = MAX_ANGLE_BETWEEN_POINTS;
        } else {
            for (float i = MIN_NUMBER_OF_POINTS; true; i++) {
                float highFactor = TWO_PI / i;
                float lowFactor = TWO_PI / (i + 1);
                if (highFactor >= angle && angle >= lowFactor) {
                    rotationStep = lowFactor;
                    numberOfPoints = (int) i + 1;
                    break;
                }
            }
        }
    }
}