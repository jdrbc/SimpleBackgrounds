package ca.jdr23bc.simplebackgrounds.colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorScheme {

    // TODO-jdr use black as a foreground color more often
    // TODO-jdr Divide colors into base, main, accent ect

    public List<Integer> colors = new ArrayList<>();
    public Integer rootColor;
    Colour.ColorScheme csType;

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
