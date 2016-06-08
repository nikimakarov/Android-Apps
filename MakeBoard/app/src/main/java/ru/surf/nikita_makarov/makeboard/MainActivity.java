package ru.surf.nikita_makarov.makeboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TableLayout tableMain;
    private LayoutInflater inflater;
    public ImageView button;
    public ScrollView scroll;
    public int inputNumber = 0;
    public int turn = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            inputNumber = savedInstanceState.getInt("number");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
        button = (ImageView) findViewById(R.id.button);
        tableMain = (TableLayout) findViewById(R.id.table_main);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scroll = (ScrollView) findViewById(R.id.scroll);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tableMain.removeAllViews();
                makeBoard();
            }
        });
    }

    protected void makeBoard() {
        try {
            int parity, shift;
            inputNumber = Integer.parseInt(editText.getText().toString());
            for (int i = 0; i < inputNumber; i++) {
                LinearLayout table_view = (LinearLayout) inflater.inflate(R.layout.table, null);
                table_view.setId(i);
                shift = setShift(i);
                for (int j = 0; j < inputNumber; j++) {
                    final ToggleButton square = (ToggleButton) inflater.inflate(R.layout.square, null);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.height = tableMain.getWidth() / inputNumber;
                    params.weight = 1;
                    parity = (j % 2 == shift) ? 255 : 102;
                    square.setBackgroundColor(Color.rgb(51, parity, 204));
                    table_view.addView(square, params);
                    turn = 0;
                    square.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (turn % 2 == 0) {
                                Drawable drawable1 = ResourcesCompat.getDrawable(getResources(), R.drawable.plus, null);
                                square.setBackground(drawable1);
                            } else {
                                Drawable drawable2 = ResourcesCompat.getDrawable(getResources(), R.drawable.unchecked, null);
                                square.setBackground(drawable2);
                            }
                            turn += 1;
                        }
                    });
                }
                tableMain.addView(table_view);
            }
        } catch (NumberFormatException e) {
            inputNumber = 0;
        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("number", inputNumber);
    }

    int setShift(int i) {
        if (i % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

}