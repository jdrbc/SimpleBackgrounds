package ca.jdr23bc.backgrounds.shapes.tree;


import android.graphics.PointF;

import ca.jdr23bc.backgrounds.utils.MathUtils;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class Leaf {

    private final PointF base;
    private final PointF direction;
    private final float length;
    private final float width;

    public Leaf (PointF base, Tree tree) {
        this.length = tree.getLeafLength();
        this.width = tree.getLeafWidth();
        this.base = base;
        this.direction = getRandomDirection(tree);
    }

    public PointF getBase() {
        return base;
    }

    public PointF getTip() {
        return MathUtils.add(base, MathUtils.mult(direction, length));
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public PointF getSideRightOfBase() {
        return MathUtils.add(getWidestPoint(), MathUtils.mult(new PointF(-direction.y, direction.x), width / 2));
    }

    public PointF getSideLeftOfBase() {
        //noinspection SuspiciousNameCombination
        return MathUtils.add(getWidestPoint(), MathUtils.mult(new PointF(direction.y, -direction.x), width / 2));
    }

    private PointF getWidestPoint() {
        return MathUtils.add(getBase(), MathUtils.mult(direction, length * 1/2));
    }

    private PointF getRandomDirection(Tree tree) {
        float oneQuarterWidth = tree.getWidth() / 4;
        float oneQuarterHeight = tree.getHeight() / 4;
        PointF randomPointNearTopOfTree = new PointF(
                RandomUtils.getRandomFloatInRange(oneQuarterWidth, 3 * oneQuarterWidth),
                -oneQuarterHeight);
        return  MathUtils.normalize(MathUtils.sub(base, randomPointNearTopOfTree));
    }
}
