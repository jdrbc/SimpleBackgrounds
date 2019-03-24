package ca.jdr23bc.backgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ca.jdr23bc.backgrounds.shapes.Rect;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class RectPainter extends ShapePainter<Rect> {
    private final int MAX_BORDER_WIDTH = 30;
    private final int MIN_BORDER_WIDTH = 1;

    private Rect rect;
    private Paint borderPaint;
    private Boolean drawn;

    public RectPainter() {
        Boolean drawBorder = RandomUtils.random.nextBoolean();
        if (drawBorder) {
            Boolean blackBorder = RandomUtils.random.nextBoolean();
            borderPaint = newPaint();
            borderPaint.setStrokeWidth(RandomUtils.getRandomFloatInRange(MIN_BORDER_WIDTH, MAX_BORDER_WIDTH));
            borderPaint.setStyle(Paint.Style.STROKE);
            if (blackBorder) {
                borderPaint.setColor(Color.BLACK);
            } else {
                borderPaint.setColor(getRandomPaintColor());
            }
        }
    }

    public void init(Rect rect) {
        this.rect = rect;
        drawn = false;
    }

    public Boolean hasNextPaintStep() {
        return !drawn;
    }

    public void paintStep(Canvas canvas) {
        Paint paint = getPaint();
        paint.setColor(getRandomPaintColor());
        canvas.drawRect(rect.getRectF(), paint);
        if (borderPaint != null) {
            canvas.drawRect(rect.getRectF(), borderPaint);
        }
        drawn = true;
    }
}
