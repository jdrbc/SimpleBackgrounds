package ca.jdr23bc.simplebackgrounds.Shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Squares extends Shape {
    public final int MAX_ROWS = 12;
    public final int MIN_ROWS = 2;
    public final int MAX_BORDER_WIDTH = 30;
    public final int MIN_BORDER_WIDTH = 0;

    public int rows;
    public int cols;
    public boolean drawBorder;
    public boolean blackBorder;
    public Grid grid;

    public Squares(int width, int height) {
        super(width, height);
        this.rows =  nextIntInRange(MIN_ROWS, MAX_ROWS);
        this.cols = nextIntInRange(MIN_ROWS, MAX_ROWS);
        grid = new Grid(width, height, rows, cols);
        this.drawBorder = random.nextBoolean();
        this.blackBorder = random.nextBoolean();
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint borderPaint = new Paint();
        if (drawBorder) {
            borderPaint.setStrokeWidth(nextFloatInRange(MIN_BORDER_WIDTH, MAX_BORDER_WIDTH));
            if (blackBorder) {
                borderPaint.setColor(Color.BLACK);
            } else {
                borderPaint.setColor(colorScheme.popRandom());
            }
            borderPaint.setStyle(Paint.Style.STROKE);
        }

        Paint p = new Paint();
        p.setAntiAlias(true);
        while(grid.hasNext()) {
            Grid.Cell cell = grid.next();
            p.setColor(colorScheme.getRandom());
            cell.draw(canvas, p);
            if (drawBorder) {
                cell.draw(canvas, borderPaint);
            }
        }
    }
}
