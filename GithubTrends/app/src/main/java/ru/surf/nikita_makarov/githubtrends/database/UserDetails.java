package ru.surf.nikita_makarov.githubtrends.database;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;

import ru.surf.nikita_makarov.githubtrends.users_utils.SmallRepositoryInfo;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserInfo;

public class UserDetails implements Serializable {

    public static final String ID_FIELD = "user_id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int userId;

    @DatabaseField(columnName = "repo_name")
    public String repoName;

    @DatabaseField(columnName = "repo_language")
    public String repoLanguage;

    @DatabaseField(columnName = "user_login")
    public String userLogin;

    @DatabaseField(columnName = "user_name")
    public String userName;

    @DatabaseField(columnName = "html_url")
    public String htmlUrl;

    @DatabaseField(columnName = "avatar_url")
    public String avatarUrl;

    @DatabaseField(columnName = "company")
    public String company;

    @DatabaseField(columnName = "blog")
    public String blog;

    @DatabaseField(columnName = "location")
    public String location;

    @DatabaseField(columnName = "email")
    public String email;

    @DatabaseField(columnName = "bio")
    public String bio;

    public UserDetails(){
    }

    public UserDetails(UserInfo userInfo, SmallRepositoryInfo smallInfo){
        this.repoName = smallInfo.getName();
        this.repoLanguage = smallInfo.getLanguage();
        this.userLogin = userInfo.getLogin();
        this.userName = userInfo.getName();
        this.htmlUrl = userInfo.getHtml_url();
        this.avatarUrl = userInfo.getAvatar_url();
        this.company = userInfo.getCompany();
        this.blog = userInfo.getBlog();
        this.location = userInfo.getLocation();
        this.email = userInfo.getEmail();
        this.bio = userInfo.getBio();
    }

}