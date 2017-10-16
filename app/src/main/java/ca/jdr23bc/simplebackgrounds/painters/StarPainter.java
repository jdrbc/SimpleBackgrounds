package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import android.util.Pair;

import ca.jdr23bc.simplebackgrounds.shapes.Star;

public class StarPainter extends ShapePainter {
    private static final String TAG = StarPainter.class.getName();
    private static final int STAR_STROKE_WIDTH = 3;

    public StarPainter() {}

    public StarPainter(Star star) {
        super(star);
    }

    @Override
    public void paint(Canvas canvas) {
        setRandomPaintColor();
        setStrokeWidth(STAR_STROKE_WIDTH);

        Star star = (Star) getShape();
        Log.d(TAG, "painting star " + star.toString());
        PointF point = star.init();
        canvas.drawPoint(point.x, point.y, getPaint());
        while(star.hasNext()) {
            Pair<PointF, PointF> next = star.next();
            Log.d(TAG, "painting line between " + next.first.toString() + " and " + next.second.toString());
            canvas.drawLine(next.first.x, next.first.y, next.second.x, next.second.y, getPaint());
        }
    }
}
