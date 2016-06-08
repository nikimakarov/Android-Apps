package ru.surf.nikita_makarov.jotter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import ru.surf.nikita_makarov.jotter.FeedReaderContract.FeedReaderDBHelper;

public class MainActivity extends AppCompatActivity {
    FeedReaderDBHelper dbHelper;
    public FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new FeedReaderDBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        MakeFragment(database);
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
        startActivity(intObj);
        return true;
    }

    public void MakeFragment(SQLiteDatabase data) {
        final Note fragmentInput = new Note();
        Cursor cursor = data.query(FeedReaderContract.FeedEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_ID);
            int themeIndex = cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_THEME);
            int textIndex = cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TEXT);
            Bundle bundle = new Bundle();
            bundle.putInt("id", cursor.getInt(idIndex));
            bundle.putString("theme", cursor.getString(themeIndex));
            bundle.putString("text", cursor.getString(textIndex));
            fragmentInput.setArguments(bundle);

            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", name = " + cursor.getString(themeIndex) +
                        ", email = " + cursor.getString(textIndex));
            } while (cursor.moveToNext());
            if (cursor.getInt(idIndex) % 2 == 0) {
                manager.beginTransaction().add(R.id.grid_view_a, fragmentInput).commit();
            } else {
                manager.beginTransaction().add(R.id.grid_view_b, fragmentInput).commit();
            }

        } else
            Log.d("mLog", "0 rows");
        cursor.close();
    }
}