package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Pair;

import ca.jdr23bc.simplebackgrounds.shapes.Shape;
import ca.jdr23bc.simplebackgrounds.shapes.Star;

public abstract class ShapePainter extends Painter {

    private Shape shape;

    public ShapePainter() { super(); }

    public ShapePainter(Shape shape) {
        this.shape = shape;
    }

    protected Shape getShape() {
        return shape;
    }

    public ShapePainter withShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    @Override
    public abstract void paint(Canvas canvas);
}
