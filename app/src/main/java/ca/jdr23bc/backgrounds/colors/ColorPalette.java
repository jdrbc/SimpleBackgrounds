package ca.jdr23bc.backgrounds.colors;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class ColorPalette {
    private static final String TAG = ColorPalette.class.getCanonicalName();
    private static final List<ColorSchemeUtils.ColorScheme> supportedSchemes = Arrays.asList(
            ColorSchemeUtils.ColorScheme.ColorSchemeMonochromatic,
            ColorSchemeUtils.ColorScheme.ColorSchemeTetrad,
            ColorSchemeUtils.ColorScheme.ColorSchemeAnalagous,
            ColorSchemeUtils.ColorScheme.ColorSchemeTriad
    );

    private final List<Integer> backgroundColors = new ArrayList<>();
    private final List<Integer> colors = new ArrayList<>();
    private final Integer rootColor;
    private final ColorSchemeUtils.ColorScheme csType;

    public ColorPalette(int base) {
        this.rootColor = base;
        Log.i(TAG, "root color: " + rootColor);
        this.csType = supportedSchemes.get(
                RandomUtils.getRandomIntInRange(0, supportedSchemes.size())
        );
        Log.i(TAG, String.format("color scheme: %s", csType));
        int[] colors = ColorSchemeUtils.generateSchemeOfType(rootColor, csType);
        Log.i(TAG, String.format("num colors: %s", colors.length));
        for (int color : colors) {
            this.colors.add(color);
            Log.i(TAG, "color int: " + color);
        }
        if (this.colors.isEmpty()) {
            this.colors.add(0);
        }
        this.backgroundColors.add(getDarkest());
        this.backgroundColors.add(getLightest());
    }

    public int getRandomBackgroundColor() {
        return backgroundColors.get(RandomUtils.getRandomIntInRange(0, backgroundColors.size()));
    }

    public int popRandom() {
        if (colors.size() > 0) {
            return colors.remove(randIndex());
        } else {
            return rootColor;
        }
    }

    public int popDarkest() {
        if (colors.isEmpty()) {
            return Color.BLACK;
        } else {
            return colors.remove(getDarkestColorIndex());
        }
    }

    private int getDarkest() {
        return colors.get(getDarkestColorIndex());
    }

    @TargetApi(Build.VERSION_CODES.N)
    private int getDarkestColorIndex() {
        if (colors.isEmpty()) {
            return 0;
        }
        int darkestIndex = 0;
        float darkestLuminance = Color.luminance(colors.get(0));
        for (int i = 1; i < colors.size(); i++) {
            if (darkestLuminance < Color.luminance(colors.get(i))) {
                darkestLuminance = Color.luminance(colors.get(i));
                darkestIndex = i;
            }
        }
        return darkestIndex;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private int getLightestIndex() {
        if (colors.isEmpty()) {
            return 0;
        }
        int lightestIndex = 0;
        float lightestLuminance = Color.luminance(colors.get(0));
        for (int i = 1; i < colors.size(); i++) {
            if (lightestLuminance > Color.luminance(colors.get(i))) {
                lightestLuminance = Color.luminance(colors.get(i));
                lightestIndex = i;
            }
        }
        return lightestIndex;
    }

    @TargetApi(24)
    public int popLightest() {
        if (colors.isEmpty()) {
            return Color.WHITE;
        } else {
            return colors.remove(getLightestIndex());
        }
    }

    @TargetApi(24)
    private int getLightest() {
        return colors.get(getLightestIndex());
    }

    public int getRandom() {
        return colors.get(randIndex());
    }

    public int getRootColor() {
        return rootColor;
    }

    @NonNull
    public String toString() {
        return "ROOT: " + rootColor + "\n" +
                "COLOR SCHEME TYPE: " + csType;
    }

    private int randIndex() {
        Random rand = new Random();
        return rand.nextInt(colors.size());
    }
}
