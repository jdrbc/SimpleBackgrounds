package ca.jdr23bc.backgrounds.colors;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ColorScheme {

    // TODO-jdr use black as a foreground & background color more often
    // TODO-jdr Divide colors into base, main, accent ect

    private final List<Integer> colors = new ArrayList<>();
    private final Integer rootColor;
    private final Colour.ColorScheme csType;

    public ColorScheme(int rootColor) {
        this(rootColor, Colour.getRandomScheme());
    }

    public ColorScheme(int rootColor, Colour.ColorScheme csType) {
        this.rootColor = rootColor;
        this.csType = csType;
        int[] colors = Colour.colorSchemeOfType(rootColor, csType);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            for (int color : Objects.requireNonNull(colors)) {
                this.colors.add(color);
            }
        }
    }

    public int popRandom() {
        if (colors.size() > 0) {
            return colors.remove(randIndex());
        } else {
            return rootColor;
        }
    }

    @TargetApi(24)
    public int popDarkest() {
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
        return colors.remove(darkestIndex);
    }

    @TargetApi(24)
    public int popLightest() {
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
        return colors.remove(lightestIndex);
    }

    public int getRandom() {
        return colors.get(randIndex());
    }

    public int getRootColor() {
        return rootColor;
    }

    public String toString() {
        return "ROOT: " + rootColor + "\n" +
                "COLOR SCHEME TYPE: " + csType;
    }

    private int randIndex() {
        Random rand = new Random();
        return rand.nextInt(colors.size());
    }
}
