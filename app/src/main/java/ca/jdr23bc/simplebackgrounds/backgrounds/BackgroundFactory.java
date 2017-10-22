package ca.jdr23bc.simplebackgrounds.backgrounds;

import ca.jdr23bc.simplebackgrounds.layout.Layout;
import ca.jdr23bc.simplebackgrounds.layout.RandomLayout;
import ca.jdr23bc.simplebackgrounds.layout.SingleCellLayout;
import ca.jdr23bc.simplebackgrounds.layout.GridLayout;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;


public class BackgroundFactory {
    public static final int NUM_PATTERNS = 4;
    public static final int NUM_LAYOUTS = 3;

    public Background getRandomBackground() {
        Layout layout;
        int layoutNum = RandomUtils.random.nextInt(NUM_LAYOUTS);
        int patternNum = RandomUtils.random.nextInt(NUM_PATTERNS);
        switch(2) {
            case 0:
                layout = new SingleCellLayout();
                break;
            case 1:
                layout = new GridLayout();
                break;
            case 2:
                layout = new RandomLayout();
                break;
            default:
                return null;
        }
        switch(patternNum) {
            case 0:
                return new CircleBackground(layout);
            case 1:
                return new LineBackground();
            case 2:
                return new RectBackground(layout);
            case 3:
                return new TargetBackground(layout);
            default:
                return new StarBackground(layout);
        }
    }

}
