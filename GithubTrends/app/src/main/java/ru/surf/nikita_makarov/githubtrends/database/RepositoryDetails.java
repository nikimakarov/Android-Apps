package ru.surf.nikita_makarov.githubtrends.database;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfo;

public class RepositoryDetails implements Serializable {

    protected static final String ID_FIELD = "repository_id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int repositoryId;

    @DatabaseField(columnName = "repository_name")
    public String repoName;

    @DatabaseField(columnName = "owner_name")
    public String ownerName;

    @DatabaseField(columnName = "html_url")
    public String htmlUrl;

    @DatabaseField(columnName = "language")
    public String language;

    @DatabaseField(columnName = "description")
    public String description;

    @DatabaseField(columnName = "forks_count")
    public int forksCount;

    @DatabaseField(columnName = "stars_count")
    public int starsCount;

    public RepositoryDetails(){
    }

    public RepositoryDetails(RepositoryInfo repositoryInfo) {
        this.repoName = repositoryInfo.getFull_name();
        this.ownerName = repositoryInfo.getAuthor();
        this.htmlUrl = repositoryInfo.getHtml_url();
        this.language = repositoryInfo.getLanguage();
        this.description = repositoryInfo.getDescription();
        this.forksCount = repositoryInfo.getForks_count();
        this.starsCount = repositoryInfo.getStargazers_count();
    }
}