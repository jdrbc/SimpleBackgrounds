package ca.jdr23bc.simplebackgrounds.layout;

public class SingleCellLayout extends Layout {
    private boolean hasNext;

    public SingleCellLayout() {
        hasNext = true;
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
