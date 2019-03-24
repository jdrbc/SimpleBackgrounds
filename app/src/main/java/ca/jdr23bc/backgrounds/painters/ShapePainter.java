package ca.jdr23bc.backgrounds.painters;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import ca.jdr23bc.backgrounds.colors.ColorScheme;
import ca.jdr23bc.backgrounds.colors.Colour;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public abstract class ShapePainter<Shape> {
    private static final String TAG = ShapePainter.class.getCanonicalName();

    private ColorScheme colorScheme;
    private final Paint paint;
    private ColorFilter filter;
    private Integer backgroundColor;

    ShapePainter() {
        this.paint = newPaint();
        colorScheme = new ColorScheme(RandomUtils.getRandomColor());
    }

    public abstract void init(Shape shape);

    public abstract Boolean hasNextPaintStep();

    public abstract void paintStep(Canvas canvas);

    public void fillBackground(Canvas canvas) {
        Paint paint = newPaint();
        paint.setStyle(Paint.Style.FILL);
        Log.d(TAG, "Background color: " + getBackgroundColor());
        paint.setColor(getBackgroundColor());
        canvas.drawPaint(paint);
    }

    public void setColorFilter(ColorFilter filter) {
        this.filter = filter;
        paint.setColorFilter(filter);
    }

    @NonNull
    @Override
    public String toString() {
        return "{ colorScheme: " + colorScheme.toString() + " }";
    }

    Paint getPaint() {
        return paint;
    }

    void setColorScheme(int rootColor) {
        colorScheme = new ColorScheme(rootColor, Colour.ColorScheme.ColorSchemeMonochromatic);
    }

    @SuppressWarnings("unused")
    protected int getRootColor() {
        return colorScheme.getRootColor();
    }

    int getRandomPaintColor() {
        return colorScheme.getRandom();
    }

    private int popRandomPaintColor() {
        return colorScheme.popRandom();
    }

    int popDarkestPaintColor() {
        if (Build.VERSION.SDK_INT >= 24) {
            return colorScheme.popDarkest();
        } else {
            return colorScheme.popRandom();
        }
    }

    int popLightestPaintColor() {
        if (Build.VERSION.SDK_INT >= 24) {
            return colorScheme.popLightest();
        } else {
            return colorScheme.popRandom();
        }
    }

    Paint newPaint() {
        Paint paint = new Paint();
        paint.setColorFilter(filter);
        paint.setAntiAlias(true);
        return paint;
    }

    void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    private int getBackgroundColor() {
        if (backgroundColor == null) {
            backgroundColor = popRandomPaintColor();
        }
        return backgroundColor;
    }
}
