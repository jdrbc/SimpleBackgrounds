package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.Canvas;
import android.util.Log;

import ca.jdr23bc.simplebackgrounds.shapes.Grid;
import ca.jdr23bc.simplebackgrounds.shapes.Shape;
import ca.jdr23bc.simplebackgrounds.shapes.ShapeFactory;

public class GridPainter extends Painter {
    private static final String TAG = GridPainter.class.getCanonicalName();

    Grid grid;
    ShapePainter painter;
    ShapeFactory shapeFactory;

    public GridPainter(Grid grid, ShapePainter painter, ShapeFactory shapeFactory) {
        this.grid = grid;
        this.painter = painter;
        this.shapeFactory = shapeFactory;
    }

    @Override
    public void paint(Canvas canvas) {
        while(grid.hasNext()) {
            Grid.Cell cell = grid.next();
            Shape nextShape = shapeFactory.build(cell.getTopLeft(), cell.getBottomRight());
            Log.d(TAG, "painting cell " + cell.toString());
            painter.withShape(nextShape).paint(canvas);
        }
    }
}
