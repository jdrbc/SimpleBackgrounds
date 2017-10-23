package ca.jdr23bc.backgrounds.utils;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class MathUtils {

    public static float getWidth(PointF left, PointF right) {
        return right.x - left.x;
    }
    public static float getHeight(PointF top, PointF bottom) {
        return bottom.y - top.y;
    }
    public static PointF getPointBetween(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }
    public static List<Integer> getCommonDivisors(int num1, int num2) {
        List<Integer> list = new ArrayList<>();
        int min = Math.min(num1, num2);
        for(int i = 1; i <= min / 2; i++) {
            if (num1 % i == 0 && num2 % i == 0) {
                list.add(i);
            }
        }

        if (num1 % min == 0 && num2 % min == 0) {
            list.add(min);
        }

        return list;
    }
}
