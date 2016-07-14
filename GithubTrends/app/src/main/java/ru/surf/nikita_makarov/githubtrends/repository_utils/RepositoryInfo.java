package ru.surf.nikita_makarov.githubtrends.repository_utils;

import java.util.ArrayList;
import java.util.List;

import ru.surf.nikita_makarov.githubtrends.database.RepositoryDetails;

public class RepositoryInfo {
    private RepositoryOwner owner = new RepositoryOwner();
    private String full_name;
    private String html_url;
    private String language;
    private String description;
    private int forks_count;
    private int stargazers_count;

    public String getAuthor() {
        return owner.getLogin();
    }

    public void setAuthor(String login) {
        owner.setLogin(login);
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public class RepositoryOwner {
        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String login;
    }

    public void transform(RepositoryDetails repositoryDetails) {
        setAuthor(repositoryDetails.ownerName);
        setDescription(repositoryDetails.description);
        setFull_name(repositoryDetails.repoName);
        setLanguage(repositoryDetails.language);
        setHtml_url(repositoryDetails.htmlUrl);
        setForks_count(repositoryDetails.forksCount);
        setStargazers_count(repositoryDetails.starsCount);
    }
}