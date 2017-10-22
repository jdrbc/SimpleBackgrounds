package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ca.jdr23bc.simplebackgrounds.shapes.Rect;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class RectPainter extends ShapePainter {
    public final int MAX_BORDER_WIDTH = 30;
    public final int MIN_BORDER_WIDTH = 1;
    private Paint borderPaint;

    public RectPainter() {
        super();
        Boolean drawBorder = RandomUtils.random.nextBoolean();
        Boolean blackBorder = RandomUtils.random.nextBoolean();
        if (drawBorder) {
            borderPaint = new Paint();
            borderPaint.setStrokeWidth(RandomUtils.getRandomFloatInRange(MIN_BORDER_WIDTH, MAX_BORDER_WIDTH));
            borderPaint.setStyle(Paint.Style.STROKE);
            if (blackBorder) {
                borderPaint.setColor(Color.BLACK);
            } else {
                borderPaint.setColor(getRandomPaintColor());
            }
        }
    }

    @Override
    public void fillBackgroundAndPaint(Canvas canvas) {
        // No need to fill background
        paint(canvas);
    }

    @Override
    public void paint(Canvas canvas) {
        Rect rect = (Rect) getShape();
        Paint paint = getPaint();
        setRandomPaintColor(paint);
        canvas.drawRect(rect.getRectF(), paint);
        if (borderPaint != null) {
            canvas.drawRect(rect.getRectF(), borderPaint);
        }
    }
}
