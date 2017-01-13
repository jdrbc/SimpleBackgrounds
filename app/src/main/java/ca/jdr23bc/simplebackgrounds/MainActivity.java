package ca.jdr23bc.simplebackgrounds;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import ca.jdr23bc.simplebackgrounds.Patterns.Tree;

public class MainActivity extends Activity {

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
//            PatternPainter pp = new PatternPainter(canvas);
//            pp.style = PatternPainter.Style.Dot_Grid;
//            pp.paint();

            Tree t = new Tree(canvas.getWidth(), canvas.getHeight());
            t.leafCount = 1000;
            t.minDist = 10;
            t.drawLeaves = true;
            t.grow();
            t.fillAndDraw(canvas);

//            Star s = new Star(canvas.getWidth(), canvas.getHeight());
//            s.fillAndDraw(canvas);

//            RandomDots rd = new RandomDots(canvas.getWidth(), canvas.getHeight());
//            rd.fillAndDraw(canvas);

//            Lines lines = new Lines(canvas.getWidth(), canvas.getHeight());
//            lines.fillAndDraw(canvas);

//            Grid grid = new Grid(canvas.getWidth(), canvas.getHeight());
//            grid.draw(canvas);

//            DotGrid dg = new DotGrid(canvas.getWidth(), canvas.getHeight());
//            dg.fillAndDraw(canvas);
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

                float x = e.getX();
                float y = e.getY();

                Log.d("Double Tap", "Tapped at: (" + x + "," + y + ")");

                return true;
            }
        }
    }
}