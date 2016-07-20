package ru.surf.nikita_makarov.githubtrends.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.surf.nikita_makarov.githubtrends.R;
import ru.surf.nikita_makarov.githubtrends.activity.MainActivity;
import ru.surf.nikita_makarov.githubtrends.database.DatabaseHelper;
import ru.surf.nikita_makarov.githubtrends.database.RepositoryDetails;
import ru.surf.nikita_makarov.githubtrends.database.UserDetails;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryAdapter;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfo;
import ru.surf.nikita_makarov.githubtrends.repository_utils.RepositoryInfoResponse;
import ru.surf.nikita_makarov.githubtrends.users_utils.SmallRepositoryInfo;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserAdapter;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserInfo;
import ru.surf.nikita_makarov.githubtrends.users_utils.UserInfoResponse;
import ru.surf.nikita_makarov.githubtrends.utils.GitHubService;

public class PageFragment extends Fragment {

    private int pageNumber;
    private RecyclerView mRecyclerView;
    private View fragmentView;
    private RepositoryAdapter repositoryAdapter;
    private UserAdapter userAdapter;
    private DatabaseHelper databaseHelper = null;
    private String date;
    private String language;
    private String parameters;
    private static final String numberString = "num";
    private static final String databaseNameString = "github_trends.db";
    private static final String noConnectionString = "No internet connection!";
    private static final String starsString = "stars";
    private static final String descString = "desc";
    private static final String createdQString = "created:>=";
    private static final String languageQString = "+language:";
    private static final String errorString = "Error: ";
    private static final String zeroString = "0";
    private static final String emptyString = "";
    private static final String todayString = "today";
    private static final String weekString = "week";
    private static final String monthString = "month";
    private static final String yearString = "year";
    private static final String dashString = "-";
    private static final String allTimeDateString = "2000-01-01";
    private static final String allTimeString = "all time";
    final private static String API_URL = "https://api.github.com/";
    final private static String ACCESS_TOKEN = "30d3d2219dbe26ddd33ff58c44d7f22000a078fd";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt(numberString) : 1;
    }

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(numberString, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        MainActivity mainActivity;

        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            date = mainActivity.getDate();
            language = mainActivity.getLanguage();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment, container, false);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_main);
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
    }

    @Override
    public void onStart() {
        super.onStart();
        parameters = makeParamsForQuery();
        if (!isOnline()) {
            Toast.makeText(getContext(), noConnectionString, Toast.LENGTH_LONG).show();
            databaseStart();
        } else {
            getContext().deleteDatabase(databaseNameString);
            retrofitStart();
        }
    }

    public void retrofitStart() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        GitHubService gitHubService = retrofit.create(GitHubService.class);
        if (pageNumber == 0) {
            repositoryCall(gitHubService);
        }
        if (pageNumber == 1) {
            shortInfoRepositoryCall(gitHubService);
            userCall(gitHubService);
        }
    }

    public void repositoryCall(GitHubService gitHubService) {
        Call<RepositoryInfoResponse> call = gitHubService.repositories(parameters, starsString, descString, 1, 20);
        call.enqueue(new Callback<RepositoryInfoResponse>() {
            @Override
            public void onResponse(Call<RepositoryInfoResponse> call, Response<RepositoryInfoResponse> response) {
                repositoryAdapter.clearData();
                repositoryAdapter.addDataAndSendRepositoriesToDatabase(response.body().getItems(), getContext());
                repositoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RepositoryInfoResponse> call, Throwable t) {
                Log.d(errorString, t.getMessage());
            }
        });
    }

    public void shortInfoRepositoryCall(GitHubService gitHubService) {
        Call<UserInfoResponse> call = gitHubService.smallRepositories(parameters, starsString, descString, 1, 20);
        try {
            userAdapter.clearRepositoryData();
            userAdapter.addRepositoryData(call.execute().body().getItems(), getContext());
            userAdapter.notifyDataSetChanged();
        } catch (IOException io) {
            Log.d(errorString, io.getMessage());
        }
    }

    public void userCall(GitHubService gitHubService) {
        userAdapter.clearUserData();
        for (int i = 0; userAdapter.smallRepositoryInfoList.size() > i & i < 20; i++) {
            Call<UserInfo> call = gitHubService.users(userAdapter.smallRepositoryInfoList.get(i).getAuthorLogin(), ACCESS_TOKEN);
            call.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    userAdapter.sendUserToDatabase(response.body());
                    userAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    Log.d(errorString, t.getMessage());
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

    public void databaseStart() {
        if (pageNumber == 0) {
            repositoryDatabaseCall();
        }
        if (pageNumber == 1) {
            userDatabaseCall();
        }
    }

    public void repositoryDatabaseCall() {
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

    public void userDatabaseCall() {
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

    public boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(cs);
        return cm.getActiveNetworkInfo() != null;
    }

    public String makeParamsForQuery() {
        return createdQString + dateInterval(date) + languageQString + language;
    }

    public String dateInterval(String dateFilter) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String zero1 = month < 10 ? zeroString : emptyString;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String zero2 = day < 10 ? zeroString : emptyString;
        switch (dateFilter) {
            case todayString:
                return (year + dashString + zero1 + month + dashString + zero2 + day);
            case weekString:
                return (year + dashString + zero1 + month + dashString + zero2 + (day - 7));
            case monthString:
                return (year + dashString + zero1 + (month - 1) + dashString + zero2 + day);
            case yearString:
                return ((year - 1) + dashString + zero1 + month + dashString + zero2 + day);
            case allTimeString:
                return allTimeDateString;
            default:
                return (year + dashString + zero1 + month + dashString + zero2 + day);
        }
    }
}
