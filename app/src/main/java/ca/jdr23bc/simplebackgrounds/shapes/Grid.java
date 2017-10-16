package ca.jdr23bc.simplebackgrounds.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ca.jdr23bc.simplebackgrounds.utils.MathUtils;
import ca.jdr23bc.simplebackgrounds.utils.RandomUtils;

public class Grid implements Iterator<Grid.Cell> {
    private static final float MIN_CELL_SIZE = 10f;
    private static final int MIN_CELL_PER_ROW = 2;
    private static final int MIN_CELL_PER_COL = 2;

    private PointF topLeft;
    private PointF bottomRight;
    private float padding;
    private float cellWidth;
    private float cellHeight;
    private boolean shuffleRows = false;

    private Cell currCell;

    public Grid(int width, int height, int rows, int cols) {
        this.topLeft = new PointF(0, 0);
        this.bottomRight = new PointF(width, height);
        this.cellWidth = width / cols;
        this.cellHeight = height / rows;
        this.padding = -1 * RandomUtils.getRandomFloatInRange(cellWidth, cellWidth * 2);
        this.currCell = getStartingCell();
    }

    public Grid(PointF topLeft, PointF bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        setRandomRectangularCellDimensions();
        this.padding = RandomUtils.getRandomFloatInRange(cellWidth, cellWidth * 2);
    }

    public Grid withSquareCells() {
        setRandomSquareCellDimensions();
        return this;
    }

    public Grid withRandomShuffleRowsSetting() {
        this.shuffleRows = RandomUtils.random.nextBoolean();
        return this;
    }

    private PointF getTopLeftWithPadding() {
        return new PointF(topLeft.x - padding, topLeft.y - padding);
    }

    private PointF getBottomRightWithPadding() {
        return new PointF(bottomRight.x + padding, bottomRight.y + padding);
    }

    private void setRandomRectangularCellDimensions() {
        this.cellHeight = RandomUtils.getRandomFloatInRange(MIN_CELL_SIZE, getMaxCellHeight());
        this.cellWidth = RandomUtils.getRandomFloatInRange(MIN_CELL_SIZE, getMaxCellWidth());
    }

    private void setRandomSquareCellDimensions() {
        float maxSize = Math.min(getMaxCellHeight(), getMaxCellWidth());
        this.cellHeight = RandomUtils.getRandomFloatInRange(MIN_CELL_SIZE, maxSize);
        this.cellWidth = cellHeight;
    }

    private float getMaxCellHeight() {
        return MathUtils.getHeight(topLeft, bottomRight) / MIN_CELL_PER_COL;
    }

    private float getMaxCellWidth() {
        return MathUtils.getWidth(topLeft, bottomRight) / MIN_CELL_PER_ROW;
    }

    private Cell getStartingCell() {
        PointF topLeftWithPadding = getTopLeftWithPadding();
        return new Cell(topLeftWithPadding.x, topLeftWithPadding.y, this);
    }

    private float getRowShuffle() {
        return -1 * RandomUtils.getRandomFloatInRange(0, cellWidth);
    }

    @Override
    public boolean hasNext() {
        if (currCell == null) {
            return true;
        } else {
            boolean hasNext = currCell.y < getBottomRightWithPadding().y;
            if (!hasNext) {
                currCell = null;
            }
            return hasNext;
        }
    }

    @Override
    public Cell next() {
        if (currCell == null) {
            currCell = getStartingCell();
        } else {
            currCell = currCell.getNextCell();
        }
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
            if (nextX > grid.getBottomRightWithPadding().x) {
                nextX = grid.getTopLeftWithPadding().x;
                if (grid.shuffleRows) {
                    nextX += grid.getRowShuffle();
                }
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

        public PointF getBottomRight() {
            return new PointF(x + width, y + height);
        }

        @Override
        public String toString() {
            return "Cell p1: " + getTopLeft() + " & p2: " + getBottomRight();
        }
    }
}
