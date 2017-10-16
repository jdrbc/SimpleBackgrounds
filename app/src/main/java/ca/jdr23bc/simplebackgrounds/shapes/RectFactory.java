package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

public class RectFactory extends ShapeFactory {

    @Override
    public Rect build(PointF topLeft, PointF bottomRight) {
        return new Rect(topLeft, bottomRight);
    }
}
