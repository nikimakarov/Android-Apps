package ru.surf.nikita_makarov.jotter;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Note extends Fragment{

    public LinearLayout button;
    public TextView txt, txt2;
    public int fragmentId = 0, fragmentColor = 0, fragmentHeight = 0;
    public String fragmentText, fragmentTheme, fragmentDate;
    public ImageView btn;

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
        fragmentDate = bundle.getString("date");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.note, container, false);
        button = (LinearLayout) view.findViewById(R.id.note);
        txt = (TextView) view.findViewById(R.id.text);
        txt2 = (TextView) view.findViewById(R.id.text2);
        btn = (ImageView) view.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInfo();
            }
        });

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

    public void ShowInfo() {
        NoteInfo noteInfo = NoteInfo.newInstance(fragmentId, fragmentTheme, fragmentText, fragmentColor, fragmentDate);
        // the transition is only available in API 21+.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            noteInfo.setSharedElementEnterTransition(new DetailsTransition());
            noteInfo.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            noteInfo.setSharedElementReturnTransition(new DetailsTransition());
        }
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.linear_main, noteInfo)
                .addToBackStack(null)
                .commit();
    }

    public void updateTextView() {
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