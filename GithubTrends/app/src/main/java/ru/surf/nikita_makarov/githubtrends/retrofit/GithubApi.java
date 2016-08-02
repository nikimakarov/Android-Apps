package ru.surf.nikita_makarov.githubtrends.retrofit;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfoResponse;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserInfo;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserInfoResponse;
import rx.Observable;

public interface GithubApi {
    @GET("search/repositories")
    Observable<RepositoryInfoResponse> getRepositories(
            @Query(value = "q", encoded = true) String query,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("page") int page,
            @Query("per_page") int per_page);

    @GET("search/repositories")
    Observable<UserInfoResponse> getSmallRepositories(
            @Query(value = "q", encoded = true) String query,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("page") int page,
            @Query("per_page") int per_page);

    @GET("users/{username}")
    Observable<UserInfo> getUsers(
            @Path("username") String user,
            @Query("access_token") String token);

}