package ca.jdr23bc.backgrounds.backgrounds;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Background {
    private Bitmap bitmap;
    private Canvas canvas;

    Background(int width, int height) {
        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bitmap);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public abstract void init();

    public abstract Boolean hasNextDrawStep();

    public abstract void drawStep();

    public void freeMemory() {
        bitmap = null;
        canvas = null;
        System.gc();
    }

    Canvas getCanvas() { return canvas; }

    int getWidth() {
        return bitmap.getWidth();
    }

    int getHeight() {
        return bitmap.getHeight();
    }
}
