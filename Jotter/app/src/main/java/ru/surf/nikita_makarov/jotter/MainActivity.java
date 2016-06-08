package ru.surf.nikita_makarov.jotter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import ru.surf.nikita_makarov.jotter.FeedReaderContract.FeedReaderDBHelper;

    public class MainActivity extends AppCompatActivity {
        //FeedReaderDBHelper dbHelper;
        public int fragmentId;
        public FragmentManager manager = getSupportFragmentManager();
        public String TAG = "MainActivity";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                String themeShow, textShow;
            if (savedInstanceState!=null) {
                themeShow = savedInstanceState.getString("theme");
                textShow = savedInstanceState.getString("text");
                fragmentId = savedInstanceState.getInt("id");
            }
            else{
                fragmentId = 0;
                themeShow = "Start";
                textShow = "using Jotter now!";
            }
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                //dbHelper = new FeedReaderDBHelper(this);
                MakeFragment(themeShow, textShow, fragmentId);
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
            fragmentId = fragmentId+1;
            intObj.putExtra("id", fragmentId);
            startActivity(intObj);
            return true;
        }

        public void MakeFragment(String theme, String text, int id) {
            Log.v(TAG, "MakeFragment opening");
            Log.v(TAG, theme);
            Log.v(TAG, text);
            Log.v(TAG, Integer.toString(id));
            final Note fragmentInput = new Note();
            Bundle bundle = new Bundle();
            bundle.putInt("id", id);
            bundle.putString("theme", theme);
            bundle.putString("text", text);
            fragmentInput.setArguments(bundle);
            if (id%2==0){
                manager.beginTransaction().add(R.id.grid_view_a, fragmentInput).commit();
            }
            else{
                manager.beginTransaction().add(R.id.grid_view_b, fragmentInput).commit();
            }
        }
    }