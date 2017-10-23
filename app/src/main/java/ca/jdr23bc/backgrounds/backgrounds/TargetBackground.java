package ca.jdr23bc.backgrounds.backgrounds;


import ca.jdr23bc.backgrounds.layout.GridLayout;
import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.layout.SingleCellLayout;
import ca.jdr23bc.backgrounds.painters.ShapePainter;
import ca.jdr23bc.backgrounds.painters.TargetPainter;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;
import ca.jdr23bc.backgrounds.shapes.Target;
import ca.jdr23bc.backgrounds.shapes.TargetFactory;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class TargetBackground extends ShapeBackground {
    TargetFactory factory;
    TargetPainter painter;

    public TargetBackground(Layout layout) {
        factory = new TargetFactory(layout);
        painter = new TargetPainter();
        if (layout instanceof SingleCellLayout) {
            singleCellSetup();
        } else if (layout instanceof GridLayout) {
            gridSetup();
        }
    }

    @Override
    protected ShapeFactory getFactory() {
        return factory;
    }

    @Override
    protected ShapePainter getPainter() {
        return painter;
    }

    private void singleCellSetup() {
        factory.withRandomCenter(RandomUtils.random.nextBoolean())
                .withRadiusMultiplier(3);
    }

    private void gridSetup() {
        Boolean constantRingCount = RandomUtils.random.nextBoolean();
        Boolean squareCells = RandomUtils.random.nextBoolean();
        Boolean cellOverlap = squareCells && constantRingCount && RandomUtils.random.nextBoolean();
        if (constantRingCount) {
            factory.withRingCount(RandomUtils.getRandomIntInRange(
                    Target.MIN_RINGS_FOR_RANDOM_INIT_VALUE, Target.MAX_RINGS_FOR_RANDOM_INIT_VALUE));
        }
        ((GridLayout) factory.getLayout()).withSquareCellsActive(squareCells)
                .withCellOverlapActive(cellOverlap);
    }
}
