package ru.surf.nikita_makarov.githubtrends.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.surf.nikita_makarov.githubtrends.R;

public class UserExtendedInfoActivity extends AppCompatActivity {

    private ImageView userImageView;
    private TextView loginTextView;
    private TextView fullInfoTextView;
    private Intent intent;
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
    private static final String transitionString = "\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_extended_info);
        setViews();
        setIntentData();
    }

    public void setViews() {
        userImageView = (ImageView) findViewById(R.id.userImageView);
        loginTextView = (TextView) findViewById(R.id.loginTextView);
        fullInfoTextView = (TextView) findViewById(R.id.fullInfoTextView);
    }

    public void setIntentData() {
        intent = getIntent();
        setUserPic();
        setUserData();
    }

    public void setUserPic() {
        Picasso.with(getApplicationContext()).load(intent.getStringExtra(avatarString))
                .noFade()
                .placeholder(R.drawable.github_logo1)
                .error(R.drawable.github_logo1)
                .into(userImageView);
    }

    public void setUserData() {
        loginTextView.setText(intent.getStringExtra(loginString));
        String fullInfo = addString(namePartString, nameString) +
                addString(locationPartString, locationString) +
                addString(companyPartString, companyString) +
                addString(repoPartString, repoString) +
                addString(blogPartString, blogString) +
                addString(emailPartString, emailString);
        fullInfoTextView.setText(fullInfo);
        Linkify.addLinks(fullInfoTextView, Linkify.ALL);
    }

    public String addString(String title, String data) {
        if (intent.getStringExtra(data) != null) {
            return title + intent.getStringExtra(data) + transitionString;
        } else {
            return "";
        }
    }

}
