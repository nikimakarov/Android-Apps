package ru.surf.nikita_makarov.jotter.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import java.util.List;

import ru.surf.nikita_makarov.jotter.util.FeedReaderContract;
import ru.surf.nikita_makarov.jotter.fragment.MainFragment;
import ru.surf.nikita_makarov.jotter.util.Note;
import ru.surf.nikita_makarov.jotter.fragment.NoteFragment;
import ru.surf.nikita_makarov.jotter.fragment.NoteInfoFragment;
import ru.surf.nikita_makarov.jotter.R;
import ru.surf.nikita_makarov.jotter.view.RemovalConfirmation;

public class MainActivity extends AppCompatActivity implements RemovalConfirmation.RemovalConfirmationListener {
    public FeedReaderContract.FeedReaderDBHelper dbHelper;
    public static final String themeString = "theme";
    public static final String textString = "text";
    public static final String colorString = "color";
    public static final String dateString = "date";
    public static final String idString = "id";
    public static final String removal = "RemovalConfirmation";
    public static final String fragmentInfo= "fragmentInfo";
    public static final String main = "Main";
    public FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle onSaveInstanceState) {
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.activity_main);
        updateData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 & resultCode == 1) {
            if (data == null) {
                return;
            }
            String themeShowResult = data.getStringExtra(themeString);
            String textShowResult = data.getStringExtra(textString);
            int fragmentColorResult = data.getIntExtra(colorString, 0);
            String dateShowResult = data.getStringExtra(dateString);
            dbHelper.pushNote(themeShowResult, textShowResult, dateShowResult, fragmentColorResult);
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.NoteAdd);
            toastShow(context, text);
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            updateData();
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
        if (item.getItemId() == R.id.add_button) {
            Intent intObj = new Intent(this, AddNoteActivity.class);
            startActivityForResult(intObj, 0);
        }
        if (item.getItemId() == R.id.delete_button) {
            DialogFragment dialog = new RemovalConfirmation();
            dialog.show(getSupportFragmentManager(), removal);
        }
        return true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        NoteInfoFragment fragment = (NoteInfoFragment) getSupportFragmentManager().findFragmentByTag(fragmentInfo);
        dbHelper.deleteNote(fragment.id);
        updateData();
        CharSequence text = getString(R.string.NoteDelete);
        getSupportFragmentManager().beginTransaction()
                .remove(fragment).commit();
        toastShow(this, text);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        CharSequence text = getString(R.string.Refusal);
        toastShow(this, text);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(fragmentInfo) == null){
             finish();
        }
        else {
            MainActivity.super.onBackPressed();
        }
    }

    public void makeFragment(String theme, String text, int color, long id, String date) {
        final NoteFragment fragmentInput = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(themeString, theme);
        bundle.putString(textString, text);
        bundle.putInt(colorString, color);
        bundle.putString(dateString, date);
        bundle.putLong(idString, id);
        fragmentInput.setArguments(bundle);
        if (!isTablet()){
            if (id % 2 != 0) {
                fragmentManager.beginTransaction().add(R.id.grid_view_b, fragmentInput).commit();
            } else {
                fragmentManager.beginTransaction().add(R.id.grid_view_a, fragmentInput).commit();
            }
        } else {
                fragmentManager.beginTransaction().add(R.id.grid_view_c, fragmentInput).commit();
        }
    }

    protected void updateData() {
        addNewFragment();
        fillNewFragment();
    }

    public void addNewFragment() {
        if (getSupportFragmentManager().findFragmentByTag(main) != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentByTag(main)).commit();
        }
        if (!isTablet()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment(), main)
                    .commit();
        } else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_landscape, new MainFragment(), main)
                    .commit();
        }
    }

    public void fillNewFragment() {
        dbHelper = new FeedReaderContract.FeedReaderDBHelper(this);
        List<Note> savedNotes = dbHelper.getAllNotes();
        for (int i = savedNotes.size()-1; i >=0; i--) {
            makeFragment(savedNotes.get(i).theme, savedNotes.get(i).text, savedNotes.get(i).color,
                    savedNotes.get(i).id, savedNotes.get(i).date);
        }
    }

    public void toastShow(Context context, CharSequence text) {
    int duration = Toast.LENGTH_SHORT;
    Toast toast = Toast.makeText(context, text, duration);
    toast.setGravity(Gravity.BOTTOM,0,45);
    toast.show();
    }

    public boolean isTablet() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}