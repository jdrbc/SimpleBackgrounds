package ca.jdr23bc.simplebackgrounds.Patterns;

import android.graphics.Canvas;
import android.graphics.Point;

public class Lines extends Pattern {

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

    public Lines(int width, int height) {
        super(width, height);
        alpha = nextIntInRange(MIN_ALPHA, MAX_ALPHA);
        sameDirection = random.nextBoolean();
        direction = Direction.values()[random.nextInt(2)];
        sameColor = random.nextBoolean();
        color = colorScheme.getRandom();
        sameWeight = random.nextBoolean();
        strokeWeight = nextFloatInRange(MIN_WIDTH, MAX_WIDTH);
        count = nextIntInRange(MIN_COUNT, MAX_COUNT);
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < count; i++) {
            if (!sameColor) {
                paint.setColor(colorScheme.getRandom());
                paint.setAlpha(alpha);
            } else {
                paint.setColor(color);
                paint.setAlpha(nextIntInRange(MIN_ALPHA, MAX_ALPHA));
            }

            if (!sameDirection) {
                direction = Direction.values()[random.nextInt(2)];
            }

            if (!sameWeight) {
                strokeWeight = nextFloatInRange(MIN_WIDTH, MAX_WIDTH);
            }
            paint.setStrokeWidth(strokeWeight);

            Point start = new Point();
            Point end = new Point();
            if (direction == Direction.VERTICAL) {
                start.y = -500;
                start.x = random.nextInt(width);
                end.y = height + 500;
                end.x = random.nextInt(width);
            } else {
                start.y = random.nextInt(height);
                start.x = -500;
                end.y = random.nextInt(height);
                end.x = width + 500;
            }
            canvas.drawLine(start.x, start.y, end.x, end.y, paint);
        }
    }
}
