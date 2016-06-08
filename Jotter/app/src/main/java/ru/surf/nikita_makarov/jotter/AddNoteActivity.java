package ru.surf.nikita_makarov.jotter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import ru.surf.nikita_makarov.jotter.FeedReaderContract.FeedReaderDBHelper;

public class AddNoteActivity extends AppCompatActivity {
    public EditText data1;
    public EditText data2;
    public String themeAdd;
    public String textAdd;
    private Button button;
    private FeedReaderDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        data1 = (EditText) findViewById(R.id.editText);
        data2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        dbHelper = new FeedReaderDBHelper(this);
        initListeners();
    }

    private void initListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                themeAdd = data1.getText().toString();
                textAdd = data2.getText().toString();
                //colorAdd = ...;
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_THEME, themeAdd);
                contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEXT, textAdd);
                database.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, contentValues);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
