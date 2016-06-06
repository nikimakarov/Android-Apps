package ru.surf.nikita_makarov.jotter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Note extends Fragment {

    public GridLayout button;
    public TextView txt, txt2;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    //вызывается один раз, когда фрагмент должен загрузить на экран свой интерфейс
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note, container, false);
    }

    @Override
    //вызывается после метода onCreateView(), когда создаётся активность-хозяйка для фрагмента.
    // Здесь можно объявить объекты, необходимые для Context.
    //Фрагменты не являются подклассами Context, вам следует использовать метод getActivity(),
    // чтобы получить родительскую активность.
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = (GridLayout) getView().findViewById(R.id.note);
        txt = (TextView) getView().findViewById(R.id.text);
        txt2 = (TextView) getView().findViewById(R.id.text2);
    }

}