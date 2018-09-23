package ca.jdr23bc.backgrounds.backgrounds;


import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.util.Log;

import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.painters.ShapePainter;
import ca.jdr23bc.backgrounds.painters.TreePainter;
import ca.jdr23bc.backgrounds.shapes.ShapeFactory;
import ca.jdr23bc.backgrounds.shapes.tree.TreeFactory;

public class TreeBackground extends ShapeBackground {
    private static final String TAG = TreeBackground.class.getCanonicalName();
    private static final int SHADE_STEP_SIZE_FOR_MULTITREE = 25; // value 0-255 controls how much lighter each subsequent tree is

    private TreeFactory factory;
    private TreePainter painter;
    private Integer currentShapeNumber = 0;

    TreeBackground(int width, int height, Layout layout) {
        super(width, height);
        factory = new TreeFactory(layout);
        painter = new TreePainter();
    }

    /**
     * Counts number of trees in factory and queues up filters to use while drawing the trees
     */
    @Override
    public void queueNextShapeForPainting() {
        super.queueNextShapeForPainting();
        currentShapeNumber++;

        Integer totalNumberOfShapes = factory.getTotalNumberOfShapes();
        Log.d(TAG, "total number of shapes: " + totalNumberOfShapes);

        // Add darker filters at the start and lighten as the last trees are drawn
        // white filter does not darken at all
        // When number of shapes painted is equal to the total number of shapes want to use white filter
        int numberOfShapesLeft = totalNumberOfShapes - currentShapeNumber;
        int progressFilter = Math.max(0, 255 - (numberOfShapesLeft * SHADE_STEP_SIZE_FOR_MULTITREE));
        int filterColor =  Color.rgb(progressFilter, progressFilter, progressFilter);
        LightingColorFilter filter = new LightingColorFilter(filterColor, 0x00000000);
        Log.d(TAG, "setting filter with color: " + filterColor);
        getPainter().setColorFilter(filter);
    }

    @Override
    public void freeMemory() {
        factory = null;
        painter = null;
        super.freeMemory();
    }

    @Override
    protected ShapeFactory getFactory() {
        return factory;
    }

    @Override
    protected ShapePainter getPainter() {
        return painter;
    }
}
