package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

public class TargetFactory extends ShapeFactory {
    @Override
    public Shape build(PointF topLeft, PointF bottomRight) {
        return new Target(topLeft, bottomRight);
    }
}
