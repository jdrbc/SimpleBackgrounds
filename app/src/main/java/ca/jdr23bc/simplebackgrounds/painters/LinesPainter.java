package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import ca.jdr23bc.simplebackgrounds.ShapesOld.Lines;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class LinesPainter extends Painter {

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

    public LinesPainter() {
        alpha = RandomUtils.getRandomIntInRange(MIN_ALPHA, MAX_ALPHA);
        sameDirection = RandomUtils.random.nextBoolean();
        direction = Direction.values()[RandomUtils.random.nextInt(2)];
        sameColor = RandomUtils.random.nextBoolean();
        color = getRandomPaintColor();
        sameWeight = RandomUtils.random.nextBoolean();
        strokeWeight = RandomUtils.getRandomFloatInRange(MIN_WIDTH, MAX_WIDTH);
        count = RandomUtils.getRandomIntInRange(MIN_COUNT, MAX_COUNT);
    }

    @Override
    public void paint(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Paint paint = getPaint();
        for (int i = 0; i < count; i++) {
            if (!sameColor) {
                setRandomPaintColor(paint);
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
