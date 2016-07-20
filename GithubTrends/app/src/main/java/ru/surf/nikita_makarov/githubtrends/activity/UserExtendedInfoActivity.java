package ru.surf.nikita_makarov.githubtrends.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.surf.nikita_makarov.githubtrends.R;

public class UserExtendedInfoActivity extends AppCompatActivity {

    private ImageView userImageView;
    private TextView loginTextView;
    private TextView fullNameTextView;
    private TextView locationTextView;
    private TextView companyTextView;
    private TextView repoTextView;
    private TextView blogTextView;
    private TextView emailTextView;
    private static final String avatarString = "avatar";
    private static final String locationString = "location";
    private static final String emailString = "email";
    private static final String loginString = "login";
    private static final String blogString = "blog";
    private static final String repoString = "repository";
    private static final String companyString = "company";
    private static final String nameString = "name";
    private static final String emailPartString = "email: ";
    private static final String locationPartString = "location: ";
    private static final String blogPartString = "blog: ";
    private static final String repoPartString = "trending repo: ";
    private static final String companyPartString = "company: ";
    private static final String namePartString = "full name:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_extended_info);
        setViews();
        setIntentData();
    }

    public void setViews(){
        userImageView = (ImageView)findViewById(R.id.userImageView);
        loginTextView = (TextView) findViewById(R.id.loginTextView);
        fullNameTextView = (TextView) findViewById(R.id.fullNameTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        companyTextView = (TextView) findViewById(R.id.companyTextView);
        repoTextView = (TextView) findViewById(R.id.repoTextView);
        blogTextView = (TextView) findViewById(R.id.blogTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
    }

    public void setIntentData(){
        Intent intent = getIntent();
        setUserPic(intent);
        setUserData(intent);
    }

    public void setUserPic(Intent intent){
        Picasso.with(getApplicationContext()).load(intent.getStringExtra(avatarString))
                .noFade()
                .placeholder(R.drawable.github_logo1)
                .error(R.drawable.github_logo1)
                .into(userImageView);
    }

    public void setUserData(Intent intent){
        loginTextView.setText(intent.getStringExtra(loginString));
        String fullName = namePartString + intent.getStringExtra(nameString);
        fullNameTextView.setText(fullName);
        String location = locationPartString + intent.getStringExtra(locationString);
        locationTextView.setText(location);
        String company = companyPartString + intent.getStringExtra(companyString);
        companyTextView.setText(company);
        String repository = repoPartString + intent.getStringExtra(repoString);
        repoTextView.setText(repository);
        String blog = blogPartString + intent.getStringExtra(blogString);
        blogTextView.setText(blog);
        String email = emailPartString + intent.getStringExtra(emailString);
        emailTextView.setText(email);
    }
}
