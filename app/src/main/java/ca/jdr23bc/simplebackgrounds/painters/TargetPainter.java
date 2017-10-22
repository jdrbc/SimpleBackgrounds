package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.shapes.Target;

public class TargetPainter extends ShapePainter {

    public TargetPainter() {
        super();
    }

    public TargetPainter(Target target) {
        super(target);
    }

    @Override
    public void paint(Canvas canvas) {
        Target target = (Target) getShape();
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(target.getRingWidth());
        setRandomPaintColor(paint);

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
