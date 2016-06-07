package ru.surf.nikita_makarov.jotter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class AddNoteActivity extends AppCompatActivity {

    public String themeAdd = "";
    public String textAdd = "";
    public int idAdd = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null) {
            idAdd = savedInstanceState.getInt("id");
        }
        setContentView(R.layout.activity_add_note);
        RelativeLayout relative = (RelativeLayout) findViewById(R.id.relative);
        EditText data1 = (EditText) relative.findViewById(R.id.editText);
        themeAdd = data1.getText().toString();
        EditText data2 = (EditText) relative.findViewById(R.id.editText2);
        textAdd = data2.getText().toString();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("theme", themeAdd);
        intent.putExtra("text", textAdd);
        intent.putExtra("id", idAdd);
        startActivity(intent);
    }
}
