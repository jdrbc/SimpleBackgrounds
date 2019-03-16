package ca.jdr23bc.backgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import ca.jdr23bc.backgrounds.shapes.Target;

public class TargetPainter extends ShapePainter<Target> {
    private static final String TAG = "TargetPainter";

    private Target target;

    public TargetPainter() {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getRandomPaintColor());
    }

    public void init(Target target) {
        this.target = target;
        getPaint().setStrokeWidth(target.getRingWidth());
        target.init();
    }

    public Boolean hasNextPaintStep() {
        return target.hasNext();
    }

    public void paintStep(Canvas canvas) {
        Target.TargetRing ring = target.next();
        if (ring.getRingNumber() % 2 == 0) {
            // skip inner ring (void in target)
            return;
        }
        PointF center = ring.getCenter();
        if (target.hasNext()) {
            // paint ring
            canvas.drawCircle(center.x, center.y, ring.getRadius(), getPaint());
        } else {
            // paint center
            getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawCircle(center.x, center.y, ring.getRadius(), getPaint());
            getPaint().setStyle(Paint.Style.STROKE);
        }
    }
}
