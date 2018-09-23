package ca.jdr23bc.backgrounds;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
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
        // for development
//        setContentView(new BackgroundView(this));
    }

    @Override
    public void onStart() {
        super.onStart();
        launchIntentToSetBackground();
    }

    @Override
    public void onResume() {
        super.onResume();
        closeActivity();
    }

    private void launchIntentToSetBackground() {
        Intent i = new Intent();

        if(Build.VERSION.SDK_INT > 15){
            i.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);

            String p = SimpleWallpaperService.class.getPackage().getName();
            String c = SimpleWallpaperService.class.getCanonicalName();
            i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(p, c));
        }
        else{
            i.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        }
        startActivityForResult(i, 1);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    // Quit once the user has set the background
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        closeActivity();
    }

    private void closeActivity() {
        Intent intent1 = new Intent(Intent.ACTION_MAIN);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent1);
    }

    private static final int LOADING_ANIMATION_STEP_LENGTH_MS = 5;
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
            Background background = new BackgroundFactory().getMultiTreeBackground(canvas.getWidth(), canvas.getHeight());
//            Background background = new RectBackground(canvas.getWidth(), canvas.getHeight(), new GridLayout());
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

            BackgroundBuilder(BackgroundView view, int delay, Background background) {
                this.view = view;
                this.delay = delay;
                this.background = background;
            }

            void start() {
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

            GestureListener(View v) {
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