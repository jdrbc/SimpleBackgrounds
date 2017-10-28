package ca.jdr23bc.backgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;


import ca.jdr23bc.backgrounds.colors.ColorScheme;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public abstract class ShapePainter<Shape> {
    private static final String TAG = ShapePainter.class.getCanonicalName();

    private ColorScheme colorScheme;
    private Paint paint;

    public ShapePainter() {
        this.paint = newPaint();
        colorScheme = new ColorScheme(RandomUtils.getRandomColor());
    }

    public abstract void init(Shape shape);

    public abstract Boolean hasNextPaintStep();

    public abstract void paintStep(Canvas canvas);

    public void fillBackground(Canvas canvas) {
        Paint paint = newPaint();
        paint.setStyle(Paint.Style.FILL);
        int background = colorScheme.popRandom();
        Log.d(TAG, "Background color: " + background);
        paint.setColor(background);
        canvas.drawPaint(paint);
    }

    @Override
    public String toString() {
        return "{ colorScheme: " + colorScheme.toString() + " }";
    }

    protected Paint getPaint() {
        return paint;
    }

    protected int getRandomPaintColor() {
        return colorScheme.getRandom();
    }

    protected int popRandomPaintColor() {
        return colorScheme.popRandom();
    }

    protected Paint newPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        return paint;
    }
}
