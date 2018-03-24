package ca.jdr23bc.backgrounds.layout;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

import ca.jdr23bc.backgrounds.utils.RandomUtils;

/**
 * Overlapping single cells that are random offset from the center 1/2 width and 1/8 height
 */
public class OverlappingSingleCellLayout extends Layout {
    private static final int WIDTH_OFFSET_RATIO = 2;
    private static final int HEIGHT_OFFSET_RATIO = 2;

    private List<SingleCellLayout> layouts;
    private SingleCellLayout currentLayout;
    private int currentLayoutIndex;

    public OverlappingSingleCellLayout(Integer numCells) {
        layouts = new ArrayList<>();
        for (Integer i = 0; i < numCells; i++) {
            SingleCellLayout layout = new SingleCellLayout();
            layouts.add(layout);
        }
    }

    @Override
    public void init(PointF topLeft, PointF bottomRight) {
        super.init(topLeft, bottomRight);
        currentLayoutIndex = 0;
        for (SingleCellLayout layout : layouts) {
            float halfWidth = (bottomRight.x - topLeft.x) / WIDTH_OFFSET_RATIO;
            float offsetX = RandomUtils.getRandomFloatInRange(-halfWidth, halfWidth);
            float offsetY = RandomUtils.getRandomFloatInRange((getHeight() / HEIGHT_OFFSET_RATIO), 0);
            PointF offsetTopLeft = new PointF(offsetX, topLeft.y + offsetY);
            PointF offsetBottomRight = new PointF(bottomRight.x + offsetX, bottomRight.y + offsetY);
            layout.init(offsetTopLeft, offsetBottomRight);
        }
        currentLayout = layouts.get(currentLayoutIndex);
    }

    @Override
    protected boolean finished() {
        return currentLayoutIndex == layouts.size() - 1 && currentLayout.finished();
    }

    @Override
    public Cell next() {
        if (currentLayout.finished()) {
            currentLayout = layouts.get(++currentLayoutIndex);
        }
        return currentLayout.next();
    }
}
