package ru.surf.nikita_makarov.jotter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {
    public int idAdd;
    public String TAG = "AddNoteActivity";
    public EditText data1;
    public EditText data2;
    public String themeAdd;
    public String textAdd;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            idAdd = savedInstanceState.getInt("id");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        data1 = (EditText) findViewById(R.id.editText);
        data2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        initListeners();
    }

    private void initListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                themeAdd = data1.getText().toString();
                textAdd = data2.getText().toString();
                Log.v(TAG, themeAdd);
                Log.v(TAG, textAdd);
                Log.v(TAG, Integer.toString(idAdd));
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("theme", themeAdd);
                intent.putExtra("text", textAdd);
                intent.putExtra("id", idAdd);
                startActivity(intent);
            }
        });
    }
}
