package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.shapes.Target;

public class TargetPainter extends ShapePainter<Target> {

    public TargetPainter() {
        super();
    }

    @Override
    public void paint(Canvas canvas, Target target) {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(target.getRingWidth());
        paint.setColor(getRandomPaintColor());

        target.init();
        while(target.hasNext()) {
            Target.TargetRing ring = target.next();
            if (ring.getRingNumber() % 2 == 0) {
                continue;
            }
            PointF center = ring.getCenter();
            if (target.hasNext()) {
                canvas.drawCircle(center.x, center.y, ring.getRadius(), paint);
            } else {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle(center.x, center.y, ring.getRadius(), paint);
            }
        }
    }
}
