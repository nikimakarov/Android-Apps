package ru.surf.nikita_makarov.makeboard;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {

    EditText edit_text;
    ImageView button;
    TableLayout table_main;
    LayoutInflater inflater;
    int input_number = 0;

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
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MakeBoard(input_number);
            }
        });
    }

    protected void MakeBoard(int size) {
        try {
            table_main.removeAllViews();
            int parity = 0;
            int shift = 0;
            size = Integer.parseInt(edit_text.getText().toString());
            table_main.setWeightSum(size);
            for (int i = 0; i < size; i++) {
                TableRow table_view = (TableRow) inflater.inflate(R.layout.table, null);
                table_view.setId(i);
                table_view.setWeightSum(size);
                shift = setShift(i);
                for (int j = 0; j < size; j++) {
                    Button square = (Button) inflater.inflate(R.layout.square, null);
                    parity = (j % 2 == shift) ? 255 : 99;
                    square.setBackgroundColor(Color.rgb(0, parity, 144));
                    table_view.addView(square);
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