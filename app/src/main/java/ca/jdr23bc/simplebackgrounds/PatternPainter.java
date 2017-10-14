package ca.jdr23bc.simplebackgrounds;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ca.jdr23bc.simplebackgrounds.Patterns.DotGrid;
import ca.jdr23bc.simplebackgrounds.Patterns.Lines;
import ca.jdr23bc.simplebackgrounds.Patterns.RandomDots;
import ca.jdr23bc.simplebackgrounds.Patterns.Squares;
import ca.jdr23bc.simplebackgrounds.Patterns.Star;
import ca.jdr23bc.simplebackgrounds.Patterns.Tree;

public class PatternPainter {
    private static final List<Style> STYLES =
            Collections.unmodifiableList(Arrays.asList(Style.values()));

    public enum Style {
        Lines, Dots, Grid, Dot_Grid, Star//, Tree
    }
    Canvas canvas;
    Style style;
    Random random = new Random();
    ColorScheme colorScheme;
    int rootColor;

    public PatternPainter(Canvas canvas) {
        this.canvas = canvas;
        this.style = STYLES.get(random.nextInt(STYLES.size()));
        rootColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        this.colorScheme = new ColorScheme(rootColor);
    }

    public void paint() {
        if (style == Style.Lines) {
            paintLines();
        } else if (style == Style.Dots) {
            paintDots();
        } else if (style == Style.Grid) {
            paintGrid();
//        } else if (style == Style.Tree) {
//            paintTree();
        } else if (style == Style.Dot_Grid) {
            paintDotGrid();
        } else if (style == Style.Star) {
            paintStar();
        }
    }

    public void paintStar() {
        Star s = new Star(canvas.getWidth(), canvas.getHeight());
        s.fillAndDraw(canvas);
    }

    public void paintTree() {
        Tree t = new Tree(canvas.getWidth(), canvas.getHeight());
        t.grow();
        t.fillAndDraw(canvas);
    }

    public void paintDots() {
        RandomDots rd = new RandomDots(canvas.getWidth(), canvas.getHeight());
        rd.fillAndDraw(canvas);
    }

    public void paintLines() {
        Lines lines = new Lines(canvas.getWidth(), canvas.getHeight());
        lines.fillAndDraw(canvas);
    }

    public void paintGrid() {
        Squares squares = new Squares(canvas.getWidth(), canvas.getHeight());
        squares.draw(canvas);
    }

    public void paintDotGrid() {
        DotGrid dg = new DotGrid(canvas.getWidth(), canvas.getHeight());
        dg.fillAndDraw(canvas);
    }

    public String toString() {
        return "STYLE: " + style.toString() + "\n" +
                colorScheme.toString();
    }
}
