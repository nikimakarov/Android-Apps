package ru.surf.nikita_makarov.githubtrends.fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.surf.nikita_makarov.githubtrends.R;
import ru.surf.nikita_makarov.githubtrends.activity.MainActivity;
import ru.surf.nikita_makarov.githubtrends.database.DatabaseHelper;
import ru.surf.nikita_makarov.githubtrends.database.RepositoryDetails;
import ru.surf.nikita_makarov.githubtrends.database.UserDetails;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryAdapter;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfo;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfoResponse;
import ru.surf.nikita_makarov.githubtrends.retrofit.GithubApi;
import ru.surf.nikita_makarov.githubtrends.retrofit.GithubService;
import ru.surf.nikita_makarov.githubtrends.users_utils.SmallRepositoryInfo;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserAdapter;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserInfo;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserInfoResponse;
import ru.surf.nikita_makarov.githubtrends.utils.ConnectionInspector;
import ru.surf.nikita_makarov.githubtrends.utils.DateFormatter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PageFragment extends Fragment {

    private int pageNumber;
    private View fragmentView;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private RepositoryAdapter repositoryAdapter;
    private UserAdapter userAdapter;
    private String date;
    private String language;
    private String parameters;
    private DatabaseHelper databaseHelper = null;
    private static final String numberString = "num";
    private static final String databaseNameString = "github_trends.db";
    private static final String noConnectionString = "No internet connection!";
    final private static String ACCESS_TOKEN = "30d3d2219dbe26ddd33ff58c44d7f22000a078fd";
    protected static final String languageString = "language";
    protected static final String dateString = "date";
    private static final String starsString = "stars";
    private static final String descString = "desc";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            date = savedInstanceState.getString(dateString);
            language = savedInstanceState.getString(languageString);
        }
        pageNumber = getArguments() != null ? getArguments().getInt(numberString) : 1;
        this.setRetainInstance(true);
    }

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(numberString, page);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(dateString, date);
        outState.putString(languageString, language);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity;
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            if (language == null && date == null) {
                date = mainActivity.getDate();
                language = mainActivity.getLanguage();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment, container, false);
        } else {
            ((ViewGroup) fragmentView).removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_main);
            progressBar = (ProgressBar) fragmentView.findViewById(R.id.progress);
            progressBar.getIndeterminateDrawable().setColorFilter(0xFFEA3A78, PorterDuff.Mode.SRC_IN);
            mRecyclerView.setVisibility(View.GONE);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            if (pageNumber == 0) {
                repositoryAdapter = new RepositoryAdapter();
                mRecyclerView.setAdapter(repositoryAdapter);
            }
            if (pageNumber == 1) {
                userAdapter = new UserAdapter();
                mRecyclerView.setAdapter(userAdapter);
            }
            if (!ConnectionInspector.hasNetwork(getActivity())) {
                DatabaseConnectionStart();
            } else {
                RetrofitConnectionStart();
            }
        }
    }

    public void DatabaseConnectionStart() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), noConnectionString, Toast.LENGTH_LONG).show();
        databaseStart(pageNumber);
    }

    public void RetrofitConnectionStart() {
        getContext().deleteDatabase(databaseNameString);
        parameters = DateFormatter.makeParamsForQuery(date, language);
        GithubApi githubApi = GithubService.createGithubService();
        if (pageNumber == 0) {
            repositoryCall(githubApi);
        }
        if (pageNumber == 1) {
            shortInfoRepositoryCall(githubApi);
        }
    }

    public void repositoryCall(GithubApi githubApi) {
        final Observable<RepositoryInfoResponse> gitHubRepository = githubApi.getRepositories(parameters, starsString, descString, 1, 20);
        gitHubRepository.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<RepositoryInfoResponse>() {
            @Override
            public final void onCompleted() {
                progressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                repositoryAdapter.notifyDataSetChanged();
            }

            @Override
            public final void onError(Throwable e) {
                Log.e("repositoryCall", e.getMessage());
            }

            @Override
            public final void onNext(RepositoryInfoResponse response) {
                repositoryAdapter.clearData();
                repositoryAdapter.addDataAndSendRepositoriesToDatabase(response.getItems(), getContext());
            }
        });
    }

    public void shortInfoRepositoryCall(GithubApi githubApi) {
        final Observable<UserInfoResponse> gitHubUserInfoRepository = githubApi.getSmallRepositories(parameters, starsString, descString, 1, 20);
        gitHubUserInfoRepository.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<UserInfoResponse>() {
            @Override
            public final void onCompleted() {
                userAdapter.notifyDataSetChanged();
                userCall(githubApi);
            }

            @Override
            public final void onError(Throwable e) {
                Log.e("userInfoRepositoryCall", e.getMessage());
            }

            @Override
            public final void onNext(UserInfoResponse response) {
                userAdapter.clearRepositoryData();
                userAdapter.addRepositoryData(response.getItems(), getContext());
            }
        });
    }

    public void userCall(GithubApi githubApi) {
        userAdapter.clearUserData();
        List<SmallRepositoryInfo> repoList = userAdapter.smallRepositoryInfoList;
        for (int i = 0; userAdapter.smallRepositoryInfoList.size() > i & i < 20; i++) {
            final Observable<UserInfo> gitHubUser = githubApi.getUsers(repoList.get(i).getAuthorLogin(), ACCESS_TOKEN);
            gitHubUser.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserInfo>() {
                        @Override
                        public final void onCompleted() {
                            progressBar.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            userAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public final void onError(Throwable e) {
                            Log.e("userCall", e.getMessage());
                        }

                        @Override
                        public final void onNext(UserInfo response) {
                            userAdapter.sendUserToDatabase(response);
                        }
                    });
        }
    }

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public void databaseStart(int pageNumber) {
        if (pageNumber == 0) {
            repositoryDatabaseCall(repositoryAdapter);
        }
        if (pageNumber == 1) {
            userDatabaseCall(userAdapter);
        }
    }

    public void repositoryDatabaseCall(RepositoryAdapter repositoryAdapter) {
        try {
            final Dao<RepositoryDetails, Integer> repositoryDao = getHelper().getRepositoryDao();
            List<RepositoryDetails> repositoryList = repositoryDao.queryForAll();
            if (repositoryList.size() != 0) {
                List<RepositoryInfo> repositoryInfo = new ArrayList<>();
                for (int index = 0; index < repositoryList.size(); index++) {
                    RepositoryInfo singleRepositoryInfo = new RepositoryInfo();
                    singleRepositoryInfo.transform(repositoryList.get(index));
                    repositoryInfo.add(index, singleRepositoryInfo);
                }
                repositoryAdapter = new RepositoryAdapter();
                mRecyclerView.setAdapter(repositoryAdapter);
                repositoryAdapter.clearData();
                repositoryAdapter.addData(repositoryInfo, getContext());
                repositoryAdapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void userDatabaseCall(UserAdapter userAdapter) {
        try {
            final Dao<UserDetails, Integer> userDao = getHelper().getUserDao();
            List<UserDetails> userList = userDao.queryForAll();
            if (userList.size() != 0) {
                List<UserInfo> userInfo = new ArrayList<>();
                List<SmallRepositoryInfo> smallRepositoryInfo = new ArrayList<>();
                for (int index = 0; index < userList.size(); index++) {
                    UserInfo singleUserInfo = new UserInfo();
                    SmallRepositoryInfo singleSmallRepositoryInfo = new SmallRepositoryInfo();
                    singleUserInfo.transform(userList.get(index));
                    singleSmallRepositoryInfo.transform(userList.get(index));
                    userInfo.add(index, singleUserInfo);
                    smallRepositoryInfo.add(singleSmallRepositoryInfo);
                }
                userAdapter = new UserAdapter();
                mRecyclerView.setAdapter(userAdapter);
                userAdapter.clearRepositoryData();
                userAdapter.addRepositoryData(smallRepositoryInfo, getContext());
                userAdapter.clearUserData();
                userAdapter.addListUserData(userInfo);
                userAdapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
