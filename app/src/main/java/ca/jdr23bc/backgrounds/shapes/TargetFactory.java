package ca.jdr23bc.backgrounds.shapes;

import android.graphics.PointF;

import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class TargetFactory extends ShapeFactory {

    private int ringCount = 3;
    private float radiusMultiplier;
    private boolean radiusMultiplierActive;
    private boolean randomCenter;

    public TargetFactory(Layout layout) {
        super(layout);
        radiusMultiplierActive = false;
    }

    public TargetFactory withRandomCenter(Boolean randomCenter) {
        this.randomCenter = randomCenter;
        return this;
    }

    public TargetFactory withRadiusMultiplier(float multiplier) {
        this.radiusMultiplier = multiplier;
        radiusMultiplierActive = true;
        return this;
    }

    public TargetFactory withRingCount(int ringCount) {
        this.ringCount = ringCount;
        return this;
    }

    @Override
    public Shape build(PointF topLeft, PointF bottomRight) {
        Target target = new Target(topLeft, bottomRight)
                .withRingCount(ringCount);
        if (randomCenter) {
            target.withCenter(RandomUtils.getRandomPointInRect(topLeft, bottomRight));
        }
        if (radiusMultiplierActive) {
            target.withRadius(target.getRadius() * radiusMultiplier);
        }
        return target;
    }
}
