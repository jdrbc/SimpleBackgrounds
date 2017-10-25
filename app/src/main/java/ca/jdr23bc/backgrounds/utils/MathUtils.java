package ca.jdr23bc.backgrounds.utils;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class MathUtils {
    public static Double getDist(PointF p1, PointF p2) { return Math.hypot(p1.x - p2.x, p1.y - p2.y); }

    public static PointF add(PointF p1, PointF p2) { return new PointF(p1.x + p2.x, p1.y + p2.y); }

    public static PointF sub(PointF p1, PointF p2) { return new PointF(p1.x - p2.x, p1.y - p2.y); }

    public static PointF mult(PointF p1, int length) { return new PointF(p1.x * length, p1.y * length); }

    public static PointF mult(PointF p1, double length) { return new PointF(new Float(p1.x * length), new Float(p1.y * length)); }

    public static PointF normalize(PointF p) {
        Double len = MathUtils.getDist(p, new PointF(0, 0));
        return new PointF(new Float(p.x / len), new Float(p.y / len));
    }

    public static float getWidth(PointF left, PointF right) {
        return right.x - left.x;
    }

    public static float getHeight(PointF top, PointF bottom) {
        return bottom.y - top.y;
    }

    public static PointF getPointBetween(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }
}
