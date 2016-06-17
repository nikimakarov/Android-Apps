package ru.surf.nikita_makarov.jotter.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.surf.nikita_makarov.jotter.view.DetailsTransition;
import ru.surf.nikita_makarov.jotter.R;

public class NoteFragment extends Fragment {

    public LinearLayout buttonLinearLayout;
    public TextView textView1;
    public TextView textView2;
    public ImageView buttonImageView;
    public int fragmentId = 0, fragmentColor = 0, fragmentHeight = 0;
    public String fragmentText, fragmentTheme, fragmentDate;
    public final String fragmentTag = "fragmentInfo";
    public NoteInfoFragment noteInfo;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle bundle = getArguments();
        fragmentId = bundle.getInt("id");
        fragmentTheme = bundle.getString("theme");
        fragmentText = bundle.getString("text");
        fragmentColor = bundle.getInt("color");
        fragmentHeight = bundle.getInt("height");
        fragmentDate = bundle.getString("date");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        buttonLinearLayout = (LinearLayout) view.findViewById(R.id.note);
        textView1 = (TextView) view.findViewById(R.id.text);
        textView2 = (TextView) view.findViewById(R.id.text2);
        buttonImageView = (ImageView) view.findViewById(R.id.btn);
        buttonLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });

        GridLayout.LayoutParams params = (GridLayout.LayoutParams) buttonLinearLayout.getLayoutParams();
        params.height = fragmentHeight;
        buttonLinearLayout.setLayoutParams(params);
        view.setBackgroundColor(fragmentColor);
        updateTextView();
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showInfo() {
        noteInfo = NoteInfoFragment.newInstance(fragmentId, fragmentTheme, fragmentText, fragmentColor, fragmentDate);
        makeTransition();
        replaceFragment();
    }

    public void makeTransition(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            noteInfo.setSharedElementEnterTransition(new DetailsTransition());
            noteInfo.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            noteInfo.setSharedElementReturnTransition(new DetailsTransition());
        }
    }

    public void replaceFragment(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .hide(getActivity().getSupportFragmentManager().findFragmentByTag("Main"))
                .commit();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, noteInfo, fragmentTag)
                .addToBackStack(null)
                .commit();
    }

    public void updateTextView() {
        String shortTheme = fragmentTheme;
        String shortText = fragmentText;
        this.textView1.setText(shortTheme);
        this.textView2.setText(shortText);
    }
}