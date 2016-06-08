package ru.surf.nikita_makarov.jotter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Note extends Fragment {

    public LinearLayout button;
    public TextView txt, txt2;
    public int fragmentId = 0;
    public String fragmentText, fragmentTheme;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle bundle = getArguments();
        fragmentId = bundle.getInt("id");
        fragmentTheme = bundle.getString("theme");
        fragmentText = bundle.getString("text");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.note, container, false);
        button = (LinearLayout) view.findViewById(R.id.note);
        txt = (TextView) view.findViewById(R.id.text);
        txt2 = (TextView) view.findViewById(R.id.text2);
        updateTextView();
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void updateTextView()
    {
        this.txt.setText(fragmentTheme);
        this.txt2.setText(fragmentText);
    }

}