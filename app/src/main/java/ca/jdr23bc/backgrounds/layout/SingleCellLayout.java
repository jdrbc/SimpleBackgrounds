package ca.jdr23bc.backgrounds.layout;

public class SingleCellLayout extends Layout {
    private boolean hasNext;

    public SingleCellLayout() {
        hasNext = true;
    }

    @Override
    public Integer getNumberOfCells() {
        return 1;
    }

    @Override
    public boolean finished() {
        return !hasNext;
    }

    @Override
    public Cell next() {
        hasNext = false;
        return new Cell(getTopLeft(), getBottomRight());
    }
}
