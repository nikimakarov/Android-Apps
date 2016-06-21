package ru.surf.nikita_makarov.jotter.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.surf.nikita_makarov.jotter.view.BorderedTextView;
import ru.surf.nikita_makarov.jotter.R;

public class NoteInfoFragment extends Fragment{
    public TextView getDateTextView, getThemeTextView;
    public BorderedTextView getTextBorderedTextView;
    public int color;
    public long id;
    public String theme;
    public String text;
    public String date;
    public View separatorView1;
    public View separatorView2;
    public static final String themeString = "theme";
    public static final String textString = "text";
    public static final String colorString = "color";
    public static final String dateString = "date";
    public static final String idString = "id";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .show(getActivity().getSupportFragmentManager().findFragmentByTag("Main"))
                .addToBackStack(null)
                .commit();
    }

    public static NoteInfoFragment newInstance(String fragmentTheme,
                                               String fragmentText, int fragmentColor, String fragmentDate, long fragmentId) {
        Bundle args = new Bundle();
        args.putString(dateString, fragmentDate);
        args.putString(themeString, fragmentTheme);
        args.putString(textString, fragmentText);
        args.putInt(colorString, fragmentColor);
        args.putLong(idString, fragmentId);
        NoteInfoFragment fragment = new NoteInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_info, container, false);
        getDateTextView = (TextView) view.findViewById(R.id.date);
        getThemeTextView = (TextView) view.findViewById(R.id.text_theme);
        separatorView1 = view.findViewById(R.id.separator1);
        separatorView2 = view.findViewById(R.id.separator2);
        getTextBorderedTextView = (BorderedTextView) view.findViewById(R.id.text_main);
        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        getTextBorderedTextView.setMinHeight(displaymetrics.widthPixels/2);
        theme = getArguments().getString(themeString);
        text = getArguments().getString(textString);
        color = getArguments().getInt(colorString, 0);
        date = getArguments().getString(dateString);
        id = getArguments().getLong(idString, 0);
        String themeOnScreen = getString(R.string.note_fragment_theme) + theme;
        String dateOnScreen = getString(R.string.note_fragment_date) + date;
        getThemeTextView.setText(themeOnScreen);
        getTextBorderedTextView.setText(text);
        getDateTextView.setText(dateOnScreen);
        getThemeTextView.setTextColor(color);
        getTextBorderedTextView.setTextColor(color);
        getDateTextView.setTextColor(color);
        separatorView1.setBackgroundColor(color);
        separatorView2.setBackgroundColor(color);
        getTextBorderedTextView.setBackgroundColor(Color.argb(Color.alpha(color)-200,Color.red(color),
                Color.green(color),Color.blue(color)));
        getTextBorderedTextView.setBorders(color, 4);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}