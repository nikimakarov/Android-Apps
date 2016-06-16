package ru.surf.nikita_makarov.jotter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RemovalConfirmation.RemovalConfirmationListener {
    public FeedReaderContract.FeedReaderDBHelper dbHelper;
    public int fragmentId, fragmentColor, height, orientation = 0;
    public FragmentManager manager = getSupportFragmentManager();
    public String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle onSaveInstanceState) {
        fragmentColor = -14575885;
        Log.v("Main", "onCreate");
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.activity_main);
        orientation = getScreenOrientation();
        updateData();

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
        //Log.v(TAG, "ON_ACTIVITY_RESULT");
        String themeShowResult = data.getStringExtra("theme");
        Log.v(TAG, themeShowResult);
        String textShowResult = data.getStringExtra("text");
        //Log.v(TAG, textShowResult);
        int fragmentColorResult = data.getIntExtra("color", 0);
        //Log.v(TAG, Integer.toString(fragmentColorResult));
        int fragmentIdResult = fragmentId;
        //Log.v(TAG, Integer.toString(fragmentIdResult));
        String dateShowResult = data.getStringExtra("date");
        //Log.v(TAG, dateShowResult);
        dbHelper.pushNote(fragmentIdResult, themeShowResult, textShowResult,
                dateShowResult, fragmentColorResult);
        MakeFragment(themeShowResult, textShowResult,
                fragmentColorResult, fragmentIdResult, dateShowResult);
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
        if (item.getItemId() == R.id.add_button) {
            Intent intObj = new Intent(this, AddNoteActivity.class);
            startActivityForResult(intObj, 0);
        }
        if (item.getItemId() == R.id.delete_button) {
            DialogFragment dialog = new RemovalConfirmation();
            dialog.show(getSupportFragmentManager(), "RemovalConfirmation");
        }
        return true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.v("MAIN","updated");
        NoteInfo fragment = (NoteInfo) getSupportFragmentManager().findFragmentByTag("fragmentInfo");
        dbHelper.deleteNote(fragment.idIn);
        updateData();
        CharSequence text = "Note has been deleted.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 45);
        getSupportFragmentManager().beginTransaction()
                .remove(fragment).commit();
        toast.show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        CharSequence text = "Refusal. Note still exists.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 45);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("fragmentInfo") == null)
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                            System.exit(0);
                        }
                    }).create().show();
        else {
            MainActivity.super.onBackPressed();
        }
    }

    public void MakeFragment(String theme, String text, int color, int id, String date) {
        Log.v("MAIN","making fragment");
        //Log.v(TAG, "MakeFragment opening");
        //Log.v(TAG, theme);
        //Log.v(TAG, text);
        //Log.v(TAG, Integer.toString(id));
        //Log.v(TAG, Integer.toString(color));
        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        height = displaymetrics.widthPixels / 2 + 4;
        final Note fragmentInput = new Note();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("theme", theme);
        bundle.putString("text", text);
        bundle.putInt("color", color);
        bundle.putInt("height", height);
        bundle.putString("date", date);
        fragmentInput.setArguments(bundle);
        if (id % 2 == 0) {
            manager.beginTransaction().add(R.id.grid_view_b, fragmentInput).commitAllowingStateLoss();
        } else {
            manager.beginTransaction().add(R.id.grid_view_a, fragmentInput).commitAllowingStateLoss();
        }
    }

    public int getScreenOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return 1; // Portrait Mode
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 2;   // Landscape mode
        }
        return 0;
    }

    protected void updateData() {
        Log.v("MAIN","update data");
        if (getSupportFragmentManager().findFragmentByTag("Main") == null) {
            Log.v("MAIN","null");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment(), "Main")
                    .commit();
        } else {
            Log.v("MAIN","exists -> remove -> add");
            getSupportFragmentManager().beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentByTag("Main")).commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment(), "Main")
                    .commit();
        }
            dbHelper = new FeedReaderContract.FeedReaderDBHelper(this);
            List<NoteStruct> savedNotes = dbHelper.getAllNotes();
            for (NoteStruct savedNote : savedNotes) {
                fragmentId = savedNote.id;
                MakeFragment(savedNote.theme, savedNote.text, savedNote.color, savedNote.id, savedNote.date);
            }
    }


    private void clearStack() {
        int count = manager.getBackStackEntryCount();
        while (count > 0) {
            manager.popBackStack();
            count--;
        }
    }

}