package ca.jdr23bc.simplebackgrounds.layout;


import android.graphics.PointF;

import java.util.Iterator;

public abstract class Layout implements Iterator<Cell> {
    private PointF topLeft;
    private PointF bottomRight;
    private Boolean initCalled;

    public void Layout() {
        initCalled = false;
    }

    public void init(PointF topLeft, PointF bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        initCalled = true;
    }

    @Override
    public boolean hasNext() {
        return initCalled && finished();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    protected abstract boolean finished();

    protected PointF getTopLeft() {
        return topLeft;
    }

    protected PointF getBottomRight() {
        return bottomRight;
    }
}
