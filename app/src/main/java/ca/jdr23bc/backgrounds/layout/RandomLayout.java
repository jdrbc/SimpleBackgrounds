package ca.jdr23bc.backgrounds.layout;

import android.graphics.PointF;

import ca.jdr23bc.backgrounds.utils.RandomUtils;


public class RandomLayout extends Layout {

    private static final int MIN_NUM_RANDOM_INIT_POINTS = 3;
    private static final int MAX_NUM_RANDOM_INIT_POINTS = 30;
    private static final float MIN_RANDOM_CELL_RATIO = 1f / 16f;
    private static final float MAX_RANDOM_CELL_RATIO = 1f / 2f;

    private final int numPoints;
    private int currPoint;

    public RandomLayout() {
        numPoints = RandomUtils.getRandomIntInRange(
                MIN_NUM_RANDOM_INIT_POINTS,
                MAX_NUM_RANDOM_INIT_POINTS);
    }

    @Override
    public void init(PointF topLeft, PointF bottomRight) {
        super.init(topLeft, bottomRight);
        currPoint = 0;
    }

    @Override
    public Integer getNumberOfCells() {
        return numPoints;
    }

    @Override
    protected boolean finished() {
        return currPoint >= numPoints;
    }

    @Override
    public Cell next() {
        currPoint++;
        float ratio = RandomUtils.getRandomFloatInRange(MIN_RANDOM_CELL_RATIO, MAX_RANDOM_CELL_RATIO);
        float cellSize = ratio * Math.max(getHeight(), getWidth());
        PointF randomPoint = RandomUtils.getRandomPointInRect(getTopLeft(), getBottomRight());
        if (RandomUtils.random.nextBoolean()) {
            PointF bottomRight = new PointF(randomPoint.x + cellSize, randomPoint.y + cellSize);
            return new Cell(randomPoint, bottomRight);
        } else {
            PointF topLeft = new PointF(randomPoint.x - cellSize, randomPoint.y + cellSize);
            return new Cell(topLeft, randomPoint);
        }
    }
}
