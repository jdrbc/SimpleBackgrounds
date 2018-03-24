package ca.jdr23bc.backgrounds.backgrounds;

import android.content.res.Resources;

import ca.jdr23bc.backgrounds.layout.GridLayout;
import ca.jdr23bc.backgrounds.layout.OverlappingSingleCellLayout;
import ca.jdr23bc.backgrounds.layout.RandomLayout;
import ca.jdr23bc.backgrounds.layout.SingleCellLayout;
import ca.jdr23bc.backgrounds.utils.RandomUtils;


public class BackgroundFactory {
    private static final int NUM_PATTERNS = 10;
    private static final int MAX_NUM_TREES_IN_MULTI_TREE = 10;

    public Background getRandomBackground() {
        return getRandomBackground(getDefaultWidth(), getDefaultHeight());
    }

    public Background getRandomBackground(int width, int height) {
        int patternType =  RandomUtils.random.nextInt(NUM_PATTERNS);
        switch(patternType) {
            case 0:
                return new CircleBackground(width, height, new GridLayout());
            case 1:
                return new CircleBackground(width, height, new RandomLayout());
            case 2:
                return new RectBackground(width, height, new GridLayout());
            case 3:
                return new RectBackground(width, height, new RandomLayout());
            case 4:
                return new TargetBackground(width, height, new GridLayout());
            case 5:
                return new StarBackground(width, height, new GridLayout());
            case 6:
                return new StarBackground(width, height, new SingleCellLayout());
            case 7:
                return new LineBackground(width, height);
            case 8:
                return getMultiTreeBackground(width, height);
            default:
                return getTreeBackground(width, height);
        }
    }

    public TreeBackground getTreeBackground() {
        return getTreeBackground(getDefaultWidth(), getDefaultHeight());
    }

    public TreeBackground getTreeBackground(int width, int height) {
        return new TreeBackground(width, height, new SingleCellLayout());
    }

    public TreeBackground getMultiTreeBackground() {
        return getMultiTreeBackground(getDefaultWidth(), getDefaultHeight());
    }

    public TreeBackground getMultiTreeBackground(int width, int height) {
        return new TreeBackground(width, height, new OverlappingSingleCellLayout(RandomUtils.getRandomIntInRange(1, MAX_NUM_TREES_IN_MULTI_TREE)));
    }

    private int getDefaultWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getDefaultHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
