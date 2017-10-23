package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;

import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class StarFactory extends ShapeFactory {

    public StarFactory(Layout layout) {
        super(layout);
    }

    public Star build(PointF topLeft, PointF bottomRight) {
        return new Star(topLeft, bottomRight)
                .withIsCentered(RandomUtils.random.nextBoolean());
    }
}
