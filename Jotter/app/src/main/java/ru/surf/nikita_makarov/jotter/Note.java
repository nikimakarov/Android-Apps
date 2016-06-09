package ru.surf.nikita_makarov.jotter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Note extends Fragment {

    public LinearLayout button;
    public TextView txt, txt2;
    public int fragmentId = 0, fragmentColor = 0, fragmentHeight = 0;
    public String fragmentText, fragmentTheme;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle bundle = getArguments();
        fragmentId = bundle.getInt("id");
        fragmentTheme = bundle.getString("theme");
        fragmentText = bundle.getString("text");
        fragmentColor = bundle.getInt("color");
        fragmentHeight = bundle.getInt("height", 184);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.note, container, false);
        button = (LinearLayout) view.findViewById(R.id.note);
        txt = (TextView) view.findViewById(R.id.text);
        txt2 = (TextView) view.findViewById(R.id.text2);
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) button.getLayoutParams();
        params.height = fragmentHeight;
        button.setLayoutParams(params);
        view.setBackgroundColor(fragmentColor);
        updateTextView();
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void updateTextView()
    {
        String shortTheme = cropString(fragmentTheme,22);
        String shortText =  cropString(fragmentText,30);
        this.txt.setText(shortTheme);
        this.txt2.setText(shortText);
    }

    public String cropString(String crop, int maxCropLength)
    {
        if (crop.length()>maxCropLength)
            return crop.substring(0,crop.substring(0,maxCropLength).lastIndexOf(" "))+ "...";
        else
            return crop;
    }
}