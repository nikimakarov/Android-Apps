package ru.surf.nikita_makarov.jotter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {

    String theme = "";
    String text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText data1 = (EditText) findViewById(R.id.editText);
        theme = data1.getText().toString();
        EditText data2 = (EditText) findViewById(R.id.editText2);
        text = data2.getText().toString();

        final Button save = (Button) findViewById(R.id.button);

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("theme", theme);
        intent.putExtra("text", text);
        startActivity(intent);
    }
}
