package ca.jdr23bc.backgrounds.colors;

import android.graphics.Color;

import java.util.LinkedHashSet;

class ColorSchemeUtils extends Color {

    //Color Scheme Enumeration (for color scheme generation)
    public enum ColorScheme {
        ColorSchemeAnalagous, ColorSchemeMonochromatic, ColorSchemeTriad, ColorSchemeTetrad
    }

    // ///////////////////////////////////
    // 4 Color Scheme from Color
    // ///////////////////////////////////

    /**
     * Creates an int[] of 4 Colors that complement the Color.
     *
     * @param type ColorSchemeAnalagous, ColorSchemeMonochromatic,
     *             ColorSchemeTriad, ColorSchemeTetrad
     * @return ArrayList<Integer>
     */
    static int[] generateSchemeOfType(int color, ColorScheme type) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);

        int[] scheme;
        switch (type) {
            case ColorSchemeAnalagous:
                scheme = ColorSchemeUtils.generateAnalagous(hsv);
                break;
            case ColorSchemeMonochromatic:
                scheme = ColorSchemeUtils.generateMonochromatic(hsv);
                break;
            case ColorSchemeTriad:
                scheme = ColorSchemeUtils.generateTriad(hsv);
                break;
            case ColorSchemeTetrad:
                scheme = ColorSchemeUtils.generateTetrad(hsv);
                break;
            default:
                scheme = new int[] { color };
        }
        return removeDupes(scheme);
    }

    private static int[] removeDupes(int[] scheme) {
        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>();
        for (int i : scheme) {
            hashSet.add(i);
        }
        int[] ret = new int[hashSet.size()];
        int index = 0;
        for (Integer val : hashSet) {
            ret[index++] = val;
        }
        return ret;
    }

    private static int[] generateAnalagous(float[] hsv) {
        float baseHue = hsv[0];
        float baseSaturation = scaleToRange(hsv[1], .1f, .9f);
        float baseValue = scaleToRange(hsv[2], .1f, .9f);

        float[] CA1 = {
                ColorSchemeUtils.addDegrees(baseHue, 15),
                baseSaturation,
                baseValue
        };
        float[] CA2 = {
                ColorSchemeUtils.addDegrees(hsv[0], 30),
                baseSaturation,
                baseValue
        };
        float[] CB1 = {
                ColorSchemeUtils.addDegrees(hsv[0], -15),
                baseSaturation,
                baseValue
        };
        float[] CB2 = {
                ColorSchemeUtils.addDegrees(hsv[0], -30),
                baseSaturation,
                baseValue
        };
        float[] baseHSV = {
                baseHue,
                baseSaturation,
                baseValue
        };

        return new int[] {
                Color.HSVToColor(CA1),
                Color.HSVToColor(CA2),
                Color.HSVToColor(CB1),
                Color.HSVToColor(CB2),
                Color.HSVToColor(baseHSV)
        };
    }

    private static int[] generateMonochromatic(float[] hsv) {
        float baseHue = hsv[0];
        float baseSaturation = hsv[1];
        float baseValue = hsv[2];
        float[] brightest = {
                baseHue,
                scaleToRange(baseSaturation, .80f, 1),
                scaleToRange(baseSaturation, 0, .20f)
        };
        float[] brighter = {
                baseHue,
                scaleToRange(baseSaturation, .60f, 1),
                scaleToRange(baseSaturation, 0, .30f)
        };
        float[] darker = {
                baseHue,
                scaleToRange(baseSaturation, 0, .30f),
                scaleToRange(baseSaturation, .60f, 1)
        };
        float[] darkest = {
                baseHue,
                scaleToRange(baseSaturation, 0, .20f),
                scaleToRange(baseSaturation, .80f, 1)
        };
        float[] baseHSV = {
                baseHue,
                baseSaturation,
                baseValue
        };

        return new int[]{
                Color.HSVToColor(baseHSV),
                Color.HSVToColor(brightest),
                Color.HSVToColor(brighter),
                Color.HSVToColor(darker),
                Color.HSVToColor(darkest)
        };
    }

    private static int[] generateTriad(float[] hsv) {
        float baseHue = hsv[0];
        float baseSaturation = scaleToRange(hsv[1], .75f, 1);
        float baseValue = scaleToRange(hsv[1], .5f, .9f);
        float[] base = {
                baseHue,
                baseSaturation,
                baseValue
        };
        float[] rightSide = {
                ColorSchemeUtils.addDegrees(baseHue, 165),
                baseSaturation,
                baseValue
        };
        float[] leftSide = {
                ColorSchemeUtils.addDegrees(baseHue, 195),
                baseSaturation,
                baseValue
        };

        return new int[]{
                Color.HSVToColor(base),
                Color.HSVToColor(rightSide),
                Color.HSVToColor(leftSide)
        };
    }

    private static int[] generateTetrad(float[] hsv) {
        float baseHue = hsv[0];
        float baseSaturation = scaleToRange(hsv[1], .1f, .9f);
        float baseValue = scaleToRange(hsv[1], .1f, .9f);

        float[] topLeft = {
                baseHue,
                baseSaturation,
                baseValue
        };
        float[] topRight = {
                addDegrees(baseHue, 30),
                baseSaturation,
                baseValue
        };
        float[] bottomLeft = {
                addDegrees(topLeft[0], 180),
                baseSaturation,
                baseValue
        };
        float[] bottomRight = {
                addDegrees(topRight[0], 180),
                baseSaturation,
                baseValue
        };
        return new int[]{
                Color.HSVToColor(topLeft),
                Color.HSVToColor(topRight),
                Color.HSVToColor(bottomLeft),
                Color.HSVToColor(bottomRight)
        };
    }

    // Convenience method
    private static float scaleToRange(float value, float newMin, float newMax) {
        return scaleToRange(value, 0, 1, newMin, newMax);
    }

    /**
     * @param value The value between old min and old max to scale into the new range
     * @param oldMin The minimum value of the range for the given value
     * @param oldMax The maximum value of the range for the given value
     * @param newMin The minimum of the range that the value will be scaled to
     * @param newMax The maximum of the range that the value will be scaled to
     * @return The value scaled to the new range
     */
    @SuppressWarnings("SameParameterValue")
    private static float scaleToRange(float value, float oldMin, float oldMax, float newMin, float newMax) {
        float percentageValueInOldRange = (value - oldMin) / oldMax;
        return newMin + (percentageValueInOldRange * newMax);
    }

    private static float addDegrees(float addDeg, float staticDeg) {
        return (staticDeg + addDeg) % 360;
    }
}