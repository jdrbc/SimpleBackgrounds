package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.shapes.Circle;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class CirclePainter extends ShapePainter {

    Boolean paintBlack;

    public CirclePainter() {
        paintBlack = RandomUtils.random.nextBoolean();
    }

    @Override
    public void paint(Canvas canvas) {
        Circle circle = (Circle) getShape();
        PointF center = circle.getCenter();
        Paint paint = getPaint();
        if (paintBlack) {
            paint.setColor(0);
        } else {
            setRandomPaintColor(paint);
        }
        canvas.drawCircle(center.x, center.y, circle.getRadius(), paint);
    }
}
