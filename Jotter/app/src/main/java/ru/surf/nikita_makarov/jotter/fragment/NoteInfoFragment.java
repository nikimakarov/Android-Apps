package ru.surf.nikita_makarov.jotter.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    public int idInput, colorInput;
    public String themeInput, textInput, dateInput;
    public View separatorView1, sep2;
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
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .show(getActivity().getSupportFragmentManager().findFragmentByTag("Main"))
                .addToBackStack(null)
                .commit();
    }

    public static NoteInfoFragment newInstance(int fragmentId, String fragmentTheme,
                                               String fragmentText, int fragmentColor, String fragmentDate) {
        Bundle args = new Bundle();
        args.putInt(idString, fragmentId);
        args.putString(dateString, fragmentDate);
        args.putString(themeString, fragmentTheme);
        args.putString(textString, fragmentText);
        args.putInt(colorString, fragmentColor);
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
        sep2 = view.findViewById(R.id.separator2);
        getTextBorderedTextView = (BorderedTextView) view.findViewById(R.id.text_main);
        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        getTextBorderedTextView.setMinHeight(displaymetrics.widthPixels/2);
        idInput = getArguments().getInt(idString, 0);
        themeInput = getArguments().getString(themeString);
        textInput = getArguments().getString(textString);
        colorInput = getArguments().getInt(colorString, 0);
        dateInput = getArguments().getString(dateString);
        themeInput = getString(R.string.note_fragment_theme) + themeInput;
        dateInput = getString(R.string.note_fragment_date) + dateInput;
        getThemeTextView.setText(themeInput);
        getTextBorderedTextView.setText(textInput);
        getDateTextView.setText(dateInput);
        getThemeTextView.setTextColor(colorInput);
        getTextBorderedTextView.setTextColor(colorInput);
        getDateTextView.setTextColor(colorInput);
        separatorView1.setBackgroundColor(colorInput);
        sep2.setBackgroundColor(colorInput);
        getTextBorderedTextView.setBackgroundColor(Color.argb(Color.alpha(colorInput)-200,Color.red(colorInput),
                Color.green(colorInput),Color.blue(colorInput)));
        getTextBorderedTextView.setBorders(colorInput, 4);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}