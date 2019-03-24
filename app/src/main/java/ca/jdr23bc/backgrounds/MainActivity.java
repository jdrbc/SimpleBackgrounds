package ca.jdr23bc.backgrounds;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchIntentToSetBackground(View view) {
        Intent i = new Intent();

        if(Build.VERSION.SDK_INT > 15){
            i.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            //noinspection ConstantConditions
            String p = SimpleWallpaperService.class.getPackage().getName();
            String c = SimpleWallpaperService.class.getCanonicalName();
            assert c != null;
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

    // Quit once the user has set the background
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO display a 'success' toast
    }
}