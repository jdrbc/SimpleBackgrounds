package ca.jdr23bc.simplebackgrounds;

import android.graphics.PointF;

import java.util.Random;

public class RandomGrid {
    public static float MAX_DEVIANCE = 6f / 7f;

    public static int MIN_DEFAULT_ROWS = 5;
    public static int MAX_DEFAULT_ROWS = 10;

    // [row][col] grid
    public PointF[][] points;
    public int rows;
    public int cols;
    public float height;
    public float width;
    OpenSimplexNoise osn;

    public RandomGrid(float width, float height) {
        this(width, height, MAX_DEFAULT_ROWS + new Random().nextInt(MIN_DEFAULT_ROWS));
    }

    public RandomGrid(float width, float height, int rows) {
        this(width, height, rows, Math.round(width / (height / rows)));
    }

    public RandomGrid(float width, float height, int rows, int cols) {
        this.osn = new OpenSimplexNoise(new Random().nextLong());
        this.height = height;
        this.width = width;

        // Add row and column on borders
        this.rows = rows + 2;
        this.cols = cols + 2;
        float cellWidth = width / (cols - 2);
        float cellHeight = height / (rows - 2);

        points = new PointF[this.rows][this.cols];
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                // Pick a random y within this row
                float starty = (row * cellHeight) - cellHeight;
                float shuffley = (float) osn.eval(0, row, col) * cellHeight * MAX_DEVIANCE;

                // Pick a random x within this column
                float startx = (col * cellWidth) - cellWidth;
                float shufflex = (float) osn.eval(1, row, col) * cellWidth * MAX_DEVIANCE;
                points[row][col] = new PointF(startx + shufflex, starty + shuffley);
            }
        }
    }
}
