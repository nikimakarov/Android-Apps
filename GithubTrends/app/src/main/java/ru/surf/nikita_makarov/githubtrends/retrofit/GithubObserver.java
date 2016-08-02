package ru.surf.nikita_makarov.githubtrends.retrofit;

import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfoResponse;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserInfoResponse;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GithubObserver {

    private static final String starsString = "stars";
    private static final String descString = "desc";

    public static Observable<RepositoryInfoResponse> repositoryCall(GithubApi githubApi, String parameters) {
        final Observable<RepositoryInfoResponse> gitHubRepository = githubApi.getRepositories(parameters, starsString, descString, 1, 20);
        gitHubRepository.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return gitHubRepository;
    }

    public static Observable<UserInfoResponse> shortInfoRepositoryCall(GithubApi githubApi, String parameters) {
        final Observable<UserInfoResponse> gitHubUserInfoRepository = githubApi.getSmallRepositories(parameters, starsString, descString, 1, 20);
        gitHubUserInfoRepository.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return gitHubUserInfoRepository;
    }
}
