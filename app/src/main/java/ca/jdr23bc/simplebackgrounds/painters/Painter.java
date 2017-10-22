package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import ca.jdr23bc.simplebackgrounds.colors.ColorScheme;

public abstract class Painter {
    private ColorScheme colorScheme;
    private int rootColor;
    private Random random;

    public Painter() {
        this.random = new Random();
        this.rootColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        this.colorScheme = new ColorScheme(rootColor);
    }

    public void fillBackgroundAndPaint(Canvas canvas) {
        fillBackground(canvas);
        paint(canvas);
    }

    public abstract void paint(Canvas canvas);

    private void fillBackground(Canvas canvas) {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorScheme.popRandom());
        canvas.drawPaint(paint);
    }

    protected int getRandomPaintColor() {
        return colorScheme.getRandom();
    }

    protected void setRandomPaintColor(Paint paint) {
        paint.setColor(getRandomPaintColor());
    }

    protected Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        return paint;
    }
}
