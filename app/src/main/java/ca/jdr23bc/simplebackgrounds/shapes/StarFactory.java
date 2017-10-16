package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

public class StarFactory extends ShapeFactory {

    public Star build(PointF topLeft, PointF bottomRight) {
        return new Star(topLeft, bottomRight).withIsCentered(true);
    }
}
