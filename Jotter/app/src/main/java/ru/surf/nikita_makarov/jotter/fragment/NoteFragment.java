package ru.surf.nikita_makarov.jotter.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public int fragmentColor = 0;
    public long fragmentId;
    public String fragmentText;
    public String fragmentTheme;
    public String fragmentDate;
    public final String fragmentTag = "fragmentInfo";
    public NoteInfoFragment noteInfo;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle bundle = getArguments();
        fragmentTheme = bundle.getString("theme");
        fragmentText = bundle.getString("text");
        fragmentColor = bundle.getInt("color");
        fragmentDate = bundle.getString("date");
        fragmentId  = bundle.getLong("id");
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
        view.setBackgroundColor(fragmentColor);
        updateTextView();
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showInfo() {
        noteInfo = NoteInfoFragment.newInstance(fragmentTheme, fragmentText, fragmentColor, fragmentDate, fragmentId);
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