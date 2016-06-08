package ru.surf.nikita_makarov.jotter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {
    public String TAG = "AddNoteActivity";
    public EditText data1;
    public EditText data2;
    public String themeAdd;
    public String textAdd;
    private Button button;
    static final int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        data1 = (EditText) findViewById(R.id.editText);
        data2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        initListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG,"on ActivityCreate");
        if (data == null) {return;}
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
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("theme", themeAdd);
                intent.putExtra("text", textAdd);
                setResult(RESULT_CODE, intent);
                finish();
            }
        });
    }
}