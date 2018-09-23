package ca.jdr23bc.backgrounds.backgrounds;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import ca.jdr23bc.backgrounds.colors.ColorScheme;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class LineBackground extends Background {

    public enum Direction { VERTICAL, HORIZONTAL }

    public static final int MIN_COUNT = 1;
    public static final int MAX_COUNT = 9;

    public static final int MAX_WIDTH = 150;
    public static final int MIN_WIDTH = 25;

    public static final int MIN_ALPHA = 100;
    public static final int MAX_ALPHA = 255;

    private int alpha = MIN_ALPHA;
    private Direction direction;
    private boolean sameDirection;
    private boolean sameColor;
    private int color;
    private boolean sameWeight;
    private float strokeWeight;
    private int numberOfLines;
    private int lineCount;
    private ColorScheme colorScheme;

    public LineBackground(int width, int height) {
        super(width, height);
        int rootColor = Color.rgb(
                RandomUtils.random.nextInt(256),
                RandomUtils.random.nextInt(256),
                RandomUtils.random.nextInt(256));
        this.colorScheme = new ColorScheme(rootColor);
        alpha = RandomUtils.getRandomIntInRange(MIN_ALPHA, MAX_ALPHA);
        sameDirection = RandomUtils.random.nextBoolean();
        direction = Direction.values()[RandomUtils.random.nextInt(2)];
        sameColor = RandomUtils.random.nextBoolean();
        color = colorScheme.getRandom();
        sameWeight = RandomUtils.random.nextBoolean();
        strokeWeight = RandomUtils.getRandomFloatInRange(MIN_WIDTH, MAX_WIDTH);
        numberOfLines = RandomUtils.getRandomIntInRange(MIN_COUNT, MAX_COUNT);
    }

    @Override
    public void init() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorScheme.popRandom());
        paint.setAntiAlias(true);
        getCanvas().drawPaint(paint);
        lineCount = 0;
    }

    @Override
    public Boolean hasNextDrawStep() {
        return lineCount < numberOfLines;
    }

    @Override
    public void drawStep() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        lineCount++;
        if (!sameColor) {
            paint.setColor(colorScheme.getRandom());
            paint.setAlpha(alpha);
        } else {
            paint.setColor(color);
            paint.setAlpha(RandomUtils.getRandomIntInRange(MIN_ALPHA, MAX_ALPHA));
        }

        if (!sameDirection) {
            direction = Direction.values()[RandomUtils.random.nextInt(2)];
        }

        if (!sameWeight) {
            strokeWeight = RandomUtils.getRandomFloatInRange(MIN_WIDTH, MAX_WIDTH);
        }
        paint.setStrokeWidth(strokeWeight);

        Point start = new Point();
        Point end = new Point();
        if (direction == Direction.VERTICAL) {
            start.y = -500;
            start.x = RandomUtils.random.nextInt(getWidth());
            end.y = getHeight() + 500;
            end.x = RandomUtils.random.nextInt(getWidth());
        } else {
            start.y = RandomUtils.random.nextInt(getHeight());
            start.x = -500;
            end.y = RandomUtils.random.nextInt(getHeight());
            end.x = getWidth() + 500;
        }
        getCanvas().drawLine(start.x, start.y, end.x, end.y, paint);
    }
}
