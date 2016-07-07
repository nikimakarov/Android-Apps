package ru.surf.nikita_makarov.githubtrends.repository_utils;

import java.util.List;
public class RepositoryInfoResponse {
    List<RepositoryInfo> items;

    public List<RepositoryInfo> getItems() {
        return items;
    }

    public void setItems(List<RepositoryInfo> items) {
        this.items = items;
    }
}
