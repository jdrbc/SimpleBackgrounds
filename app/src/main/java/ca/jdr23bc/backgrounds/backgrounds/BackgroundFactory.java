package ca.jdr23bc.backgrounds.backgrounds;

import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.layout.RandomLayout;
import ca.jdr23bc.backgrounds.layout.SingleCellLayout;
import ca.jdr23bc.backgrounds.layout.GridLayout;
import ca.jdr23bc.backgrounds.utils.RandomUtils;


public class BackgroundFactory {
    public static final int NUM_PATTERNS = 5;
    public static final int NUM_LAYOUTS = 3;

    public Background getRandomBackground(int width, int height) {
        int patternNum = RandomUtils.random.nextInt(NUM_PATTERNS);
        switch(patternNum) {
            case 0:
                return new CircleBackground(width, height, getRandomLayout());
            case 1:
                return new LineBackground(width, height);
            case 2:
                return new RectBackground(width, height, getRandomLayout());
            case 3:
                return new TargetBackground(width, height, getRandomLayout());
            case 4:
                return new TreeBackground(width, height);
            default:
                return new StarBackground(width, height, getRandomLayout());
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

}
