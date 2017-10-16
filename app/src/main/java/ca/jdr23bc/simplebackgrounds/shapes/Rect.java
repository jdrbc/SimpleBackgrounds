package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;
import android.graphics.RectF;

public class Rect extends Shape {
    public Rect(PointF topLeft, PointF bottomRight) {
        super(topLeft, bottomRight);
    }

    public RectF getRectF() {
        PointF topLeft = getTopLeft();
        PointF bottomRight = getBottomRight();
        return new RectF(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
    }
}
