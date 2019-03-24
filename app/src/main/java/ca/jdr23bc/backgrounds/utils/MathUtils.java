package ca.jdr23bc.backgrounds.utils;

import android.annotation.SuppressLint;
import android.graphics.PointF;

@SuppressWarnings("unused")
public class MathUtils {
    public static Double getDist(PointF p1, PointF p2) { return Math.hypot(p1.x - p2.x, p1.y - p2.y); }

    public static Float getDistF(PointF p1, PointF p2) { return (float) Math.hypot(p1.x - p2.x, p1.y - p2.y); }

    public static PointF add(PointF p1, PointF p2) { return new PointF(p1.x + p2.x, p1.y + p2.y); }

    public static PointF sub(PointF p1, PointF p2) { return new PointF(p1.x - p2.x, p1.y - p2.y); }

    public static PointF mult(PointF p1, int length) { return new PointF(p1.x * length, p1.y * length); }

    @SuppressLint("UseValueOf")
    public static PointF mult(PointF p1, double length) { //noinspection UnnecessaryBoxing,UnnecessaryBoxing,UnnecessaryBoxing,UnnecessaryBoxing,UnnecessaryBoxing
        return new PointF(new Float(p1.x * length), new Float(p1.y * length)); }

    @SuppressLint("UseValueOf")
    public static PointF normalize(PointF p) {
        Double len = MathUtils.getDist(p, new PointF(0, 0));
        //noinspection UnnecessaryBoxing,UnnecessaryBoxing
        return new PointF(new Float(p.x / len), new Float(p.y / len));
    }

    @SuppressWarnings("unused")
    public static float getMagnitude(PointF p) {
        return (float) Math.hypot(p.x, p.y);
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

    public static float getMillisecondsBetweenFrames(int fps) {
        return (1.0f / fps) * 1000;
    }
}
