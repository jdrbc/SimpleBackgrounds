package ca.jdr23bc.simplebackgrounds;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import ca.jdr23bc.simplebackgrounds.backgrounds.Background;
import ca.jdr23bc.simplebackgrounds.backgrounds.BackgroundFactory;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getCanonicalName();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    public class MyView extends View {
        GestureDetector gestureDetector;

        public MyView(Context context) {
            super(context);
            gestureDetector = new GestureDetector(context, new GestureListener(this));
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            return gestureDetector.onTouchEvent(e);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Background background = new BackgroundFactory().getRandomBackground();
            background.fill(canvas);
            Log.d(TAG, "drew background: " + background.toString());
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