package ru.surf.nikita_makarov.jotter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class NoteInfo extends Fragment{

    public TextView getDate, getTheme;
    public BorderedTextView getText;
    public int idIn, colorIn;
    public String themeIn, textIn, dateIn;
    public View sep1, sep2;
    public ScrollView scroll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        getText.setMinHeight(displaymetrics.widthPixels/2);
        idIn = getArguments().getInt("id", 0);
        themeIn = getArguments().getString("theme");
        textIn = getArguments().getString("text");
        colorIn = getArguments().getInt("color", 0);
        dateIn = getArguments().getString("date");
        themeIn = "Theme: " + themeIn;
        textIn = textIn;
        dateIn = "Note date: " + dateIn;
        getTheme.setText(themeIn);
        getText.setText(textIn);
        getDate.setText(dateIn);
        getTheme.setTextColor(colorIn);
        getText.setTextColor(colorIn);
        getDate.setTextColor(colorIn);
        sep1.setBackgroundColor(colorIn);
        sep2.setBackgroundColor(colorIn);
        getText.setBackgroundColor(Color.argb(Color.alpha(colorIn)-200,Color.red(colorIn),
                Color.green(colorIn),Color.blue(colorIn)));
        getText.setBorders(colorIn, 4);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}