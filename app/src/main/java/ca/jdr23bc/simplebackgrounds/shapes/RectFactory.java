package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;
import android.graphics.RectF;

import ca.jdr23bc.simplebackgrounds.layout.Layout;

public class RectFactory extends ShapeFactory {

    public RectFactory(Layout layout) {
        super(layout);
    }

    @Override
    public Rect build(PointF topLeft, PointF bottomRight) {
        return new Rect(topLeft, bottomRight);
    }
}
