package ca.jdr23bc.backgrounds.backgrounds;

import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.painters.CirclePainter;
import ca.jdr23bc.backgrounds.painters.ShapePainter;
import ca.jdr23bc.backgrounds.shapes.CircleFactory;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;

public class CircleBackground extends ShapeBackground {
    private final CirclePainter painter;
    private final CircleFactory factory;

    public CircleBackground(int width, int height, Layout layout) {
        super(width, height);
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
