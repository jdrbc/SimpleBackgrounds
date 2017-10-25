package ca.jdr23bc.backgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;


import ca.jdr23bc.backgrounds.colors.ColorScheme;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public abstract class ShapePainter<Shape> {
    private static final String TAG = ShapePainter.class.getCanonicalName();

    private ColorScheme colorScheme;

    public ShapePainter() {
        this.colorScheme = new ColorScheme(RandomUtils.getRandomColor());
    }

    public abstract void paint(Canvas canvas, Shape shape);

    public void fillBackground(Canvas canvas) {
        Paint paint = getPaint();
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

    protected int getRandomPaintColor() {
        return colorScheme.getRandom();
    }

    protected Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        return paint;
    }
}
