package ru.surf.nikita_makarov.jotter.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.surf.nikita_makarov.jotter.R;

public class AddNoteActivity extends AppCompatActivity {
    public EditText dataEditText1;
    public EditText dataEditText2;
    public ImageView colorShowImageView;
    public String theme;
    public String text;
    public int color;
    private Button addButton;
    public static final int RESULT_CODE = 1;
    public static final String themeString = "theme";
    public static final String textString = "text";
    public static final String colorString = "color";
    public static final String dateString = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        dataEditText1 = (EditText) findViewById(R.id.editText);
        dataEditText2 = (EditText) findViewById(R.id.editText2);
        colorShowImageView = (ImageView) findViewById(R.id.color_show);
        addButton = (Button) findViewById(R.id.button);
        color = ContextCompat.getColor(this, R.color.blue);
        colorShowImageView.setBackgroundColor(color);
        initListeners();
    }

    private void initListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (dataEditText1.getText().length() != 0 & dataEditText2.getText().length() != 0 & color != 0) {
                    theme = dataEditText1.getText().toString();
                    text = dataEditText2.getText().toString();
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd. MM. yyyy h:mm a", Locale.getDefault());
                    String dateAdd = sdf.format(date);
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra(dateString, dateAdd);
                    intent.putExtra(themeString, theme);
                    intent.putExtra(textString, text);
                    intent.putExtra(colorString, color);
                    setResult(RESULT_CODE, intent);
                    finish();
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = getString(R.string.Warning);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.BOTTOM, 0, 45);
                    toast.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.colors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.yellow:
                color = ContextCompat.getColor(this, R.color.yellow);
                break;
            case R.id.red:
                color = ContextCompat.getColor(this, R.color.red);
                break;
            case R.id.blue:
                color = ContextCompat.getColor(this, R.color.blue);
                break;
            case R.id.green:
                color = ContextCompat.getColor(this, R.color.green);
                break;
            case R.id.violet:
                color = ContextCompat.getColor(this, R.color.violet);
                break;
            case R.id.pink:
                color = ContextCompat.getColor(this, R.color.pink);
                break;
            default:
                color = ContextCompat.getColor(this, R.color.blue);
                break;
        }
        colorShowImageView.setBackgroundColor(color);
        return true;
    }


}