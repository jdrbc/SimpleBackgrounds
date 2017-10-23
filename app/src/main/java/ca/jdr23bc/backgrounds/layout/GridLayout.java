package ca.jdr23bc.backgrounds.layout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import ca.jdr23bc.backgrounds.utils.MathUtils;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class GridLayout extends Layout {
    private static final float MIN_CELL_SIZE = 10f;
    private static final int MIN_CELL_PER_ROW = 2;
    private static final int MIN_CELL_PER_COL = 2;

    private float padding;
    private float cellWidth;
    private float cellHeight;
    private boolean squareCellsActive = false;
    private boolean cellOverlapActive = false;
    private boolean rowSkewActive = false;
    private boolean shuffledRowsActive = false;
    private float rowSkew;
    private float cellOverlap;
    private GridCell currCell;

    public GridLayout withSquareCellsActive(Boolean active) {
        this.squareCellsActive = active;
        return this;
    }

    public GridLayout withCellOverlapActive(boolean active) {
        this.cellOverlapActive = active;
        return this;
    }

    public GridLayout withShuffledRowsActive(Boolean active) {
        this.shuffledRowsActive = active;
        return this;
    }

    public GridLayout withRowSkewActive(Boolean rowSkewActive) {
        this.rowSkewActive = rowSkewActive;
        return this;
    }

    @Override
    public void init(PointF topLeft, PointF bottomRight) {
        super.init(topLeft, bottomRight);
        if (squareCellsActive) {
            setRandomSquareCellDimensions();
        } else {
            setRandomRectangularCellDimensions();
        }
        if (cellOverlapActive) {
            cellOverlap = RandomUtils.getRandomFloatInRange(0, cellWidth / 2);
        }
        if (rowSkewActive) {
            rowSkew = RandomUtils.getRandomFloatInRange(-cellWidth, cellWidth);
        }
        this.padding = RandomUtils.getRandomFloatInRange(cellWidth, cellWidth * 2);
    }

    @Override
    public boolean finished() {
        if (currCell == null) {
            return false;
        } else {
            boolean finished = currCell.y >= getBottomRightWithPadding().y;
            if (finished) {
                currCell = null;
            }
            return finished;
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

    @Override
    public String toString() {
        return "GridLayout: " + "{ " +
                "padding: " + padding + ", " +
                "cellWidth: " + cellWidth + ", " +
                "cellHeight:  " + cellHeight + ", " +
                "cellOverlapActive:  " + cellOverlapActive + ", " +
                "rowSkewActive:  " + rowSkewActive + ", " +
                "shuffledRowsActive:  " + shuffledRowsActive + ", " +
                "rowSkew:  " + rowSkew + ", " +
                "cellOverlap:  " + cellOverlap + " }";
    }

    private PointF getTopLeftWithPadding() {
        return new PointF(getTopLeft().x - padding, getTopLeft().y - padding);
    }

    private PointF getBottomRightWithPadding() {
        return new PointF(getBottomRight().x + padding, getBottomRight().y + padding);
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
        return MathUtils.getHeight(getTopLeft(), getBottomRight()) / MIN_CELL_PER_COL;
    }

    private float getMaxCellWidth() {
        return MathUtils.getWidth(getTopLeft(), getBottomRight()) / MIN_CELL_PER_ROW;
    }

    private GridCell getStartingCell() {
        PointF topLeftWithPadding = getTopLeftWithPadding();
        return new GridCell(topLeftWithPadding.x, topLeftWithPadding.y, this);
    }

    private Boolean isCellOverlapActive() {
        return cellOverlapActive;
    }

    private float getRowSkew(int rowNumber) {
        if (shuffledRowsActive) {
            return -1 * RandomUtils.getRandomFloatInRange(0, cellWidth);
        } else if (rowSkewActive) {
            return rowSkew * (rowNumber / getNumberOfRows());
        } else {
            return 0;
        }
    }

    private int getNumberOfRows() {
        return (int) (getBottomRightWithPadding().y / cellHeight);
    }

    private float getCellOverlap() {
        return cellOverlap;
    }

    public class GridCell extends Cell {
        GridLayout grid;
        float width;
        float height;
        float x;
        float y;

        public GridCell(float x, float y, GridLayout grid) {
            super(new PointF(x, y), new PointF(x + grid.cellWidth, y + grid.cellHeight));
            this.grid = grid;
            this.x = x;
            this.y = y;
            this.width = grid.cellWidth;
            this.height = grid.cellHeight;
        }

        public GridCell getNextCell() {
            float nextX = getNextX();
            float nextY = y;
            if (nextX > grid.getBottomRightWithPadding().x) {
                nextX = grid.getTopLeftWithPadding().x + getRowSkew(getRow());
                nextY += height;
            }
            return new GridCell(nextX, nextY, grid);
        }

        public int getRow() {
            return (int) (y / cellHeight);
        }

        public int getCol() {
            return (int) (x / cellWidth);
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

        private float getNextX() {
            if (grid.isCellOverlapActive()) {
                return x + width - getCellOverlap();
            } else {
                return x + width;
            }
        }

        @Override
        public String toString() {
            return "Cell p1: " + getTopLeft() + " & p2: " + getBottomRight();
        }
    }
}
