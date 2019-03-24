package ca.jdr23bc.backgrounds.backgrounds;

import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ca.jdr23bc.backgrounds.backgrounds.preferences.IBackgroundPreferences;
import ca.jdr23bc.backgrounds.layout.GridLayout;
import ca.jdr23bc.backgrounds.layout.OverlappingSingleCellLayout;
import ca.jdr23bc.backgrounds.layout.RandomGridLayout;
import ca.jdr23bc.backgrounds.layout.RandomLayout;
import ca.jdr23bc.backgrounds.layout.SingleCellLayout;
import ca.jdr23bc.backgrounds.utils.RandomUtils;


public class BackgroundFactory {
    private static final String TAG = Background.class.getCanonicalName();

    private enum PatternType {
        CIRCLE_GRID,
        CIRCLE_RANDOM_GRID,
        CIRCLE_RANDOM,
        RECT_GRID,
        RECT_RANDOM,
        TARGET_GRID,
        STAR_GRID,
        STAR_SINGLE,
        LINE,
        MULTI_TREE,
        TREE,
        @SuppressWarnings("unused") SOLID
    }
    private static final int MAX_NUM_TREES_IN_MULTI_TREE = 10;

    private final IBackgroundPreferences preferences;

    public BackgroundFactory(IBackgroundPreferences preferences) {
        this.preferences = preferences;
    }

    public Background getRandomBackground() {
        return getRandomBackground(getDefaultWidth(), getDefaultHeight());
    }

    private Background getRandomBackground(int width, int height) {
        List<PatternType> enabledPatterns = getEnabledPatterns();
        if (enabledPatterns.isEmpty()) {
            Log.i(TAG, "plain");
            return new PlainBackground(width, height);
        }
        PatternType patternType = enabledPatterns.get(
                RandomUtils.random.nextInt(enabledPatterns.size())
        );
        switch(patternType) {
            case CIRCLE_GRID:
                Log.i(TAG, "circle grid");
                return new CircleBackground(width, height, new GridLayout());
            case CIRCLE_RANDOM_GRID:
                Log.i(TAG, "circle grid");
                return new CircleBackground(width, height, new RandomGridLayout());
            case CIRCLE_RANDOM:
                Log.i(TAG, "circle random");
                return new CircleBackground(width, height, new RandomLayout());
            case RECT_GRID:
                Log.i(TAG, "rect grid");
                return new RectBackground(width, height, new GridLayout());
            case RECT_RANDOM:
                Log.i(TAG, "rect random");
                return new RectBackground(width, height, new RandomLayout());
            case TARGET_GRID:
                Log.i(TAG, "target grid");
                return new TargetBackground(width, height, new RandomGridLayout());
            case STAR_GRID:
                Log.i(TAG, "star grid");
                return new StarBackground(width, height, new RandomGridLayout());
            case STAR_SINGLE:
                Log.i(TAG, "star single");
                return new StarBackground(width, height, new SingleCellLayout());
            case LINE:
                Log.i(TAG, "line");
                return new LineBackground(width, height);
            case MULTI_TREE:
                Log.i(TAG, "multi tree");
                return getMultiTreeBackground(width, height);
            case TREE:
                Log.i(TAG, "tree");
                return getTreeBackground(width, height);
            default:
                Log.i(TAG, "plain");
                return new PlainBackground(width, height);
        }
    }

    private List<PatternType> getEnabledPatterns() {
        List<PatternType> ret = new ArrayList<>();
        if (preferences.treesEnabled()) {
            ret.add(PatternType.TREE);
            ret.add(PatternType.MULTI_TREE);
        }
        if (preferences.circlesEnabled()) {
            ret.add(PatternType.CIRCLE_GRID);
            ret.add(PatternType.CIRCLE_RANDOM_GRID);
            ret.add(PatternType.CIRCLE_RANDOM);
        }
        if (preferences.linesEnabled()) {
            ret.add(PatternType.LINE);
        }
        if (preferences.rectanglesEnabled()) {
            ret.add(PatternType.RECT_GRID);
            ret.add(PatternType.RECT_RANDOM);
        }
        if (preferences.starsEnabled()) {
            ret.add(PatternType.STAR_GRID);
            ret.add(PatternType.STAR_SINGLE);
        }
        if (preferences.targetsEnabled()) {
            ret.add(PatternType.TARGET_GRID);
        }
        return ret;
    }

    private TreeBackground getTreeBackground(int width, int height) {
        return new TreeBackground(width, height, new SingleCellLayout());
    }

    private TreeBackground getMultiTreeBackground(int width, int height) {
        return new TreeBackground(width, height, new OverlappingSingleCellLayout(RandomUtils.getRandomIntInRange(1, MAX_NUM_TREES_IN_MULTI_TREE)));
    }

    private int getDefaultWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getDefaultHeight() {
        // navigation bar height
        int navigationBarHeight = 0;
        int resourceId = Resources.getSystem().getIdentifier(
                "navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return Resources.getSystem().getDisplayMetrics().heightPixels + navigationBarHeight;
    }
}
