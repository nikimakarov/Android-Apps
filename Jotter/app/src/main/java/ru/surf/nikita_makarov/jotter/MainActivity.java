package ru.surf.nikita_makarov.jotter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public int fragmentId, fragmentColor, height;
    public FragmentManager manager = getSupportFragmentManager();
    public String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle onSaveInstanceState) {
        fragmentId = 0;
        fragmentColor = -14575885;
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        fragmentId += 1;
        Log.v(TAG, "ON_ACTIVITY_RESULT");
        String themeShowResult = data.getStringExtra("theme");
        Log.v(TAG, themeShowResult);
        String textShowResult = data.getStringExtra("text");
        Log.v(TAG, textShowResult);
        int fragmentColorResult = data.getIntExtra("color",0);
        Log.v(TAG, Integer.toString(fragmentColorResult));
        int fragmentIdResult = fragmentId;
        Log.v(TAG, Integer.toString(fragmentIdResult));
        MakeFragment(themeShowResult, textShowResult,
                fragmentColorResult, fragmentIdResult);
        Context context = getApplicationContext();
        CharSequence text = "Brand new note added!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 45);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        Intent intObj = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intObj, 0);
        return true;
    }

    public void MakeFragment(String theme, String text, int color, int id) {
        Log.v(TAG, "MakeFragment opening");
        Log.v(TAG, theme);
        Log.v(TAG, text);
        Log.v(TAG, Integer.toString(id));
        Log.v(TAG, Integer.toString(color));
        ScrollView scroll = (ScrollView) findViewById(R.id.scroll);
        height = scroll.getWidth() / 2 + 4;
        final Note fragmentInput = new Note();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("theme", theme);
        bundle.putString("text", text);
        bundle.putInt("color", color);
        bundle.putInt("height", height);
        fragmentInput.setArguments(bundle);
        if (id % 2 == 0) {
            manager.beginTransaction().add(R.id.grid_view_a, fragmentInput).commitAllowingStateLoss();
        } else {
            manager.beginTransaction().add(R.id.grid_view_b, fragmentInput).commitAllowingStateLoss();
        }
    }
}