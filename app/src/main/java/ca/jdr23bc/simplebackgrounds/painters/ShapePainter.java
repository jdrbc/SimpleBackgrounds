package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.util.Pair;

import java.util.Random;

import ca.jdr23bc.simplebackgrounds.colors.ColorScheme;
import ca.jdr23bc.simplebackgrounds.shapes.Shape;
import ca.jdr23bc.simplebackgrounds.shapes.Star;

public abstract class ShapePainter<Shape> {
    private static final String TAG = ShapePainter.class.getCanonicalName();

    private ColorScheme colorScheme;
    private int rootColor;
    private Random random;

    public ShapePainter() {
        this.random = new Random();
        this.rootColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        this.colorScheme = new ColorScheme(rootColor);
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
