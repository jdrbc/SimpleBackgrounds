package ca.jdr23bc.backgrounds.shapes.tree;

import android.graphics.PointF;

import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.shapes.Shape;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;


public class TreeFactory extends ShapeFactory {
    public TreeFactory(Layout layout) {
        super(layout);
    }

    @Override
    public Shape build(PointF topLeft, PointF bottomRight) {
        return new Tree(topLeft, bottomRight);
    }
}
