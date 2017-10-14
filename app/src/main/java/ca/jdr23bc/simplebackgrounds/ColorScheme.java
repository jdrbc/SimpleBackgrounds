package ca.jdr23bc.simplebackgrounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.jdr23bc.simplebackgrounds.Patterns.Pattern;

public class ColorScheme {

    public List<Integer> colors = new ArrayList<>();
    public Integer rootColor;
    Colour.ColorScheme csType;

    public ColorScheme(int rootColor, Colour.ColorScheme csType) {
        this.rootColor = rootColor;
        colors.add(rootColor);
        this.csType = csType;
        int[] ints = Colour.colorSchemeOfType(rootColor, csType);
        for (int index = 0; index < ints.length; index++) {
            colors.add(ints[index]);
        }
    }

    public ColorScheme(int rootColor) {
        this.rootColor = rootColor;
        csType = Colour.getRandomScheme();
        int[] ints = Colour.colorSchemeOfType(rootColor, csType);
        for (int index = 0; index < ints.length; index++) {
            colors.add(ints[index]);
        }
    }

    public int popRandom() {
        if (colors.size() > 0) {
            return colors.remove(randIndex());
        } else {
            return rootColor;
        }
    }

    public int getRandom() {
        return colors.get(randIndex());
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
