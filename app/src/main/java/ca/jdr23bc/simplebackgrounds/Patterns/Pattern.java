package ca.jdr23bc.simplebackgrounds.Patterns;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import ca.jdr23bc.simplebackgrounds.ColorScheme;
import ca.jdr23bc.simplebackgrounds.OpenSimplexNoise;

public abstract class Pattern {
    public ColorScheme colorScheme;
    public int rootColor;
    public Integer backgroundColor;
    public Random random;
    public OpenSimplexNoise osn;
    public Paint paint;
    public int width;
    public int height;

    public abstract String toString();
    public abstract void draw(Canvas canvas);

    public Pattern(Pattern parentPattern) {
        this.random = parentPattern.random;
        this.paint = parentPattern.paint;
        this.rootColor = parentPattern.rootColor;
        this.colorScheme = parentPattern.colorScheme;
        this.backgroundColor = parentPattern.backgroundColor;
        this.osn = parentPattern.osn;
    }

    public Pattern(int width, int height) {
        this.width = width;
        this.height = height;
        this.random = new Random();
        this.osn = new OpenSimplexNoise(random.nextLong());
        this.paint = new Paint();
        paint.setAntiAlias(true);
        this.rootColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        this.colorScheme = new ColorScheme(rootColor);
    }

    public void fillAndDraw(Canvas canvas) {
        fillBackground(canvas);
        draw(canvas);
    }

    public void fillBackground(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        if (backgroundColor == null) {
            backgroundColor = colorScheme.popRandom();
        }
        paint.setColor(backgroundColor);
        canvas.drawPaint(paint);
    }

    public float nextFloatInRange(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public int nextIntInRange(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public int nextIntInRangeSequenced(int min, int max, double x, double y) {
        double rand = Math.abs(osn.eval(x, y));
        return (int) (rand * (max - min)) + min;
    }
}
