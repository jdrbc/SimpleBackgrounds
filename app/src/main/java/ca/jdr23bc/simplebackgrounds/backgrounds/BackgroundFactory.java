package ca.jdr23bc.simplebackgrounds.backgrounds;

import android.graphics.PointF;

import ca.jdr23bc.simplebackgrounds.layout.Layout;
import ca.jdr23bc.simplebackgrounds.layout.SingleCellLayout;
import ca.jdr23bc.simplebackgrounds.painters.RectPainter;
import ca.jdr23bc.simplebackgrounds.painters.StarPainter;
import ca.jdr23bc.simplebackgrounds.painters.TargetPainter;
import ca.jdr23bc.simplebackgrounds.layout.GridLayout;
import ca.jdr23bc.simplebackgrounds.shapes.RectFactory;
import ca.jdr23bc.simplebackgrounds.shapes.Star;
import ca.jdr23bc.simplebackgrounds.shapes.StarFactory;
import ca.jdr23bc.simplebackgrounds.shapes.Target;
import ca.jdr23bc.simplebackgrounds.shapes.TargetFactory;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;


public class BackgroundFactory {
    public static final int NUM_PATTERNS = 4;
    public static final int NUM_LAYOUTS = 2;

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
