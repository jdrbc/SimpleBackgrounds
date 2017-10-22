package ca.jdr23bc.simplebackgrounds.backgrounds;

import ca.jdr23bc.simplebackgrounds.layout.Layout;
import ca.jdr23bc.simplebackgrounds.painters.CirclePainter;
import ca.jdr23bc.simplebackgrounds.painters.ShapePainter;
import ca.jdr23bc.simplebackgrounds.shapes.CircleFactory;
import ca.jdr23bc.simplebackgrounds.shapes.ShapeFactory;

public class CircleBackground extends ShapeBackground {
    CirclePainter painter;
    CircleFactory factory;

    public CircleBackground(Layout layout) {
        factory = new CircleFactory(layout);
        painter = new CirclePainter();
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
