package ru.surf.nikita_makarov.jotter;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity {

        String th = "", tx = "";
        public int x = 0;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            if (savedInstanceState!=null) {
                th = savedInstanceState.getString("theme");
                tx = savedInstanceState.getString("text");
                MakeFragment(th,tx);
            }
            else
            {
                final FragmentManager fm = getSupportFragmentManager();
                final Note fragment1 = new Note();
                fm.beginTransaction().add(R.id.grid_view_a, fragment1).commit();
                final Note fragment2 = new Note();
                fm.beginTransaction().add(R.id.grid_view_b, fragment2).commit();
            }

            GridLayout grid1 = (GridLayout) findViewById(R.id.grid_view_a);
            GridLayout grid2 = (GridLayout) findViewById(R.id.grid_view_b);

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.add, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(android.view.MenuItem item)
        {
            final int itemId = item.getItemId();
            Intent intObj = new Intent(this, AddNoteActivity.class);
            intObj.putExtra("id", x);
            startActivity(intObj);
            return true;
        }

        public void MakeFragment(String th1, String tx1) {
            final FragmentManager manager = getSupportFragmentManager();
            final Note fragment1 = new Note();
            fragment1.txt.setText(th1);
            fragment1.txt2.setText(tx1);
            manager.beginTransaction().add(R.id.grid_view_a, fragment1).commit();
        }
    }