package ca.jdr23bc.simplebackgrounds.backgrounds;


import ca.jdr23bc.simplebackgrounds.layout.Layout;
import ca.jdr23bc.simplebackgrounds.painters.ShapePainter;
import ca.jdr23bc.simplebackgrounds.painters.StarPainter;
import ca.jdr23bc.simplebackgrounds.shapes.ShapeFactory;
import ca.jdr23bc.simplebackgrounds.shapes.StarFactory;

public class StarBackground extends ShapeBackground {
    StarPainter painter;
    StarFactory factory;

    public StarBackground(Layout layout) {
        this.painter = new StarPainter();
        this.factory = new StarFactory(layout);
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
