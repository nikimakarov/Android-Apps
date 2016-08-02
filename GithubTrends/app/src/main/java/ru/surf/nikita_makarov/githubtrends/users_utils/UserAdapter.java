package ru.surf.nikita_makarov.githubtrends.users_utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.surf.nikita_makarov.githubtrends.R;
import ru.surf.nikita_makarov.githubtrends.activity.UserExtendedInfoActivity;
import ru.surf.nikita_makarov.githubtrends.database.DatabaseHelper;
import ru.surf.nikita_makarov.githubtrends.database.UserDetails;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public List<UserInfo> userList;
    public List<SmallRepositoryInfo> smallRepositoryInfoList;
    public Context context;
    public SmallRepositoryInfo smi;
    public UserInfo ui;
    public DatabaseHelper databaseUserHelper = null;
    private static final String emptyString = "";
    private static final String nullString = "null";
    private static final String slashString = " / ";
    private static final String leftBracketString = " (";
    private static final String rightBracketString = ")";
    private static final String avatarString = "avatar";
    private static final String locationString = "location";
    private static final String emailString = "email";
    private static final String loginString = "login";
    private static final String blogString = "blog";
    private static final String repoString = "repository";
    private static final String companyString = "company";
    private static final String nameString = "name";

    public UserAdapter() {
        super();
        userList = new ArrayList<>();
        smallRepositoryInfoList = new ArrayList<>();
    }

    public void addRepositoryData(List<SmallRepositoryInfo> addRepositoryList, Context addContext) {
        context = addContext;
        smallRepositoryInfoList = addRepositoryList;
        notifyDataSetChanged();
    }

    public void addListUserData(List<UserInfo> addUserList) {
        userList = addUserList;
        notifyDataSetChanged();
    }

    public void clearRepositoryData() {
        smallRepositoryInfoList.clear();
        notifyDataSetChanged();
    }

    public void clearUserData() {
        userList.clear();
        notifyDataSetChanged();
    }

    public void sendUserToDatabase(UserInfo user) {
        try {
            userList.add(user);
            final Dao<UserDetails, Integer> repoDao = getHelper().getUserDao();
            for (int j = 0; j < smallRepositoryInfoList.size(); j++) {
                if (user.getLogin().equals(smallRepositoryInfoList.get(j).getAuthorLogin())) {
                    UserDetails userSpecimen = new UserDetails(user, smallRepositoryInfoList.get(j));
                    repoDao.create(userSpecimen);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_user, viewGroup, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder userViewHolder, int i) {
        ui = userList.get(i);
        for (int j = 0; j < smallRepositoryInfoList.size(); j++) {
            if (ui.getLogin().equals(smallRepositoryInfoList.get(j).getAuthorLogin())) {
                smi = smallRepositoryInfoList.get(j);
            }
        }
        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(ui.getAvatar_url())
                .noFade()
                .placeholder(R.drawable.github_logo1)
                .error(R.drawable.github_logo1)
                .into(userViewHolder.userPortraitImageView);
        String userLoginAndName;
        try {
            if (!ui.getName().equals(nullString)) {
                userLoginAndName = ui.getLogin() + leftBracketString + ui.getName() + rightBracketString;
            } else {
                userLoginAndName = ui.getLogin();
            }
        } catch (NullPointerException ex) {
            userLoginAndName = ui.getLogin();
        }
        userViewHolder.loginWithNameTextView.setText(userLoginAndName);
        String repositoryNameAndLanguage = smi.getName() + languageShow(smi.getLanguage());
        userViewHolder.descriptionTextView.setText(repositoryNameAndLanguage);
        userViewHolder.usersLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = userViewHolder.getAdapterPosition();
                sendToFullInfo(v, position);
            }
        });
    }

    public String languageShow(String language) {
        try {
            if (!language.equals(nullString)) {
                return slashString + language;
            } else {
                return emptyString;
            }
        } catch (NullPointerException ex) {
            return emptyString;
        }
    }

    public void sendToFullInfo(View v, int position) {
        UserInfo uif = userList.get(position);
        SmallRepositoryInfo smif = new SmallRepositoryInfo();
        for (int j = 0; j < smallRepositoryInfoList.size(); j++) {
            if (uif.getLogin().equals(smallRepositoryInfoList.get(j).getAuthorLogin())) {
                smif = smallRepositoryInfoList.get(j);
            }
        }
        Intent intent = new Intent(v.getContext(), UserExtendedInfoActivity.class);
        intent.putExtra(avatarString, uif.getAvatar_url());
        intent.putExtra(loginString, uif.getLogin());
        intent.putExtra(nameString, uif.getName());
        intent.putExtra(locationString, uif.getLocation());
        intent.putExtra(companyString, uif.getCompany());
        intent.putExtra(repoString, smif.getName());
        intent.putExtra(blogString, uif.getBlog());
        intent.putExtra(emailString, uif.getEmail());
        context.startActivity(intent);
    }

    public DatabaseHelper getHelper() {
        if (databaseUserHelper == null) {
            databaseUserHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseUserHelper;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView loginWithNameTextView;
        public TextView descriptionTextView;
        public ImageView userPortraitImageView;
        public LinearLayout usersLinearLayout;

        public UserViewHolder(View v) {
            super(v);
            usersLinearLayout = (LinearLayout) v.findViewById(R.id.usersLinearLayout);
            loginWithNameTextView = (TextView) v.findViewById(R.id.loginWithNameTextView);
            descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);
            userPortraitImageView = (ImageView) v.findViewById(R.id.userPortraitImageView);
        }
    }

}