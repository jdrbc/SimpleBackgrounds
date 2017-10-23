package ca.jdr23bc.backgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import ca.jdr23bc.backgrounds.shapes.Circle;

public class CirclePainter extends ShapePainter<Circle> {
    private static final String TAG = CirclePainter.class.getCanonicalName();

    @Override
    public void paint(Canvas canvas, Circle circle) {
        PointF center = circle.getCenter();
        Paint paint = getPaint();
        paint.setColor(getRandomPaintColor());
        Log.d(TAG, "painting color: " + paint.getColor());
        Log.d(TAG, "painting circle: " + circle.toString());
        canvas.drawCircle(center.x, center.y, circle.getRadius(), paint);
    }
}
