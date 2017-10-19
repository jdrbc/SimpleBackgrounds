package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.shapes.Target;

public class TargetPainter extends ShapePainter {

    public TargetPainter(Target target) {
        super(target);
    }

    @Override
    public void paint(Canvas canvas) {
        Target target = (Target) getShape();
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        setRandomPaintColor();

        target.init();
        while(target.hasNext()) {
            Target.TargetRing ring = target.next();
            PointF center = ring.getCenter();
            canvas.drawCircle(center.x, center.y, ring.getRadius(), paint);
        }
    }
}
