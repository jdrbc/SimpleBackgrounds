package ca.jdr23bc.backgrounds.backgrounds;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Background {
    private Bitmap bitmap;
    private Canvas canvas;

    public Background(int width, int height) {
        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bitmap);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Bitmap create() {
        init();
        while (hasNextDrawStep()) {
            drawStep();
        }
        return bitmap;
    }

    public abstract void init();

    public abstract Boolean hasNextDrawStep();

    public abstract void drawStep();

    protected Canvas getCanvas() { return canvas; }

    protected int getWidth() {
        return bitmap.getWidth();
    }

    protected int getHeight() {
        return bitmap.getHeight();
    }
}
