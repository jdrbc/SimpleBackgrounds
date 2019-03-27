package ca.jdr23bc.backgrounds.colors;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mattyork.colours.Colour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class ColorPalette {
    private static final String TAG = ColorPalette.class.getCanonicalName();

    // TODO-jdr Divide colors into base, main, accent ect

    private final List<Integer> backgroundColors = new ArrayList<>();
    private final List<Integer> colors = new ArrayList<>();
    private final Integer rootColor;
    private final Colour.ColorScheme csType;
    private final List<Colour.ColorScheme> supportedSchemes = Arrays.asList(
            Colour.ColorScheme.ColorSchemeMonochromatic,
            Colour.ColorScheme.ColorSchemeComplementary,
            Colour.ColorScheme.ColorSchemeAnalagous
    );

    public ColorPalette(int rootColor) {
        this(rootColor, Colour.ColorScheme.values()[
                RandomUtils.getRandomIntInRange(0, Colour.ColorScheme.values().length)
        ]);
    }

    private ColorPalette(int rootColor, Colour.ColorScheme csType) {
        Log.i(TAG, String.format("color scheme: %s", csType));
        this.rootColor = rootColor;
        this.csType = csType;
        int[] colors = Colour.colorSchemeOfType(rootColor, csType);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            for (int color : Objects.requireNonNull(colors)) {
                this.colors.add(color);
            }
            this.backgroundColors.add(Color.BLACK);
            this.backgroundColors.add(getDarkest());
            this.backgroundColors.add(Color.WHITE);
            this.backgroundColors.add(getLightest());
        }
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
        return colors.remove(getDarkestColorIndex());
    }

    private int getDarkest() {
        return colors.get(getDarkestColorIndex());
    }

    @TargetApi(Build.VERSION_CODES.N)
    private int getDarkestColorIndex() {
        if (colors.size() == 0) {
            return rootColor;
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
        if (colors.size() == 0) {
            return rootColor;
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
        return colors.remove(getLightestIndex());
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
