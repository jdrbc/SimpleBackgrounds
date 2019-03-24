package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;

import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class StarFactory extends ShapeFactory {
    private Boolean isCentered;

    public StarFactory(Layout layout) {
        super(layout);
        isCentered = RandomUtils.random.nextBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    public StarFactory withIsCentered(Boolean isCentered) {
        this.isCentered = isCentered;
        return this;
    }

    public Star build(PointF topLeft, PointF bottomRight) {
        return new Star(topLeft, bottomRight)
                .withIsCentered(isCentered);
    }
}
