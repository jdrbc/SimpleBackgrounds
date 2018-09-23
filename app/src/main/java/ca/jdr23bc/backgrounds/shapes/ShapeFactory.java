package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;

import java.util.Iterator;

import ca.jdr23bc.backgrounds.layout.Cell;
import ca.jdr23bc.backgrounds.layout.Layout;

public abstract class ShapeFactory implements Iterator<Shape> {
    private Layout layout;

    public ShapeFactory(Layout layout) {
        this.layout = layout;
    }

    public Layout getLayout() {
        return layout;
    }

    /**
     * @return The number of shapes this factory will produce
     */
    public Integer getTotalNumberOfShapes() {
        return layout.getNumberOfCells();
    }

    public void init(PointF topLeft, PointF bottomRight) {
        layout.init(topLeft, bottomRight);
    }

    public boolean hasNext() {
        return layout.hasNext();
    }

    public Shape next() {
        Cell cell = layout.next();
        return build(cell.getTopLeft(), cell.getBottomRight());
    }

    public abstract Shape build(PointF topLeft, PointF bottomRight);

    @Override
    public String toString() {
        return "{ layout: " + layout.toString() + " }";
    }
}
