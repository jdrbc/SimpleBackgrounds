package ca.jdr23bc.simplebackgrounds.painters;

import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.shapes.CircleFactory;
import ca.jdr23bc.simplebackgrounds.shapes.Grid;
import ca.jdr23bc.simplebackgrounds.shapes.RectFactory;
import ca.jdr23bc.simplebackgrounds.shapes.Star;
import ca.jdr23bc.simplebackgrounds.shapes.StarFactory;
import ca.jdr23bc.simplebackgrounds.shapes.Target;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class PainterFactory {

    public Painter getRandomPainter(PointF topLeft, PointF bottomRight) {
//        int randomInt = new Random().nextInt(2);
        int randomInt = 5;
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
        RectFactory factory = new RectFactory();
        RectPainter painter = new RectPainter();
        return new GridPainter(
                new Grid(topLeft, bottomRight)
                        .withSquareCells()
                        .withRandomShuffleRowsSetting(),
                painter,
                factory
        );
    }

    public Painter getLinesPainter() {
        return new LinesPainter();
    }

    public Painter getTargetPainter(PointF topLeft, PointF bottomRight) {
        Target target = new Target(topLeft, bottomRight);
        return new TargetPainter(target.withRingCount(9));
    }
}
