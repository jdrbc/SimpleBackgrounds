package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import ca.jdr23bc.simplebackgrounds.shapes.Circle;
import ca.jdr23bc.simplebackgrounds.shapes.Shape;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class CirclePainter extends ShapePainter<Circle> {
    private static final String TAG = CirclePainter.class.getCanonicalName();

    Boolean paintBlack;

    public CirclePainter() {
        paintBlack = RandomUtils.random.nextBoolean();
    }

    @Override
    public void paint(Canvas canvas, Circle circle) {
        PointF center = circle.getCenter();
        Paint paint = getPaint();
        if (paintBlack) {
            paint.setColor(Color.BLACK);
        } else {
            paint.setColor(getRandomPaintColor());
        }
        Log.d(TAG, "painting color: " + paint.getColor());
        Log.d(TAG, "painting circle: " + circle.toString());

        canvas.drawCircle(center.x, center.y, circle.getRadius(), paint);
    }
}
