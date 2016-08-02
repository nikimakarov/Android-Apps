package ru.surf.nikita_makarov.githubtrends.users_utils;

import ru.surf.nikita_makarov.githubtrends.database.UserDetails;

public class UserInfo {

    private String login;
    private String name;
    private String html_url;
    private String avatar_url;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String bio;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void transform(UserDetails userDetails) {
        setName(userDetails.userName);
        setLogin(userDetails.userLogin);
        setAvatar_url(userDetails.avatarUrl);
        setBio(userDetails.bio);
        setBlog(userDetails.blog);
        setCompany(userDetails.company);
        setHtml_url(userDetails.htmlUrl);
        setLocation(userDetails.location);
        setEmail(userDetails.email);
    }
}