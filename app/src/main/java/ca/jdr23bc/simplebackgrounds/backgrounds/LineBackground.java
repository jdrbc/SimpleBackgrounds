package ca.jdr23bc.simplebackgrounds.backgrounds;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import ca.jdr23bc.simplebackgrounds.colors.ColorScheme;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class LineBackground implements Background {

    public enum Direction { VERTICAL, HORIZONTAL {}}

    public int MIN_COUNT = 1;
    public int MAX_COUNT = 9;

    public int MAX_WIDTH = 150;
    public int MIN_WIDTH = 25;

    public int MIN_ALPHA = 100;
    public int MAX_ALPHA = 255;

    int alpha = MIN_ALPHA;
    Direction direction;
    boolean sameDirection;
    boolean sameColor;
    int color;
    boolean sameWeight;
    float strokeWeight;
    int count;
    ColorScheme colorScheme;

    public LineBackground() {
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
        count = RandomUtils.getRandomIntInRange(MIN_COUNT, MAX_COUNT);
    }

    @Override
    public void fill(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorScheme.popRandom());
        canvas.drawPaint(paint);

        paint.setAntiAlias(true);
        for (int i = 0; i < count; i++) {
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
                start.x = RandomUtils.random.nextInt(width);
                end.y = height + 500;
                end.x = RandomUtils.random.nextInt(width);
            } else {
                start.y = RandomUtils.random.nextInt(height);
                start.x = -500;
                end.y = RandomUtils.random.nextInt(height);
                end.x = width + 500;
            }
            canvas.drawLine(start.x, start.y, end.x, end.y, paint);
        }
    }
}
