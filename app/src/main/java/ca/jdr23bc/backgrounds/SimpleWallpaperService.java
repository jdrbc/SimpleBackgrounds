package ca.jdr23bc.backgrounds;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.lang.ref.WeakReference;

import ca.jdr23bc.backgrounds.backgrounds.Background;
import ca.jdr23bc.backgrounds.backgrounds.BackgroundFactory;
import ca.jdr23bc.backgrounds.backgrounds.preferences.BackgroundPreferencesAllDisabled;
import ca.jdr23bc.backgrounds.backgrounds.preferences.BackgroundPreferencesSharedPreferences;
import ca.jdr23bc.backgrounds.utils.MathUtils;

/**
 * TODO list
 *      - better & different trees
 *          - 'tree properties/dna class'
 *              - attractor bunching for savana, pine, oak ect
 *              - attractor density
 *              - branch length, width ect
 *              - branch & trunk tapering (1/2 width if is second child?)
 *              - leaf types (pine, long, fat, ect)
 *      - that 'wind vector' simple background
 *      - better color schemes
 *      - make use of simplex noise
 *      - grid layout on angle
 */
public class SimpleWallpaperService extends WallpaperService {
    private static final String TAG = SimpleWallpaperService.class.getCanonicalName();
    private static final int FPS = 60;

    @Override
    public Engine onCreateEngine() {
        return new SimpleWallpaperEngine();
    }

    private class SimpleWallpaperEngine extends Engine {
        private final GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureListener());
        BackgroundAnimation backgroundAnimation;

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            Log.i(TAG, "surface created");
            drawBackgroundOrStartNewBackgroundCreation();
        }

        @Override
        public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
            Log.i(TAG, "surface redraw needed");
            drawBackgroundOrStartNewBackgroundCreation();
        }

        @Override
        public void onDesiredSizeChanged(int desiredWidth, int desiredHeight) {
            Log.i(TAG, "desired size changed");
            drawBackgroundOrStartNewBackgroundCreation();
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            Log.d(TAG, "touch event");
            super.onTouchEvent(event);
            if (isVisible()) {
                gestureDetector.onTouchEvent(event);
            }
        }

        private void drawBackgroundOrStartNewBackgroundCreation() {
            Log.i(TAG, "drawing background or starting new background creation...");
            if (backgroundAnimation == null || !backgroundAnimation.isValid()) {
                Log.i(TAG, "...background animation not defined, finished animating or lost " +
                        "reference to background");
                startNewBackgroundCreation();
            } else {
                Log.d(TAG, "...background animation in progress - drawing background");
                backgroundAnimation.draw();
            }
        }

        private void startNewBackgroundCreation() {
            Log.i(TAG, "starting new background creation!");
            if (backgroundAnimation != null) {
                Log.i(TAG, "stopping old background animation");
                backgroundAnimation.stop();
                backgroundAnimation.freeMemory();
            }

            Log.i(TAG, "creating new background");
            Background background = getBackgroundFactory().getRandomBackground();
            Log.i(TAG, "new background created");

            Log.i(TAG, "creating new background animation");
            backgroundAnimation = new BackgroundAnimation(background, getSurfaceHolder());
            backgroundAnimation.start();
            Log.i(TAG, "new background animation created & started");
        }

        private BackgroundFactory getBackgroundFactory() {
            try {
                BackgroundPreferencesSharedPreferences preferences = new BackgroundPreferencesSharedPreferences(
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                );
                return new BackgroundFactory(preferences);
            } catch (Exception e) {
                Log.w(TAG, "exception thrown when creating background factory: " + e);
                return new BackgroundFactory(new BackgroundPreferencesAllDisabled());
            }
        }

        private class GestureListener extends GestureDetector.SimpleOnGestureListener {
            // event when double tap occurs
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                startNewBackgroundCreation();
                return true;
            }
        }
    }

    private static Integer backgroundAnimationCount = 0;
    static class BackgroundAnimation extends Handler implements Runnable, SurfaceHolder.Callback {
        final Background background;
        final int delay;
        final Integer animationNumber;
        final String logTag;
        WeakReference<SurfaceHolder> holder;

        BackgroundAnimation(Background background, SurfaceHolder holder) {
            this.delay = Math.round(MathUtils.getMillisecondsBetweenFrames(FPS));
            this.background = background;
            this.holder = new WeakReference<> (holder);
            this.animationNumber = backgroundAnimationCount++;
            this.logTag = BackgroundAnimation.class.getCanonicalName() + "-" + animationNumber;
            holder.addCallback(this);
        }

        void start() {
            Log.d(logTag, "start! initializing " + background.toString());
            background.init();
            post(this);
        }

        void stop() {
            Log.i(logTag, "stop!");
            removeCallbacks(this);
        }

        void freeMemory() {
            Log.i(logTag, "freeing memory!");
            background.freeMemory();
        }

        @Override
        public void run() {
            Log.v(logTag, "run!");
            background.drawStep();
            draw();

            if (!background.hasNextDrawStep()) {
                Log.i(TAG, "all draw steps complete");
                stop();
            } else if (holder.get() == null) {
                Log.i(TAG, "lost reference to surface");
                stop();
            } else {
                postDelayed(this, delay);
            }
        }

        /**
         * @return True if this animation has a valid background image to draw
         */
        Boolean isValid() {
            return holder.get() != null;
        }

        void draw() {
            // Check if holder has been gc'd
            SurfaceHolder strongReferenceToHolder = holder.get();
            if (strongReferenceToHolder != null) {
                draw(strongReferenceToHolder);
            } else {
                Log.e(logTag, "lost reference to the surface holder! Stopping");
                stop();
            }
        }

        private void draw(SurfaceHolder holder) {
            Log.v(logTag, "draw!");
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
            Log.i(TAG, "surface created");
            holder = new WeakReference<>(surfaceHolder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            Log.i(TAG, "surface changed");
            holder = new WeakReference<>(surfaceHolder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i(TAG, "surface destroyed");
            stop();
        }
    }
}
