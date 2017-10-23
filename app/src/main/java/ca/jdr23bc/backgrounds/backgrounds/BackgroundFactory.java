package ca.jdr23bc.backgrounds.backgrounds;

import ca.jdr23bc.backgrounds.layout.Layout;
import ca.jdr23bc.backgrounds.layout.RandomLayout;
import ca.jdr23bc.backgrounds.layout.SingleCellLayout;
import ca.jdr23bc.backgrounds.layout.GridLayout;
import ca.jdr23bc.backgrounds.utils.RandomUtils;


public class BackgroundFactory {
    public static final int NUM_PATTERNS = 5;
    public static final int NUM_LAYOUTS = 3;

    public Background getRandomBackground() {
        Layout layout;
        int layoutNum = RandomUtils.random.nextInt(NUM_LAYOUTS);
        int patternNum = RandomUtils.random.nextInt(NUM_PATTERNS);
        switch(layoutNum) {
            case 0:
                layout = new SingleCellLayout();
                break;
            case 1:
                layout = new GridLayout();
                break;
            default:
                layout = new RandomLayout();
                break;
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
