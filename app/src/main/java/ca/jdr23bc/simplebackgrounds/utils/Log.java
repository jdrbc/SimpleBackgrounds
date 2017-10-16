package ca.jdr23bc.simplebackgrounds.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ca.jdr23bc.simplebackgrounds.R;

public class Log extends AppCompatActivity {

    private static String TAG = "LOG";
    private static ArrayList<String> backgrounds = new ArrayList<>();
    private static ListView backgroundsListView;
    private static ArrayAdapter<String> adapter;

    public static void newBackground(String background) {
        backgrounds.add(background);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        backgroundsListView = (ListView) findViewById(R.id.backgrounds);

        android.util.Log.d(TAG, "backgrounds: " + backgrounds);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, backgrounds);

        // Assign adapter to ListView
        backgroundsListView.setAdapter(adapter);
    }
}
