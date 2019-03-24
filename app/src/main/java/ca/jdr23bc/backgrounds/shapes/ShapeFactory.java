package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;
import android.support.annotation.NonNull;

import java.util.Iterator;

import ca.jdr23bc.backgrounds.layout.Cell;
import ca.jdr23bc.backgrounds.layout.Layout;

public abstract class ShapeFactory implements Iterator<Shape> {
    private final Layout layout;

    protected ShapeFactory(Layout layout) {
        this.layout = layout;
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

    protected abstract Shape build(PointF topLeft, PointF bottomRight);

    @NonNull
    @Override
    public String toString() {
        return "{ layout: " + layout.toString() + " }";
    }
}
