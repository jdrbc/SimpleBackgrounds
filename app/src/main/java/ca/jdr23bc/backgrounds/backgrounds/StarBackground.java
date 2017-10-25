package ca.jdr23bc.backgrounds.backgrounds;


import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.painters.ShapePainter;
import ca.jdr23bc.backgrounds.painters.StarPainter;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;
import ca.jdr23bc.backgrounds.shapes.StarFactory;

public class StarBackground extends ShapeBackground {
    StarPainter painter;
    StarFactory factory;

    public StarBackground(int width, int height, Layout layout) {
        super(width, height);
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
