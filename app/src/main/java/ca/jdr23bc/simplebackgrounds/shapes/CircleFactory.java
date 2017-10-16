package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

public class CircleFactory extends ShapeFactory {
    @Override
    public Shape build(PointF topLeft, PointF bottomRight) {
        return new Circle(topLeft, bottomRight);
    }
}
