package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.PointF;

public class TargetFactory extends ShapeFactory {

    int ringCount;
    boolean ringCountSet;

    public TargetFactory withRingCount(int ringCount) {
        this.ringCount = ringCount;
        this.ringCountSet = true;
        return this;
    }

    @Override
    public Shape build(PointF topLeft, PointF bottomRight) {
        Target target = new Target(topLeft, bottomRight);
        if (ringCountSet) {
            target.withRingCount(ringCount);
        }
        return target;
    }
}
