package ca.jdr23bc.simplebackgrounds.Shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.Colors.Colour;

public class Circle extends Pattern {
    float radius;
    PointF pos;
    boolean target = false;
    int numInnerCircles;
    float innerCircleStep;
    float innerCircleValueStep;
    int primaryColor;

    public Circle(float radius, PointF pos, Pattern parentPattern) {
        super(parentPattern);
        this.radius = radius;
        this.pos = pos;
    }

    public void draw(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        int color = primaryColor;
        paint.setColor(color);
        canvas.drawCircle(pos.x, pos.y, radius, paint);
        if (target) {
            float multiDotRad = radius * innerCircleStep;
            for (int i = 0; i < numInnerCircles; i++) {
                if (i % 2 == 0) {
                    paint.setColor(backgroundColor);
                } else {
                    color = Colour.darken(color, innerCircleValueStep);
                    paint.setColor(color);
                }
                canvas.drawCircle(pos.x, pos.y, multiDotRad, paint);
                multiDotRad = multiDotRad * innerCircleStep;
            }
        }
    }

    public String toString() {
        return "radius: " + radius + ", pos: " + pos;
    }
}
