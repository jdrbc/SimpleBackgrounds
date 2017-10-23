package ca.jdr23bc.backgrounds.layout;


import android.graphics.PointF;

public class Cell {
    private PointF topLeft;
    private PointF bottomRight;

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
