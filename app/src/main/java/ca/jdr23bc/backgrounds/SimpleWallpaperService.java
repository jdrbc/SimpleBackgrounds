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

import java.lang.ref.WeakReference;

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
            Background background = new BackgroundFactory().getRandomBackground(
                    getDesiredMinimumWidth(), getDesiredMinimumHeight());
            backgroundAnimation = new BackgroundAnimation(24, background, getSurfaceHolder());
            backgroundAnimation.start();
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

    static class BackgroundAnimation extends Handler implements Runnable, SurfaceHolder.Callback {
        Background background;
        int delay;
        WeakReference<SurfaceHolder> holder;

        BackgroundAnimation(int fps, Background background, SurfaceHolder holder) {
            this.delay = Math.round(MathUtils.getMillisecondsBetweenFrames(fps));
            this.background = background;
            this.holder = new WeakReference<> (holder);
            holder.addCallback(this);
        }

        void start() {
            Log.d(TAG, "start!");
            background.init();
            post(this);
        }

        void stop() {
            Log.d(TAG, "stop!");
            removeCallbacks(this);
            background.freeMemory();
        }

        @Override
        public void run() {
            Log.d(TAG, "run!");
            background.drawStep();
            // Check if holder has been gc'd
            SurfaceHolder strongReferenceToHolder = holder.get();
            if (strongReferenceToHolder != null) {
                draw(strongReferenceToHolder);
                if (background.hasNextDrawStep()) {
                    postDelayed(this, delay);
                }
            } else {
                stop();
            }
        }

        private void draw(SurfaceHolder holder) {
            Log.d(TAG, "draw!");
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    canvas.drawBitmap(background.getBitmap(), new Matrix(), null);
                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            stop();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            stop();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            stop();
        }
    }
}
