package ca.jdr23bc.backgrounds.backgrounds;


import ca.jdr23bc.backgrounds.layout.GridLayout;
import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.painters.RectPainter;
import ca.jdr23bc.backgrounds.painters.ShapePainter;
import ca.jdr23bc.backgrounds.shapes.RectFactory;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class RectBackground extends ShapeBackground {
    private static final String TAG = RectBackground.class.getCanonicalName();
    private final RectFactory factory;
    private final RectPainter painter;

    public RectBackground(int width, int height, Layout layout) {
        super(width, height);
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
