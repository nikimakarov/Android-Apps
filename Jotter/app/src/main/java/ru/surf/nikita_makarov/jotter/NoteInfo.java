package ru.surf.nikita_makarov.jotter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NoteInfo extends Fragment {

    public TextView getDate, getTheme;
    public BorderedTextView getText;
    public int idIn, colorIn;
    public String themeIn, textIn, dateIn;
    public View sep1, sep2;
    public FeedReaderContract.FeedReaderDBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static NoteInfo newInstance(int fragmentId, String fragmentTheme,
                                       String fragmentText, int fragmentColor, String fragmentDate) {
        Bundle args = new Bundle();
        args.putInt("id", fragmentId);
        args.putString("date", fragmentDate);
        args.putString("theme", fragmentTheme);
        args.putString("text", fragmentText);
        args.putInt("color", fragmentColor);
        NoteInfo fragment = new NoteInfo();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_info, container, false);
        getDate = (TextView) view.findViewById(R.id.date);
        getTheme = (TextView) view.findViewById(R.id.text_theme);
        sep1 = view.findViewById(R.id.separator1);
        sep2 = view.findViewById(R.id.separator2);
        getText = (BorderedTextView) view.findViewById(R.id.text_main);
        idIn = getArguments().getInt("id", 0);
        themeIn = getArguments().getString("theme");
        textIn = getArguments().getString("text");
        colorIn = getArguments().getInt("color", 0);
        dateIn = getArguments().getString("date");
        themeIn = "Theme: " + themeIn;
        textIn = "Text: " + textIn;
        dateIn = "Note date: " + dateIn;
        getTheme.setText(themeIn);
        getText.setText(textIn);
        getDate.setText(dateIn);
        getTheme.setTextColor(colorIn);
        getText.setTextColor(Color.WHITE);
        getDate.setTextColor(colorIn);
        sep1.setBackgroundColor(colorIn);
        sep2.setBackgroundColor(colorIn);
        getText.setBackgroundColor(colorIn);
        getText.setBorders(colorIn - 30000, 10);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, 1, 0, "Delete").setIcon(R.drawable.minus)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getTitle()=="Delete") {
            dbHelper = new FeedReaderContract.FeedReaderDBHelper(getContext());
            dbHelper.deleteNote(idIn);
            Intent gotoMainScreen = new Intent(getContext(), MainActivity.class);
            CharSequence text = "Note has been deleted.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getContext(), text, duration);
            toast.setGravity(Gravity.BOTTOM, 0, 45);
            startActivity(gotoMainScreen);
            toast.show();
        }
        return true;
    }

}