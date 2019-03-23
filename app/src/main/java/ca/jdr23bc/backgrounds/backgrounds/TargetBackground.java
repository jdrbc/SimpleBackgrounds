package ca.jdr23bc.backgrounds.backgrounds;


import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.painters.ShapePainter;
import ca.jdr23bc.backgrounds.painters.TargetPainter;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;
import ca.jdr23bc.backgrounds.shapes.Target;
import ca.jdr23bc.backgrounds.shapes.TargetFactory;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class TargetBackground extends ShapeBackground {
    TargetFactory factory;
    TargetPainter painter;

    public TargetBackground(int width, int height, Layout layout) {
        super(width, height);
        factory = new TargetFactory(layout);
        painter = new TargetPainter();
        Boolean squareCells = RandomUtils.random.nextBoolean();
        Boolean cellOverlap = squareCells && RandomUtils.random.nextBoolean();
        factory.withRingCount(RandomUtils.getRandomIntInRange(
                Target.MIN_RINGS_FOR_RANDOM_INIT_VALUE, Target.MAX_RINGS_FOR_RANDOM_INIT_VALUE));
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
