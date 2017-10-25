package ca.jdr23bc.backgrounds.painters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.util.Pair;

import ca.jdr23bc.backgrounds.shapes.Star;

public class StarPainter extends ShapePainter<Star> {
    private static final String TAG = StarPainter.class.getCanonicalName();
    private static final int STAR_STROKE_WIDTH = 3;

    private Star star;
    private Boolean firstStep = false;

    public void init(Star star) {
        this.star = star;
        Paint paint = getPaint();
        paint.setColor(getRandomPaintColor());
        paint.setStrokeWidth(STAR_STROKE_WIDTH);
        star.init();
        Log.d(TAG, "painting star " + star.toString());
        firstStep = true;
    }

    public Boolean hasNextPaintStep() {
        return star.hasNext();
    }

    public void paintStep(Canvas canvas) {
        if (firstStep) {
            PointF startingPoint = star.getStartingPoint();
            canvas.drawPoint(startingPoint.x, startingPoint.y, getPaint());
            firstStep = false;
        }
        Pair<PointF, PointF> next = star.next();
        canvas.drawLine(next.first.x, next.first.y, next.second.x, next.second.y, getPaint());
    }
}
