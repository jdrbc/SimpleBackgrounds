package ca.jdr23bc.backgrounds;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import ca.jdr23bc.backgrounds.backgrounds.Background;
import ca.jdr23bc.backgrounds.backgrounds.BackgroundFactory;
import ca.jdr23bc.backgrounds.utils.MathUtils;

public class SimpleWallpaperService extends WallpaperService {
    private static final String TAG = SimpleWallpaperService.class.getCanonicalName();

    @Override
    public Engine onCreateEngine() {
        return new SimpleWallpaperEngine();
    }

    private class SimpleWallpaperEngine extends Engine {
        private GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureListener());
        BackgroundAnimation backgroundAnimation;

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            startBackgroundCreation();
        }

        @Override
        public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
            startBackgroundCreation();
        }

        @Override
        public void onDesiredSizeChanged(int desiredWidth, int desiredHeight) {
            startBackgroundCreation();
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            if (isVisible()) {
                gestureDetector.onTouchEvent(event);
            }
        }

        private void startBackgroundCreation() {
            if (backgroundAnimation != null) {
                backgroundAnimation.stop();
            }
            Log.d(TAG, "start creation!");
            backgroundAnimation = new BackgroundAnimation(24);
            backgroundAnimation.start();
        }

        private class BackgroundAnimation extends Handler implements Runnable {
            Background background;
            int delay;
            boolean initCalled;

            public BackgroundAnimation(int fps) {
                this.delay = Math.round(MathUtils.getMillisecondsBetweenFrames(fps));
                this.background = new BackgroundFactory().getRandomBackground(
                        getDesiredMinimumWidth(), getDesiredMinimumHeight());
                initCalled = false;
            }

            public void start() {
                Log.d(TAG, "start!");
                background.init();
                post(this);
            }

            public void stop() {
                Log.d(TAG, "stop!");
                removeCallbacks(this);
            }

            @Override
            public void run() {
                Log.d(TAG, "run!");
                background.drawStep();
                draw();
                if (background.hasNextDrawStep()) {
                    postDelayed(this, delay);
                }
            }

            private void draw() {
                Log.d(TAG, "draw!");
                SurfaceHolder holder = getSurfaceHolder();
                Canvas canvas = null;
                try {
                    canvas = holder.lockCanvas();
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    canvas.drawBitmap(background.getBitmap(), new Matrix(), null);
                } finally {
                    if (canvas != null)
                        holder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private class GestureListener extends GestureDetector.SimpleOnGestureListener {
            // event when double tap occurs
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                startBackgroundCreation();
                return true;
            }
        }
    }
}
