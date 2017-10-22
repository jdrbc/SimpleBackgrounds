package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.layout.Layout;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class StarFactory extends ShapeFactory {

    public StarFactory(Layout layout) {
        super(layout);
    }

    public Star build(PointF topLeft, PointF bottomRight) {
        return new Star(topLeft, bottomRight)
                .withIsCentered(RandomUtils.random.nextBoolean());
    }
}
