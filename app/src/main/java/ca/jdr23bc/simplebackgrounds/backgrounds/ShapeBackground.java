package ca.jdr23bc.simplebackgrounds.backgrounds;


import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;

import ca.jdr23bc.simplebackgrounds.painters.ShapePainter;
import ca.jdr23bc.simplebackgrounds.shapes.Shape;
import ca.jdr23bc.simplebackgrounds.shapes.ShapeFactory;

public abstract class ShapeBackground implements Background {
    private static final String TAG = ShapeBackground.class.getCanonicalName();

    @Override
    public void fill(Canvas canvas) {
        ShapeFactory factory = getFactory();
        ShapePainter painter = getPainter();
        painter.fillBackground(canvas);
        factory.init(new PointF(0, 0), new PointF(canvas.getWidth(), canvas.getHeight()));
        while(factory.hasNext()) {
            Shape shape = factory.next();
            Log.d(TAG, "Painting shape: " + shape.toString());
            painter.paint(canvas, shape);
        }
    }

    public String toString() {
        return "{ name: " + this.getClass().getSimpleName() + ", " +
                "factory: " + getFactory().toString() + ", " +
                "painter: " + getPainter().toString() + " }";
    }

    protected abstract ShapeFactory getFactory();
    protected abstract ShapePainter getPainter();
}
