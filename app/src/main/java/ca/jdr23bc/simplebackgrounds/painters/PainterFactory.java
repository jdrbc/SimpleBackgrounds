package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.shapes.CircleFactory;
import ca.jdr23bc.simplebackgrounds.shapes.Grid;
import ca.jdr23bc.simplebackgrounds.shapes.RectFactory;
import ca.jdr23bc.simplebackgrounds.shapes.Star;
import ca.jdr23bc.simplebackgrounds.shapes.StarFactory;
import ca.jdr23bc.simplebackgrounds.shapes.Target;
import ca.jdr23bc.simplebackgrounds.shapes.TargetFactory;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;


public class PainterFactory {
    public static final int NUM_PATTERNS = 6;

    public Painter getRandomPainter(PointF topLeft, PointF bottomRight) {
        int randomInt = RandomUtils.random.nextInt(NUM_PATTERNS + 1);
//        randomInt = 3;
        switch(randomInt) {
            case 0:
                return getStarPainter(topLeft, bottomRight);
            case 1:
                return getGridStarPainter(topLeft, bottomRight);
            case 2:
                return getGridCirclePainter(topLeft, bottomRight);
            case 3:
                return getGridRectPainter(topLeft, bottomRight);
            case 4:
                return getLinesPainter();
            case 5:
                return getTargetPainter(topLeft, bottomRight);
            case 6:
                return getGridTargetPainter(topLeft, bottomRight);
            default:
                return getStarPainter(topLeft, bottomRight);

        }
    }

    public Painter getStarPainter(PointF topLeft, PointF bottomRight) {
        return new StarPainter(
                new Star(topLeft, bottomRight)
                        .withIsCentered(true)
        );
    }

    public Painter getGridStarPainter(PointF topLeft, PointF bottomRight) {
        StarFactory starFactory = new StarFactory();
        StarPainter starPainter = new StarPainter();
        return new GridPainter(
                new Grid(topLeft, bottomRight),
                starPainter,
                starFactory
        );
    }

    public Painter getGridCirclePainter(PointF topLeft, PointF bottomRight) {
        CircleFactory factory = new CircleFactory();
        CirclePainter painter = new CirclePainter();
        return new GridPainter(
                new Grid(topLeft, bottomRight),
                painter,
                factory
        );
    }

    public Painter getGridRectPainter(PointF topLeft, PointF bottomRight) {
        Boolean rowSkewActive = RandomUtils.random.nextBoolean();
        Boolean randomRowSkewActive = rowSkewActive && RandomUtils.random.nextBoolean();
        RectFactory factory = new RectFactory();
        RectPainter painter = new RectPainter();
        return new GridPainter(
                new Grid(topLeft, bottomRight)
                        .withSquareCells()
                        .withRowSkewActive(rowSkewActive)
                        .withRandomRowSkewActive(randomRowSkewActive),
                painter,
                factory
        );
    }

    public Painter getLinesPainter() {
        return new LinesPainter();
    }

    public Painter getTargetPainter(PointF topLeft, PointF bottomRight) {
        PointF randomCenterPoint = RandomUtils.getRandomPointInRect(topLeft, bottomRight);
        Target target = new Target(topLeft, bottomRight);
        return new TargetPainter(target.withCenter(randomCenterPoint).withRadius(target.getRadius() * 2));
    }

    public Painter getGridTargetPainter(PointF topLeft, PointF bottomRight) {
        Boolean constantRingCount = RandomUtils.random.nextBoolean();
        Boolean squareCells = RandomUtils.random.nextBoolean();
        Boolean cellOverlap = squareCells && constantRingCount && RandomUtils.random.nextBoolean();

        TargetFactory factory = new TargetFactory();
        if (constantRingCount) {
            factory.withRingCount(RandomUtils.getRandomIntInRange(
                    Target.MIN_RINGS_FOR_RANDOM_INIT_VALUE, Target.MAX_RINGS_FOR_RANDOM_INIT_VALUE));
        }
        TargetPainter painter = new TargetPainter();
        Grid grid = new Grid(topLeft, bottomRight)
                .withSquareCellsActive(squareCells)
                .withCellOverlapActive(cellOverlap);
        return new GridPainter(
                grid,
                painter,
                factory
        );
    }
}
