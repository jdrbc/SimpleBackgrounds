package ca.jdr23bc.backgrounds.painters;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import ca.jdr23bc.backgrounds.colors.ColorPalette;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public abstract class ShapePainter<Shape> {
    private static final String TAG = ShapePainter.class.getCanonicalName();

    private ColorPalette colorPalette;
    private final Paint paint;
    private ColorFilter filter;
    private Integer backgroundColor;

    ShapePainter() {
        this.paint = newPaint();
        colorPalette = new ColorPalette(RandomUtils.getRandomColor());
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
        return "{ colorPalette: " + colorPalette.toString() + " }";
    }

    Paint getPaint() {
        return paint;
    }

    void setColorPalette(int rootColor) {
        colorPalette = new ColorPalette(rootColor);
    }

    @SuppressWarnings("unused")
    protected int getRootColor() {
        return colorPalette.getRootColor();
    }

    int getRandomPaintColor() {
        return colorPalette.getRandom();
    }

    int popDarkestPaintColor() {
        if (Build.VERSION.SDK_INT >= 24) {
            return colorPalette.popDarkest();
        } else {
            return colorPalette.popRandom();
        }
    }

    int popLightestPaintColor() {
        if (Build.VERSION.SDK_INT >= 24) {
            return colorPalette.popLightest();
        } else {
            return colorPalette.popRandom();
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
            backgroundColor = colorPalette.getRandomBackgroundColor();
        }
        return backgroundColor;
    }
}
