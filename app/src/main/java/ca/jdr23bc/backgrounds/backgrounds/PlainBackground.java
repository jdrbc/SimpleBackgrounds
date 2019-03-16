package ca.jdr23bc.backgrounds.backgrounds;

import android.graphics.Color;
import android.graphics.Paint;

import ca.jdr23bc.backgrounds.utils.RandomUtils;

public class PlainBackground extends Background {
    PlainBackground(int width, int height) {
        super(width, height);
    }

    @Override
    public void init() {
        int rootColor = Color.rgb(
        RandomUtils.random.nextInt(256),
        RandomUtils.random.nextInt(256),
        RandomUtils.random.nextInt(256));
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(rootColor);
        paint.setAntiAlias(true);
        getCanvas().drawPaint(paint);
    }

    @Override
    public Boolean hasNextDrawStep() {
        return false;
    }

    @Override
    public void drawStep() { }
}
