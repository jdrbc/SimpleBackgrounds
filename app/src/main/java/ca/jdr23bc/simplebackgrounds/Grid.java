package ca.jdr23bc.simplebackgrounds;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.Iterator;

public class Grid implements Iterator<Grid.Cell> {
    public int width;
    public int height;
    public float cellWidth;
    public float cellHeight;

    private Cell currCell;

    public Grid(int width, int height, int rows, int cols) {
        super();
        this.width = width;
        this.height = height;
        this.cellWidth = width / cols;
        this.cellHeight = height / rows;
        this.currCell = new Cell(-cellWidth, -cellHeight, this);
    }

    @Override
    public boolean hasNext() {
        boolean hasNext = currCell.y < height + cellHeight;
        if (!hasNext) {
            currCell = new Cell(-cellWidth, -cellHeight, this);
        }
        return hasNext;
    }

    @Override
    public Cell next() {
        currCell = currCell.getNextCell();
        return currCell;
    }

    public class Cell {
        Grid grid;
        float width;
        float height;
        float x;
        float y;

        public Cell(float x, float y, Grid grid) {
            this.grid = grid;
            this.x = x;
            this.y = y;
            this.width = grid.cellWidth;
            this.height = grid.cellHeight;
        }

        public Cell getNextCell() {
            float nextX = x + width;
            float nextY = y;
            if (nextX > grid.width + width) {
                nextX = 0;
                nextY += height;
            }
            return new Cell(nextX, nextY, grid);
        }

        public int getRow() {
            return (int) (x / cellWidth);
        }

        public int getCol() {
            return (int) (y / cellHeight);
        }

        public void draw(Canvas canvas, Paint paint) {
            canvas.drawRect(x, y, x + cellWidth, y + cellHeight, paint);
        }

        public PointF getTopLeft() {
            return new PointF(x, y);
        }
    }
}
