package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

public abstract class ShapeFactory {

    public ShapeFactory() {}

    public abstract Shape build(PointF topLeft, PointF bottomRight);
}
