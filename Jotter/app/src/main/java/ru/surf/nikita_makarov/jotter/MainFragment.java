package ru.surf.nikita_makarov.jotter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.fragment_layout,
                container, false);

        return myFragmentView;
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        List<NoteStruct> savedNotes = ((MainActivity)getActivity()).dbHelper.getAllNotes();
        for (NoteStruct savedNote : savedNotes) {
            ((MainActivity)getActivity()).fragmentId = savedNote.id;
            ((MainActivity)getActivity()).MakeFragment(savedNote.theme, savedNote.text, savedNote.color, savedNote.id, savedNote.date);
        }
    }
*/
}

