package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;

import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class TargetFactory extends ShapeFactory {

    int ringCount;
    float raduisMultiplier;
    boolean raduisMultiplierActive;
    boolean ringCountSet;
    boolean randomCenter;

    public TargetFactory(Layout layout) {
        super(layout);
        raduisMultiplierActive = false;
    }

    public TargetFactory withRandomCenter(Boolean randomCenter) {
        this.randomCenter = randomCenter;
        return this;
    }

    public TargetFactory withRadiusMultiplier(float multiplier) {
        this.raduisMultiplier = multiplier;
        raduisMultiplierActive = true;
        return this;
    }

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
        if (randomCenter) {
            target.withCenter(RandomUtils.getRandomPointInRect(topLeft, bottomRight));
        }
        if (raduisMultiplierActive) {
            target.withRadius(target.getRadius() * raduisMultiplier);
        }
        return target;
    }
}
