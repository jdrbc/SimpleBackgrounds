package ca.jdr23bc.backgrounds.layout;


import android.graphics.PointF;

import java.util.Iterator;

import ca.jdr23bc.backgrounds.utils.MathUtils;

public abstract class Layout implements Iterator<Cell> {
    private PointF topLeft;
    private PointF bottomRight;
    private Boolean initCalled;

    public Layout() {
        initCalled = false;
    }

    public void init(PointF topLeft, PointF bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        initCalled = true;
    }

    @Override
    public boolean hasNext() {
        boolean finished = finished();
        return initCalled && !finished;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public abstract Integer getNumberOfCells();

    protected abstract boolean finished();

    protected PointF getTopLeft() {
        return topLeft;
    }

    protected PointF getBottomRight() {
        return bottomRight;
    }

    protected float getWidth() {
        return MathUtils.getWidth(getTopLeft(), getBottomRight());
    }

    protected float getHeight() {
        return MathUtils.getHeight(getTopLeft(), getBottomRight());
    }
}
