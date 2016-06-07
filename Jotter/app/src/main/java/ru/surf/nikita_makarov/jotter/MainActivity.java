package ru.surf.nikita_makarov.jotter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.GridLayout;

    public class MainActivity extends AppCompatActivity {

        public String themeShow = "", textShow = "";
        public int fragmentId = 1;
        public FragmentManager manager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            manager = getSupportFragmentManager();
            if (savedInstanceState!=null) {
                themeShow = savedInstanceState.getString("theme");
                textShow = savedInstanceState.getString("text");
                fragmentId = savedInstanceState.getInt("id");
                if (fragmentId!=1)
                {
                    MakeFragment(themeShow, textShow, fragmentId);
                }
            }
            else
            {
                MakeFragment("Start","using Jotter now!",0);
                MakeFragment("Make", "your first note!",1);
            }

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
            fragmentId+=1;
            intObj.putExtra("id", fragmentId);
            startActivity(intObj);
            return true;
        }

        public void MakeFragment(String theme, String text, int id) {
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