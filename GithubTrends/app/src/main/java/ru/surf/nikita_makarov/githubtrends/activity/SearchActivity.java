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

    private ImageView filterImageView;
    private Spinner languageSpinner;
    private Spinner dateSpinner;
    private static final String languageString = "language";
    private static final String dateString = "date";
    private static final String cString = "C";
    private static final String javaString = "Java";
    private static final String pythonString = "Python";
    private static final String javaScriptString = "Javascript";
    private static final String objCString = "Objective-C";
    private static final String swiftString = "Swift";
    private static final String htmlString = "HTML";
    private static final String scalaString = "Scala";
    private static final String allString = "all";
    private static final String todayString = "today";
    private static final String weekString = "week";
    private static final String monthString = "month";
    private static final String yearString = "year";
    private static final String allTimeString = "all time";
    protected String languageChoice;
    protected String dateChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        filterImageView = (ImageView)findViewById(R.id.filterImageView);
        languageSpinner = (Spinner) findViewById(R.id.languageSpinner);
        dateSpinner = (Spinner) findViewById(R.id.dateSpinner);
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
        languageCategories.add(cString);
        languageCategories.add(javaString);
        languageCategories.add(pythonString);
        languageCategories.add(javaScriptString);
        languageCategories.add(objCString);
        languageCategories.add(swiftString);
        languageCategories.add(htmlString);
        languageCategories.add(scalaString);
        languageCategories.add(allString);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, languageCategories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(dataAdapter);
    }

    protected void setDateSpinner(){
        dateSpinner.setOnItemSelectedListener(this);
        List<String> dateCategories = new ArrayList<String>();
        dateCategories.add(todayString);
        dateCategories.add(weekString);
        dateCategories.add(monthString);
        dateCategories.add(yearString);
        dateCategories.add(allTimeString);
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
    }

}
