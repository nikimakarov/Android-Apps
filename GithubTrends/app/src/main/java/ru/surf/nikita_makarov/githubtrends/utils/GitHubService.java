package ru.surf.nikita_makarov.githubtrends.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfoResponse;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserInfo;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserInfoResponse;

public interface GitHubService {
    @GET("search/repositories")
    Call<RepositoryInfoResponse> repositories(
            @Query(value = "q", encoded = true) String query,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("page") int page,
            @Query("per_page") int per_page);

    @GET("search/repositories")
    Call<UserInfoResponse> smallRepositories(
            @Query(value = "q", encoded = true) String query,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("page") int page,
            @Query("per_page") int per_page);

    @GET("users/{username}")
    Call<UserInfo> users(
            @Path("username")String user,
            @Query("access_token") String token);

}