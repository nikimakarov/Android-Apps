package ru.surf.nikita_makarov.makeboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText edit_text;
    ImageView button;
    TableLayout table_main;
    LayoutInflater inflater;
    ScrollView scroll;
    int input_number = 0;
    int turn = 0;

    int setShift(int i) {
        if (i % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            input_number = savedInstanceState.getInt("number");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_text = (EditText) findViewById(R.id.edit_text);
        button = (ImageView) findViewById(R.id.button);
        table_main = (TableLayout) findViewById(R.id.table_main);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scroll = (ScrollView) findViewById(R.id.scroll);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                table_main.removeAllViews();
                MakeBoard(input_number);
            }
        });
    }

    protected void MakeBoard(int size) {
        try {
            int parity = 0;
            int shift = 0;
            size = Integer.parseInt(edit_text.getText().toString());
            for (int i = 0; i < size; i++) {
                LinearLayout table_view = (LinearLayout) inflater.inflate(R.layout.table, null);
                table_view.setId(i);
                shift = setShift(i);
                for (int j = 0; j < size; j++) {
                    final ToggleButton square = (ToggleButton) inflater.inflate(R.layout.square, null);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.height = table_main.getWidth() / size;
                    params.weight = 1;
                    parity = (j % 2 == shift) ? 255 : 102;
                    square.setBackgroundColor(Color.rgb(51, parity, 204));
                    table_view.addView(square, params);
                    turn = 0;
                    square.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (turn % 2 == 0) {
                                Drawable drawable1 = getResources().getDrawable(R.drawable.plus);
                                square.setBackground(drawable1);
                            } else {
                                Drawable drawable2 = getResources().getDrawable(R.drawable.unchecked);
                                square.setBackground(drawable2);
                            }
                            turn += 1;
                        }
                    });
                }
                table_main.addView(table_view);
            }
        } catch (NumberFormatException e) {
            size = 0;
        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("number", input_number);
    }
}