package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.layout.Layout;

public class CircleFactory extends ShapeFactory {

    public CircleFactory(Layout layout) {
        super(layout);
    }
    @Override
    public Shape build(PointF topLeft, PointF bottomRight) {
        return new Circle(topLeft, bottomRight);
    }
}
