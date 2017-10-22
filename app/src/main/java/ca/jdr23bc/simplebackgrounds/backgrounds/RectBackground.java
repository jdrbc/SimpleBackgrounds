package ca.jdr23bc.simplebackgrounds.backgrounds;


import ca.jdr23bc.simplebackgrounds.layout.GridLayout;
import ca.jdr23bc.simplebackgrounds.layout.Layout;
import ca.jdr23bc.simplebackgrounds.painters.RectPainter;
import ca.jdr23bc.simplebackgrounds.painters.ShapePainter;
import ca.jdr23bc.simplebackgrounds.shapes.RectFactory;
import ca.jdr23bc.simplebackgrounds.shapes.ShapeFactory;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class RectBackground extends ShapeBackground {
    RectFactory factory;
    RectPainter painter;

    public RectBackground(Layout layout) {
        if (layout instanceof GridLayout) {
            ((GridLayout) layout).withSquareCellsActive(true)
                    .withRowSkewActive(RandomUtils.random.nextBoolean())
                    .withShuffledRowsActive(RandomUtils.random.nextBoolean());
        }
        factory = new RectFactory(layout);
        painter = new RectPainter();
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
