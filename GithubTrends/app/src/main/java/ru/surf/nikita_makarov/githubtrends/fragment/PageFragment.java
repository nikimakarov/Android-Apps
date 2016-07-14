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
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.surf.nikita_makarov.githubtrends.R;
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

    public int pageNumber;
    public String API_URL = "https://api.github.com/";
    public String chosenDate = "created:2016-06-01";
    public RecyclerView mRecyclerView;
    public View fragmentView;
    public RepositoryAdapter repositoryAdapter;
    public UserAdapter userAdapter;
    private DatabaseHelper databaseHelper = null;
    final public String ACCESS_TOKEN = "30d3d2219dbe26ddd33ff58c44d7f22000a078fd";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
        Log.d("pageNumber", Integer.toString(pageNumber));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isOnline()){
            Toast.makeText(getContext(), "No internet connection!", Toast.LENGTH_LONG).show();
            databaseStart();
        }
        else {
            getContext().deleteDatabase("github_trends.db");
            retrofitStart();
        }
    }

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment, container, false);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_main);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (pageNumber == 0){
            repositoryAdapter = new RepositoryAdapter();
            mRecyclerView.setAdapter(repositoryAdapter);}
        if (pageNumber == 1) {
            userAdapter = new UserAdapter();
            mRecyclerView.setAdapter(userAdapter);
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

    public void repositoryCall(GitHubService gitHubService){
        Call<RepositoryInfoResponse> call = gitHubService.repositories(chosenDate, "stars", "desc", "1", "20");
        call.enqueue(new Callback<RepositoryInfoResponse>() {
            @Override
            public void onResponse(Call<RepositoryInfoResponse> call, Response<RepositoryInfoResponse> response) {
                repositoryAdapter.clearData();
                repositoryAdapter.addDataAndSendRepositoriesToDatabase(response.body().getItems(), getContext());
                repositoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RepositoryInfoResponse> call, Throwable t) {
                Log.d("Error: ", t.getMessage());
            }
        });
    }

    public void shortInfoRepositoryCall(GitHubService gitHubService){
        Call<UserInfoResponse> call = gitHubService.smallRepositories(chosenDate, "stars", "desc", "1", "20");
        try {
            userAdapter.clearRepositoryData();
            userAdapter.addRepositoryData(call.execute().body().getItems(),getContext());
            userAdapter.notifyDataSetChanged();
        }
        catch (IOException io) {
            Log.d("Error: ", io.getMessage());
        }
    }

    public void userCall(GitHubService gitHubService){
        userAdapter.clearUserData();
        for (int i = 0; userAdapter.smallRepositoryInfoList.size()>i & i < 20; i++) {
            Call<UserInfo> call = gitHubService.users(userAdapter.smallRepositoryInfoList.get(i).getAuthorLogin(),ACCESS_TOKEN);
            call.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    userAdapter.sendUserToDatabase(response.body());
                    userAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    Log.d("Error: ", t.getMessage());
                }
            });
        }
    }

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getContext(),DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public void databaseStart() {
        if (pageNumber == 0){
        repositoryDatabaseCall();
        }
        if (pageNumber == 1) {
            userDatabaseCall();
        }
    }

    public void repositoryDatabaseCall(){
        try {
            final Dao<RepositoryDetails, Integer> repositoryDao = getHelper().getRepositoryDao();
            List<RepositoryDetails> repositoryList = repositoryDao.queryForAll();
            if (repositoryList.size()!=0){
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void userDatabaseCall(){
        try {
            final Dao<UserDetails, Integer> userDao = getHelper().getUserDao();
            List<UserDetails> userList = userDao.queryForAll();
            if (userList.size()!=0){
            List<UserInfo> userInfo = new ArrayList<>();
            List<SmallRepositoryInfo> smallRepositoryInfo = new ArrayList<>();
            for (int index = 0; index < userList.size(); index++){
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(cs);
        return cm.getActiveNetworkInfo()!=null;
    }

}
