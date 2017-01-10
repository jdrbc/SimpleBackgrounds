package ca.jdr23bc.simplebackgrounds.Patterns;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class Triangle {
    PointF p1;
    PointF p2;
    PointF p3;
    Triangle(PointF p1, PointF p2, PointF p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public void draw(Canvas canvas, Paint paint) {
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.lineTo(p1.x, p1.y);
        path.close();
        canvas.drawPath(path, paint);
    }
}
