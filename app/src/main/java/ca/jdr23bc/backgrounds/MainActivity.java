package ca.jdr23bc.backgrounds;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchIntentToSetBackground(View view) {
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
    }

    public void startPreferences(View view) {
        Intent intent = new Intent(MainActivity.this,PrefsActivity.class);
        startActivity(intent);
    }

    public void launchIntentToPickWallpapers(View view) {
        Intent i = new Intent(this, WallpaperPicker.class);
        startActivity(i);
    }

    // Quit once the user has set the background
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO display a 'success' toast
    }
}