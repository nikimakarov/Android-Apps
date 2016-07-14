package ru.surf.nikita_makarov.githubtrends.users_utils;

import java.util.List;

public class UserInfoResponse {
    List<SmallRepositoryInfo> items;

    public List<SmallRepositoryInfo> getItems() {
        return items;
    }

    public void setItems(List<SmallRepositoryInfo> items) {
        this.items = items;
    }
}
