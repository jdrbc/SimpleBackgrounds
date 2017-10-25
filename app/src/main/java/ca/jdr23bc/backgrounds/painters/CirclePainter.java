package ca.jdr23bc.backgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import ca.jdr23bc.backgrounds.shapes.Circle;

public class CirclePainter extends ShapePainter<Circle> {
    private static final String TAG = CirclePainter.class.getCanonicalName();

    private Boolean drawn;
    private Circle circle;

    public void init(Circle circle) {
        this.circle = circle;
        drawn = false;
    }

    @Override
    public Boolean hasNextPaintStep() {
        return !drawn;
    }

    @Override
    public void paintStep(Canvas canvas) {
        PointF center = circle.getCenter();
        Paint paint = newPaint();
        paint.setColor(getRandomPaintColor());
        Log.d(TAG, "painting color: " + paint.getColor());
        Log.d(TAG, "painting circle: " + circle.toString());
        canvas.drawCircle(center.x, center.y, circle.getRadius(), paint);
    }
}
