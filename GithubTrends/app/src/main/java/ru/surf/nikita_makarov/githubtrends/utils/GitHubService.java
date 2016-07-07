package ru.surf.nikita_makarov.githubtrends.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfoResponse;

public interface GitHubService {
    @GET("search/repositories")
    Call<RepositoryInfoResponse> repositories(
            @Query("q") String query,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("page") String page,
            @Query("per_page") String per_page);
}