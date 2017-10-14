package ca.jdr23bc.simplebackgrounds.Shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

public class RandomDots extends Shape {
    public static final int MAX_CIRCLE_SIZE_RATIO_TO_WIDTH = 4;
    public static final int MIN_CIRCLE_SIZE_RATIO_TO_WIDTH = 32;

    public static final int MAX_CIRCLE_COUNT = 16;
    public static final int MIN_CIRCLE_COUNT = 1;

    public static final int MAX_INNER_CIRCLES = 9;
    public static final int MIN_INNER_CIRCLES = 1;

    public static final float MAX_INNER_CIRCLE_STEP = 0.7f;
    public static final float MIN_INNER_CIRCLE_STEP = 0.5f;

    public static final float MAX_TARGET_VALUE_STEP = 1.25f;
    public static final float MIN_TARGET_VALUE_STEP = 0.75f;

    int num;
    int minDotWidth;
    int maxDotWidth;

    public boolean targets;
    int numInnerCircles = MIN_INNER_CIRCLES;
    float innerCircleStep = MIN_INNER_CIRCLE_STEP;
    float innerCircleValueStep = MIN_INNER_CIRCLE_STEP;

    boolean sameColors;
    int primaryColor = Color.BLACK;

    public RandomDots(int width, int height, Shape parentPattern) {
        super(parentPattern);
        init(width, height);
    }

    public RandomDots(int width, int height) {
        super(width, height);
        init(width, height);
    }

    private void init(int width, int height) {
        this.minDotWidth = width / MIN_CIRCLE_SIZE_RATIO_TO_WIDTH;
        this.maxDotWidth = width / MAX_CIRCLE_SIZE_RATIO_TO_WIDTH;
        this.num = nextIntInRange(MIN_CIRCLE_COUNT, MAX_CIRCLE_COUNT);

        this.targets = random.nextBoolean();
        this.numInnerCircles = nextIntInRange(MIN_INNER_CIRCLES, MAX_INNER_CIRCLES);
        this.innerCircleStep = nextFloatInRange(MIN_INNER_CIRCLE_STEP, MAX_INNER_CIRCLE_STEP);
        this.innerCircleValueStep = nextFloatInRange(MIN_TARGET_VALUE_STEP, MAX_TARGET_VALUE_STEP);

        this.sameColors = random.nextBoolean();
        if (sameColors) {
            this.primaryColor = colorScheme.popRandom();
        } else {
            this.primaryColor = colorScheme.getRandom();
        }
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < num; i++) {
            Circle c = getDot();
            c.draw(canvas);
        }
    }

    public Circle getDot() {
        PointF p = new PointF();
        p.x = random.nextFloat() * width;
        p.y = random.nextFloat() * height;
        Float radius = random.nextFloat() * (maxDotWidth - minDotWidth) + minDotWidth;
        Circle c = new Circle(radius, p, this);
        if (targets) {
            c.target = targets;
            c.innerCircleStep = innerCircleStep;
            c.numInnerCircles = numInnerCircles;
            c.innerCircleValueStep = innerCircleValueStep;
        }

        if (sameColors) {
            c.primaryColor = primaryColor;
        } else {
            c.primaryColor = colorScheme.getRandom();
        }
        return c;
    }
}
