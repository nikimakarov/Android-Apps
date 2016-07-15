package ru.surf.nikita_makarov.githubtrends.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ru.surf.nikita_makarov.githubtrends.R;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected ImageView filterImageView;
    protected Spinner languageSpinner;
    protected Spinner dateSpinner;
    protected static final String languageString = "language";
    protected static final String dateString = "date";
    protected String languageChoice;
    protected String dateChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        filterImageView = (ImageView)findViewById(R.id.filterImageView);
        languageSpinner = (Spinner) findViewById(R.id.spinnerLanguage);
        dateSpinner = (Spinner) findViewById(R.id.spinnerDate);
        setLanguageSpinner();
        setDateSpinner();
        filterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnimationToImageView(filterImageView);
                dataTransitionToMainActivity(view);
            }
        });
    }

    protected void setAnimationToImageView(ImageView imageView){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(500);
        fadeOut.setDuration(1000);
        imageView.startAnimation(fadeOut);
    }

    protected void dataTransitionToMainActivity(View v){
        languageChoice = languageSpinner.getSelectedItem().toString();
        dateChoice = dateSpinner.getSelectedItem().toString();
        Intent intentMainActivity = new Intent(v.getContext(), MainActivity.class);
        intentMainActivity.putExtra(languageString, languageChoice);
        intentMainActivity.putExtra(dateString, dateChoice);
        startActivity(intentMainActivity);
    }

    protected void setLanguageSpinner(){
        languageSpinner.setOnItemSelectedListener(this);
        List<String> languageCategories = new ArrayList<String>();
        languageCategories.add("C++");
        languageCategories.add("Java");
        languageCategories.add("Python");
        languageCategories.add("JavaScript");
        languageCategories.add("Objective-C");
        languageCategories.add("Swift");
        languageCategories.add("HTML");
        languageCategories.add("Scala");
        languageCategories.add("all");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, languageCategories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(dataAdapter);
    }

    protected void setDateSpinner(){
        dateSpinner.setOnItemSelectedListener(this);
        List<String> dateCategories = new ArrayList<String>();
        dateCategories.add("today");
        dateCategories.add("week");
        dateCategories.add("month");
        dateCategories.add("year");
        dateCategories.add("all time");
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dateCategories);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(dateAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
