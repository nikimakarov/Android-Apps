package ru.surf.nikita_makarov.githubtrends.users_utils;

import ru.surf.nikita_makarov.githubtrends.database.UserDetails;

public class SmallRepositoryInfo {

    public SmallRepositoryOwner owner = new SmallRepositoryOwner();
    private String name;
    private String language;

    public String getAuthorLogin() {
        return owner.getLogin();
    }

    public void setAuthorLogin(String login) {
        owner.setLogin(login);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public class SmallRepositoryOwner {
        public String login;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }

    public void transform(UserDetails details) {
        setName(details.repoName);
        setAuthorLogin(details.userLogin);
        setLanguage(details.repoLanguage);
    }
}
