package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;

import ca.jdr23bc.backgrounds.layout.Layout;

public class TargetFactory extends ShapeFactory {

    private int ringCount = 3;

    public TargetFactory(Layout layout) {
        super(layout);
    }

    @SuppressWarnings("UnusedReturnValue")
    public TargetFactory withRingCount(int ringCount) {
        this.ringCount = ringCount;
        return this;
    }

    @Override
    public Shape build(PointF topLeft, PointF bottomRight) {
        return new Target(topLeft, bottomRight)
                .withRingCount(ringCount);
    }
}
