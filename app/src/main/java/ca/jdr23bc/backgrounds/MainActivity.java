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
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        // launchIntentToSetBackground();
    }

    @Override
    public void onResume() {
        super.onResume();
        // closeActivity();
    }

    private void launchIntentToSetBackground() {
        Intent i = new Intent();

        if(Build.VERSION.SDK_INT > 15){
            i.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);

            String p = SimpleWallpaperService.class.getPackage().getName();
            String c = SimpleWallpaperService.class.getCanonicalName();
            i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(p, c));
        } else {
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
}