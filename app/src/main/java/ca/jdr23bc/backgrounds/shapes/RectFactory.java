package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;

import ca.jdr23bc.backgrounds.layout.Layout;

public class RectFactory extends ShapeFactory {

    public RectFactory(Layout layout) {
        super(layout);
    }

    @Override
    public Rect build(PointF topLeft, PointF bottomRight) {
        return new Rect(topLeft, bottomRight);
    }
}
