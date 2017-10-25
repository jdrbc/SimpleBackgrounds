package ca.jdr23bc.backgrounds;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import ca.jdr23bc.backgrounds.backgrounds.Background;
import ca.jdr23bc.backgrounds.backgrounds.BackgroundFactory;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BackgroundView(this));
    }

    private static final int LOADING_ANIMATION_STEP_LENGTH_MS = 10;
    public class BackgroundView extends View {
        GestureDetector gestureDetector;
        Background currentBackground;
        Boolean processing;
        Boolean doNotStartBackgroundCreation;
        BackgroundBuilder backgroundBuilder;

        public BackgroundView(Context context) {
            super(context);
            processing = false;
            doNotStartBackgroundCreation = false;
            gestureDetector = new GestureDetector(context, new GestureListener(this));
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            return gestureDetector.onTouchEvent(e);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Log.d(TAG, "processing : " + processing);

            if (!processing && !doNotStartBackgroundCreation) {
                currentBackground = startBackgroundCreation(canvas);
                processing = true;
            }
            doNotStartBackgroundCreation = false;
            canvas.drawBitmap(currentBackground.getBitmap(), new Matrix(), null);
        }

        private Background startBackgroundCreation(Canvas canvas) {
            Background background = new BackgroundFactory()
                    .getRandomBackground(canvas.getWidth(), canvas.getHeight());
            Log.d(TAG, "drawing: " + background.toString());
            backgroundBuilder = new BackgroundBuilder(this, LOADING_ANIMATION_STEP_LENGTH_MS, background);
            backgroundBuilder.start();
            return background;
        }

        public void onBackgroundCreationComplete() {
            processing = false;
            doNotStartBackgroundCreation = true;
            this.invalidate();
        }

        private class BackgroundBuilder extends Handler implements Runnable {
            BackgroundView view;
            Background background;
            int delay;

            public BackgroundBuilder(BackgroundView view, int delay, Background background) {
                this.view = view;
                this.delay = delay;
                this.background = background;
            }

            public void start() {
                background.init();
                post(this);
            }

            @Override
            public void run() {
                if (background.hasNextDrawStep()) {
                    background.drawStep();
                    view.invalidate();
                    postDelayed(this, delay);
                } else {
                    view.onBackgroundCreationComplete();
                }
            }
        }

        private class GestureListener extends GestureDetector.SimpleOnGestureListener {
            View v;

            public GestureListener(View v) {
                this.v = v;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                v.invalidate();
                return true;
            }
        }
    }
}