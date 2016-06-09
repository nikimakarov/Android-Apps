package ru.surf.nikita_makarov.jotter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NoteInfoActivity extends AppCompatActivity {

    TextView getTheme, getText, getNumber;
    int idIn, colorIn;
    String themeIn, textIn;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_info);
        getTheme = (TextView) findViewById(R.id.text_theme);
        getText = (TextView) findViewById(R.id.text_main);
        getNumber = (TextView) findViewById(R.id.number);
        back = (Button) findViewById(R.id.button_back);
        Intent data = getIntent();
        idIn = data.getIntExtra("id", 0);
        themeIn = data.getStringExtra("theme");
        Log.v("So22", themeIn);
        textIn = data.getStringExtra("text");
        Log.v("So22", textIn);
        colorIn = data.getIntExtra("color", 0);
        getTheme.setText(themeIn);
        getText.setText(textIn);
        getNumber.setText(Integer.toString(idIn));
        getNumber.setTextColor(colorIn);
        back.setBackgroundColor(colorIn);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(colorIn));
    }
}