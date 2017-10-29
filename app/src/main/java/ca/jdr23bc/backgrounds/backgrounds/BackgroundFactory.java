package ca.jdr23bc.backgrounds.backgrounds;

import android.content.res.Resources;

import ca.jdr23bc.backgrounds.layout.GridLayout;
import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.layout.RandomLayout;
import ca.jdr23bc.backgrounds.layout.SingleCellLayout;
import ca.jdr23bc.backgrounds.utils.RandomUtils;


public class BackgroundFactory {
    public static final int NUM_PATTERNS = 6;
    public static final int NUM_LAYOUTS = 3;
    public static final int NUM_STAR_LAYOUTS = 2;
    public static final int NUM_RECT_LAYOUTS = 2;

    public Background getRandomBackground() {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        return getRandomBackground(width, height);
    }

    public Background getRandomBackground(int width, int height) {
        int patternType =  RandomUtils.random.nextInt(NUM_PATTERNS);
        switch(patternType) {
            case 0:
                return new CircleBackground(width, height, getRandomLayout());
            case 1:
                return new LineBackground(width, height);
            case 2:
                return new RectBackground(width, height, getRandomRectLayout());
            case 3:
                return new TargetBackground(width, height, getRandomLayout());
            case 4:
                return new StarBackground(width, height, getRandomStarLayout());
            default:
                return new TreeBackground(width, height, new SingleCellLayout());
        }
    }

    private Layout getRandomLayout() {
        int layoutNum = RandomUtils.random.nextInt(NUM_LAYOUTS);
        switch(layoutNum) {
            case 0:
                return new SingleCellLayout();
            case 1:
                return new GridLayout();
            default:
                return new RandomLayout();
        }
    }

    private Layout getRandomRectLayout() {
        int layoutNum = RandomUtils.random.nextInt(NUM_RECT_LAYOUTS);
        switch(layoutNum) {
            case 0:
                return new RandomLayout();
            default:
                return new GridLayout();
        }
    }

    private Layout getRandomStarLayout() {
        int layoutNum = RandomUtils.random.nextInt(NUM_STAR_LAYOUTS);
        switch(layoutNum) {
            case 0:
                return new SingleCellLayout();
            default:
                return new GridLayout();
        }
    }
}
