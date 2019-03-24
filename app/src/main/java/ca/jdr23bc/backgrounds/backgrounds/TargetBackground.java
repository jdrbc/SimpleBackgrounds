package ca.jdr23bc.backgrounds.backgrounds;


import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.painters.ShapePainter;
import ca.jdr23bc.backgrounds.painters.TargetPainter;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;
import ca.jdr23bc.backgrounds.shapes.Target;
import ca.jdr23bc.backgrounds.shapes.TargetFactory;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class TargetBackground extends ShapeBackground {
    private final TargetFactory factory;
    private final TargetPainter painter;

    TargetBackground(int width, int height, Layout layout) {
        super(width, height);
        factory = new TargetFactory(layout)
                .withRingCount(RandomUtils.getRandomIntInRange(Target.MIN_RINGS_FOR_RANDOM_INIT_VALUE, Target.MAX_RINGS_FOR_RANDOM_INIT_VALUE));
        painter = new TargetPainter();
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
