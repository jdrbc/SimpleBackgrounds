package ca.jdr23bc.backgrounds.backgrounds;


import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.painters.ShapePainter;
import ca.jdr23bc.backgrounds.painters.TreePainter;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;
import ca.jdr23bc.backgrounds.shapes.tree.TreeFactory;

public class TreeBackground extends ShapeBackground {

    TreeFactory factory;
    TreePainter painter;

    public TreeBackground(int width, int height, Layout layout) {
        super(width, height);
        factory = new TreeFactory(layout);
        painter = new TreePainter();
    }

    @Override
    protected ShapeFactory getFactory() {
        return factory;
    }

    @Override
    protected ShapePainter getPainter() {
        return painter;
    }
}
