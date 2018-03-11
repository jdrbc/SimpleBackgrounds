package ca.jdr23bc.backgrounds.backgrounds;


import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.painters.ShapePainter;
import ca.jdr23bc.backgrounds.painters.TreePainter;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;
import ca.jdr23bc.backgrounds.shapes.tree.TreeFactory;

public class TreeBackground extends ShapeBackground {

    private TreeFactory factory;
    private TreePainter painter;

    TreeBackground(int width, int height, Layout layout) {
        super(width, height);
        factory = new TreeFactory(layout);
        painter = new TreePainter();
    }

    @Override
    public void freeMemory() {
        factory = null;
        painter = null;
        super.freeMemory();
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
