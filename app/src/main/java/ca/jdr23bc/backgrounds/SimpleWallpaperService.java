package ca.jdr23bc.backgrounds;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.service.wallpaper.WallpaperService;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import ca.jdr23bc.backgrounds.backgrounds.BackgroundFactory;

public class SimpleWallpaperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new SimpleWallpaperEngine();
    }

    private class SimpleWallpaperEngine extends Engine {
        private GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureListener());

        public SimpleWallpaperEngine() {
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            draw(holder);
        }

        @Override
        public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
            draw(holder);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            super.onTouchEvent(event);
        }

        private void draw() {
            draw(getSurfaceHolder());
        }

        private void draw(SurfaceHolder holder) {
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                new BackgroundFactory().getRandomBackground().fill(canvas);
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
        }

        private class GestureListener extends GestureDetector.SimpleOnGestureListener {
            // event when double tap occurs
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                draw();
                return true;
            }
        }
    }
}
