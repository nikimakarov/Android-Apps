package ru.surf.nikita_makarov.makeboard;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int input_number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText edit_text = (EditText) findViewById(R.id.edit_text);
        final ImageView button = (ImageView) findViewById(R.id.button);
        final GridLayout grid = (GridLayout) findViewById(R.id.grid);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    input_number = Integer.parseInt(edit_text.getText().toString());
                    grid.setColumnCount(input_number);
                    grid.setRowCount(input_number);
                    int parity = 0;
                    for (int i = 0; i < input_number * input_number; i++) {
                        LayoutInflater ltInflater = getLayoutInflater();
                        View view = ltInflater.inflate(R.layout.square, null, false);
                        parity = (i % 2 == 0) ? 255 : 99;
                        view.setBackgroundColor(Color.rgb(0, parity, 144));
                        grid.addView(view);
                    }

                } catch (NumberFormatException e) {
                    input_number = 0;
                }
            }
        });
    }
}