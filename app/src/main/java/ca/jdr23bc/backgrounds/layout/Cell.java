package ca.jdr23bc.backgrounds.layout;


import android.graphics.PointF;

public class Cell {
    private final PointF topLeft;
    private final PointF bottomRight;

    public Cell(PointF topLeft, PointF bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public PointF getTopLeft() {
        return topLeft;
    }

    public PointF getBottomRight() {
        return bottomRight;
    }
}
