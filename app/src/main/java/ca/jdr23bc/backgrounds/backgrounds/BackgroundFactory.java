package ca.jdr23bc.backgrounds.backgrounds;

import android.content.res.Resources;

import ca.jdr23bc.backgrounds.layout.GridLayout;
import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.layout.RandomLayout;
import ca.jdr23bc.backgrounds.layout.SingleCellLayout;
import ca.jdr23bc.backgrounds.utils.RandomUtils;


public class BackgroundFactory {
    public static final int NUM_PATTERNS = 10;

    public Background getRandomBackground() {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        return getRandomBackground(width, height);
    }

    public Background getRandomBackground(int width, int height) {
        int patternType =  RandomUtils.random.nextInt(NUM_PATTERNS);
        switch(patternType) {
            case 0:
                return new CircleBackground(width, height, new SingleCellLayout());
            case 1:
                return new CircleBackground(width, height, new GridLayout());
            case 2:
                return new CircleBackground(width, height, new RandomLayout());
            case 3:
                return new RectBackground(width, height, new GridLayout());
            case 4:
                return new RectBackground(width, height, new RandomLayout());
            case 5:
                return new TargetBackground(width, height, new GridLayout());
            case 6:
                return new TargetBackground(width, height, new SingleCellLayout());
            case 7:
                return new StarBackground(width, height, new GridLayout());
            case 8:
                return new StarBackground(width, height, new SingleCellLayout());
            case 9:
                return new LineBackground(width, height);
            default:
                return new TreeBackground(width, height, new SingleCellLayout());
        }
    }
}
